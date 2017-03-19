package cc.ethon.logmaker.gui.reader.strong;

import java.io.File;

import javafx.scene.Node;
import javafx.stage.Stage;
import cc.ethon.logmaker.Settings;
import cc.ethon.logmaker.gui.reader.SingleFileReaderController;
import cc.ethon.logmaker.reader.LogReader;
import cc.ethon.logmaker.reader.strong.StrongCsvReader;

public class StrongCsvReaderController extends SingleFileReaderController<StrongCsvReaderModel> {

	@Override
	protected String getImageResourceName() {
		return "/strong-icon-24.png";
	}

	public StrongCsvReaderController(Settings settings) {
		super(new StrongCsvReaderModel(settings));
	}

	@Override
	public String getName() {
		return "Strong CSV Export Reader";
	}

	@Override
	public LogReader createReader() {
		return new StrongCsvReader(new File(model.getFile().get()));
	}

	@Override
	public Node getOptionsView(Stage stage) {
		return new StrongCsvReaderView(stage, model);
	}

}
