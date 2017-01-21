package cc.ethon.logmaker.gui.sink.fileappender;

import java.io.File;
import java.io.FileNotFoundException;

import javafx.scene.Node;
import javafx.stage.Stage;
import cc.ethon.logmaker.Settings;
import cc.ethon.logmaker.gui.sink.SinkController;
import cc.ethon.logmaker.sink.FileAppenderSink;
import cc.ethon.logmaker.sink.Sink;

public class FileAppenderSinkController extends SinkController {

	private final FileAppenderSinkModel model;

	public FileAppenderSinkController(Settings settings) {
		model = new FileAppenderSinkModel(settings);
	}

	@Override
	public String getName() {
		return "File Appender";
	}

	@Override
	public Sink createSink() throws FileNotFoundException {
		return new FileAppenderSink(new File(model.getFile().get()), true);
	}

	@Override
	public Node getOptionsView(Stage stage) {
		return new FileAppenderSinkView(stage, model);
	}

}
