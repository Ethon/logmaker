package cc.ethon.logmaker.gui.sink;

import javafx.scene.Node;
import javafx.stage.Stage;
import cc.ethon.logmaker.sink.Sink;

public abstract class SinkController {

	public abstract String getName();

	public abstract Sink createSink();

	public abstract Node getOptionsView(Stage stage);

	@Override
	public String toString() {
		return getName();
	}

}
