package cc.ethon.logmaker.gui.reader;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import cc.ethon.logmaker.Settings;

public abstract class SingleFileReaderModel {

	private final SimpleStringProperty file;
	private final BooleanProperty deleteFile;

	protected abstract String getFileKey();

	protected abstract String getDeleteFileKey();

	public SingleFileReaderModel(Settings settings) {
		file = new SimpleStringProperty();
		if (settings.getString(getFileKey()) != null) {
			file.set(settings.getString(getFileKey()));
		}
		file.addListener((obs, o, n) -> settings.setString(getFileKey(), n));

		deleteFile = new SimpleBooleanProperty();
		deleteFile.set(settings.getBoolean(getDeleteFileKey()));
		deleteFile.addListener((obs, o, n) -> settings.setBoolean(getDeleteFileKey(), n));
	}

	public StringProperty getFile() {
		return file;
	}

	public BooleanProperty getDeleteFile() {
		return deleteFile;
	}

}
