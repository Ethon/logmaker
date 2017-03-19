package cc.ethon.logmaker.gui.reader;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import cc.ethon.logmaker.reader.LogReader;

public abstract class LogReaderController {

	private Image image;

	protected abstract String getImageResourceName();

	public abstract String getName();

	public Image getImage() {
		if (image == null && getImageResourceName() != null) {
			image = new Image(this.getClass().getResourceAsStream(getImageResourceName()));
		}
		return image;
	}

	public abstract LogReader createReader();

	public abstract void postProcess();

	public abstract Node getOptionsView(Stage stage);

	@Override
	public String toString() {
		return getName();
	}
}
