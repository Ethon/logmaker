package cc.ethon.logmaker.gui.reader.logmaker;

import java.io.File;

import javafx.scene.Node;
import javafx.stage.Stage;
import cc.ethon.logmaker.Settings;
import cc.ethon.logmaker.gui.reader.SingleFileReaderController;
import cc.ethon.logmaker.reader.LogReader;
import cc.ethon.logmaker.reader.logmaker.LogmakerXmlReader;

public class LogmakerXmlReaderController extends SingleFileReaderController<LogmakerXmlReaderModel> {

	@Override
	protected String getImageResourceName() {
		return "/logmaker-icon-24.png";
	}

	public LogmakerXmlReaderController(Settings settings) {
		super(new LogmakerXmlReaderModel(settings));
	}

	@Override
	public String getName() {
		return "Logmaker XML Reader";
	}

	@Override
	public LogReader createReader() {
		return new LogmakerXmlReader(new File(model.getFile().get()));
	}

	@Override
	public Node getOptionsView(Stage stage) {
		return new LogmakerXmlReaderView(stage, model);
	}

}
