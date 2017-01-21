package cc.ethon.logmaker.gui.sink.fileappender;

import java.util.HashMap;
import java.util.Map;

import javafx.stage.Stage;
import cc.ethon.logmaker.gui.sink.SingleFileSinkModel;
import cc.ethon.logmaker.gui.sink.SingleFileSinkView;

public class FileAppenderSinkView extends SingleFileSinkView {

	@Override
	protected Map<Integer, String> createStrings() {
		final Map<Integer, String> strings = new HashMap<Integer, String>();
		strings.put(CHOSEN_FILE, "Chosen file:");
		strings.put(CHOOSE_FILE, "Choose file");
		strings.put(SELECT_FILE, "Select Output File");
		return strings;
	}

	public FileAppenderSinkView(Stage stage, SingleFileSinkModel model) {
		super(stage, model);
	}

}
