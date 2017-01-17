package cc.ethon.logmaker.gen;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import no.api.freemarker.java8.Java8ObjectWrapper;
import cc.ethon.logmaker.WorkoutLog;
import cc.ethon.logmaker.formula.MaxEstimator;
import cc.ethon.logmaker.gen.model.WorkoutLogModel;
import cc.ethon.logmaker.sink.Sink;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class TemplateGenerator extends Generator {

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

		configuration.setObjectWrapper(new Java8ObjectWrapper(Configuration.VERSION_2_3_23));

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

	public List<String> getAvailableTemplates() {
		return availableTemplates;
	}

	public void selectTemplate(String name) {
		if (!availableTemplates.contains(name)) {
			throw new IllegalArgumentException("Template " + name + " does not exist");
		}
		selectedTemplate = name;
	}

	@Override
	protected void generate(Sink sink, WorkoutLog log, MaxEstimator maxEstimator) throws Exception {
		final Template template = configuration.getTemplate(selectedTemplate);
		final PrintWriter writer = new PrintWriter(sink.getOutputStream());
		template.process(new WorkoutLogModel(log, maxEstimator), writer);
		writer.flush();
		sink.applyContent();
	}

}
