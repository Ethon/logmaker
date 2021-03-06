package cc.ethon.logmaker.gui.reader.redy.csv;

import java.io.File;

import javafx.scene.Node;
import javafx.stage.Stage;
import cc.ethon.logmaker.Settings;
import cc.ethon.logmaker.gui.reader.SingleFileReaderController;
import cc.ethon.logmaker.reader.LogReader;
import cc.ethon.logmaker.reader.redy.csv.RedyGymLogCsvReader;

public class RedyGymLogCsvReaderController extends SingleFileReaderController<RedyGymLogCsvReaderModel> {

	@Override
	protected String getImageResourceName() {
		return "/redy-icon-24.png";
	}

	public RedyGymLogCsvReaderController(Settings settings) {
		super(new RedyGymLogCsvReaderModel(settings));
	}

	@Override
	public String getName() {
		return "Redy Gym Log CSV Export Reader";
	}

	@Override
	public LogReader createReader() {
		return new RedyGymLogCsvReader(new File(model.getFile().get()));
	}

	@Override
	public Node getOptionsView(Stage stage) {
		return new RedyGymLogCsvReaderView(stage, model);
	}

}
