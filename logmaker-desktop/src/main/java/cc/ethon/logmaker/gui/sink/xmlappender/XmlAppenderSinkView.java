package cc.ethon.logmaker.gui.sink.xmlappender;

import java.util.HashMap;
import java.util.Map;

import javafx.stage.Stage;
import cc.ethon.logmaker.gui.sink.SingleFileSinkModel;
import cc.ethon.logmaker.gui.sink.SingleFileSinkView;

public class XmlAppenderSinkView extends SingleFileSinkView {

	@Override
	protected Map<Integer, String> createStrings() {
		final Map<Integer, String> strings = new HashMap<Integer, String>();
		strings.put(CHOSEN_FILE, "Chosen XML file:");
		strings.put(CHOOSE_FILE, "Choose XML file");
		strings.put(SELECT_FILE, "Select Output XML File");
		return strings;
	}

	public XmlAppenderSinkView(Stage stage, SingleFileSinkModel model) {
		super(stage, model);
	}

}
