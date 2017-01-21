package cc.ethon.logmaker.gui.reader.redy.db;

import java.util.HashMap;
import java.util.Map;

import javafx.stage.Stage;
import cc.ethon.logmaker.gui.reader.SingleFileReaderView;

public class RedyGymLogDbReaderView extends SingleFileReaderView {

	@Override
	protected Map<Integer, String> createStrings() {
		final Map<Integer, String> strings = new HashMap<Integer, String>();
		strings.put(CHOSEN_FILE, "Chosen backup:");
		strings.put(CHOOSE_FILE, "Choose backup");
		strings.put(SELECT_FILE, "Select Backup File");
		strings.put(DELETE_FILE, "Delete backup after generation");
		return strings;
	}

	public RedyGymLogDbReaderView(Stage stage, RedyGymLogDbReaderModel model) {
		super(stage, model);
	}

}
