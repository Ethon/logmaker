package cc.ethon.logmaker.gui.sink.filewriter;

import java.io.File;
import java.io.FileNotFoundException;

import javafx.scene.Node;
import javafx.stage.Stage;
import cc.ethon.logmaker.Settings;
import cc.ethon.logmaker.gui.sink.SinkController;
import cc.ethon.logmaker.sink.FileAppenderSink;
import cc.ethon.logmaker.sink.Sink;

public class FileWriterSinkController extends SinkController {

	private final FileWriterSinkModel model;

	public FileWriterSinkController(Settings settings) {
		model = new FileWriterSinkModel(settings);
	}

	@Override
	public String getName() {
		return "File Writer";
	}

	@Override
	public Sink createSink() throws FileNotFoundException {
		return new FileAppenderSink(new File(model.getFile().get()), false);
	}

	@Override
	public Node getOptionsView(Stage stage) {
		return new FileWriterSinkView(stage, model);
	}

}
