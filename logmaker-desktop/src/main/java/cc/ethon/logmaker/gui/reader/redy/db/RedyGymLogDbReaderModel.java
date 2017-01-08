package cc.ethon.logmaker.gui.reader.redy.db;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import cc.ethon.logmaker.Settings;

public class RedyGymLogDbReaderModel {

	private static final String KEY_BACKUPFILE = RedyGymLogDbReaderModel.class.getSimpleName() + ".backupFile";
	private static final String KEY_DELETEBACKUPFILE = RedyGymLogDbReaderModel.class.getSimpleName() + ".deleteBackupFile";

	private final SimpleStringProperty backupFile;
	private final BooleanProperty deleteBackupFile;

	public RedyGymLogDbReaderModel(Settings settings) {
		backupFile = new SimpleStringProperty();
		if (settings.getString(KEY_BACKUPFILE) != null) {
			backupFile.set(settings.getString(KEY_BACKUPFILE));
		}
		backupFile.addListener((obs, o, n) -> settings.setString(KEY_BACKUPFILE, n));

		deleteBackupFile = new SimpleBooleanProperty();
		deleteBackupFile.set(settings.getBoolean(KEY_DELETEBACKUPFILE));
		deleteBackupFile.addListener((obs, o, n) -> settings.setBoolean(KEY_DELETEBACKUPFILE, n));
	}

	public StringProperty getBackupFile() {
		return backupFile;
	}

	public BooleanProperty getDeleteBackupFile() {
		return deleteBackupFile;
	}

}
