package cc.ethon.logmaker.gui.reader;

import javafx.scene.Node;
import javafx.stage.Stage;
import cc.ethon.logmaker.reader.LogReader;

public abstract class LogReaderController {

	public abstract String getName();

	public abstract LogReader createReader();

	public abstract void postProcess();

	public abstract Node getOptionsView(Stage stage);

	@Override
	public String toString() {
		return getName();
	}
}
