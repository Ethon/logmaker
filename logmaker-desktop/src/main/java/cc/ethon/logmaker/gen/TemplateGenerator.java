package cc.ethon.logmaker.gen;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import cc.ethon.logmaker.WorkoutLog;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;

public class TemplateGenerator implements Generator {

	private final Configuration configuration;
	private final List<String> availableTemplates;
	private String selectedTemplate;

	private Configuration createConfiguration(File templateDirectory) throws IOException {
		// Create your Configuration instance, and specify if up to what
		// FreeMarker
		// version (here 2.3.23) do you want to apply the fixes that are not
		// 100%
		// backward-compatible. See the Configuration JavaDoc for details.
		final Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);

		// Specify the source where the template files come from. Here I set a
		// plain directory for it, but non-file-system sources are possible too:
		configuration.setDirectoryForTemplateLoading(templateDirectory);

		// Set the preferred charset template files are stored in. UTF-8 is
		// a good choice in most applications:
		configuration.setDefaultEncoding("UTF-8");

		// Sets how errors will appear.
		// During web page *development*
		// TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		// Don't log exceptions inside FreeMarker that it will thrown at you
		// anyway:
		configuration.setLogTemplateExceptions(false);

		return configuration;
	}

	private List<String> searchAvailableTemplates(File templateDirectory) {
		final List<String> result = new ArrayList<String>();
		for (final File file : templateDirectory.listFiles()) {
			if (file.isFile()) {
				result.add(file.getName());
			}
		}
		result.sort(String.CASE_INSENSITIVE_ORDER);
		return result;
	}

	public TemplateGenerator(File templateDirectory) throws IOException {
		if (!templateDirectory.isDirectory()) {
			throw new IllegalArgumentException("Directoy expected");
		}
		configuration = createConfiguration(templateDirectory);
		availableTemplates = searchAvailableTemplates(templateDirectory);
		if (!availableTemplates.isEmpty()) {
			selectedTemplate = availableTemplates.get(0);
		}
	}

	public void selectTemplate(String name) {
		if (!availableTemplates.contains(name)) {
			throw new IllegalArgumentException("Template " + name + " does not exist");
		}
		selectedTemplate = name;
	}

	@Override
	public void gen(PrintStream out, WorkoutLog log) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException,
			TemplateException {
		final Template template = configuration.getTemplate(selectedTemplate);
		template.process(log, new PrintWriter(out));
	}

	@Override
	public void genLastWorkout(PrintStream out, WorkoutLog log) {
		if (log.getWorkouts().isEmpty()) {
			throw new UnsupportedOperationException("Can't generate last workout when no workouts are available");
		}
		final WorkoutLog syntheticLog = new WorkoutLog();
		syntheticLog.addWorkout(log.getWorkouts().get(log.getWorkouts().size() - 1));
	}

	@Override
	public void genLastWorkoutToClipboard(WorkoutLog log) {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintStream ps = new PrintStream(baos);
		genLastWorkout(ps, log);

		final Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		final StringSelection data = new StringSelection(baos.toString());
		c.setContents(data, data);
	}

}
