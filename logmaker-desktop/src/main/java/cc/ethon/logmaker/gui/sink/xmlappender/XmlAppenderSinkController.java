package cc.ethon.logmaker.gui.sink.xmlappender;

import java.io.File;
import java.io.FileNotFoundException;

import javafx.scene.Node;
import javafx.stage.Stage;
import cc.ethon.logmaker.Settings;
import cc.ethon.logmaker.gui.sink.SinkController;
import cc.ethon.logmaker.sink.Sink;
import cc.ethon.logmaker.sink.XmlAppenderSink;

public class XmlAppenderSinkController extends SinkController {

	private final XmlAppenderSinkModel model;

	public XmlAppenderSinkController(Settings settings) {
		model = new XmlAppenderSinkModel(settings);
	}

	@Override
	public String getName() {
		return "XML Appender";
	}

	@Override
	public Sink createSink() throws FileNotFoundException {
		return new XmlAppenderSink(new File(model.getFile().get()));
	}

	@Override
	public Node getOptionsView(Stage stage) {
		return new XmlAppenderSinkView(stage, model);
	}

}
