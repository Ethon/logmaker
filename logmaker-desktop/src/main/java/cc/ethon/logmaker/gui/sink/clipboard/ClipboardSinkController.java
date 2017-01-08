package cc.ethon.logmaker.gui.sink.clipboard;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import cc.ethon.logmaker.gui.sink.SinkController;
import cc.ethon.logmaker.sink.ClipboardSink;
import cc.ethon.logmaker.sink.Sink;

public class ClipboardSinkController extends SinkController {

	@Override
	public String getName() {
		return "Clipboard";
	}

	@Override
	public Sink createSink() {
		return new ClipboardSink();
	}

	@Override
	public Node getOptionsView(Stage stage) {
		return new StackPane();
	}

}
