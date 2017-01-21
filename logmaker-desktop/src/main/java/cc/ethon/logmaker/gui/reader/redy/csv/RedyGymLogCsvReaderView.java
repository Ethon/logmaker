package cc.ethon.logmaker.gui.reader.redy.csv;

import java.util.HashMap;
import java.util.Map;

import javafx.stage.Stage;
import cc.ethon.logmaker.gui.reader.SingleFileReaderView;

public class RedyGymLogCsvReaderView extends SingleFileReaderView {

	@Override
	protected Map<Integer, String> createStrings() {
		final Map<Integer, String> strings = new HashMap<Integer, String>();
		strings.put(CHOSEN_FILE, "Chosen CSV export:");
		strings.put(CHOOSE_FILE, "Choose export");
		strings.put(SELECT_FILE, "Select Export File");
		strings.put(DELETE_FILE, "Delete export after generation");
		return strings;
	}

	public RedyGymLogCsvReaderView(Stage stage, RedyGymLogCsvReaderModel model) {
		super(stage, model);
	}

}
