package cc.ethon.logmaker.gui.sink;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import cc.ethon.logmaker.Settings;

public abstract class SingleFileSinkModel {

	private final SimpleStringProperty file;

	protected abstract String getFileKey();

	public SingleFileSinkModel(Settings settings) {
		file = new SimpleStringProperty();
		if (settings.getString(getFileKey()) != null) {
			file.set(settings.getString(getFileKey()));
		}
		file.addListener((obs, o, n) -> settings.setString(getFileKey(), n));
	}

	public StringProperty getFile() {
		return file;
	}

}
