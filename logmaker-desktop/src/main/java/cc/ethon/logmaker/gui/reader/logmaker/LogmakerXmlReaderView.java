package cc.ethon.logmaker.gui.reader.logmaker;

import java.util.HashMap;
import java.util.Map;

import javafx.stage.Stage;
import cc.ethon.logmaker.gui.reader.SingleFileReaderModel;
import cc.ethon.logmaker.gui.reader.SingleFileReaderView;

public class LogmakerXmlReaderView extends SingleFileReaderView {

	@Override
	protected Map<Integer, String> createStrings() {
		final Map<Integer, String> strings = new HashMap<Integer, String>();
		strings.put(CHOSEN_FILE, "Chosen XML file:");
		strings.put(CHOOSE_FILE, "Choose file");
		strings.put(SELECT_FILE, "Select XML File");
		strings.put(DELETE_FILE, "Delete file after generation");
		return strings;
	}

	public LogmakerXmlReaderView(Stage stage, SingleFileReaderModel model) {
		super(stage, model);
	}

}
