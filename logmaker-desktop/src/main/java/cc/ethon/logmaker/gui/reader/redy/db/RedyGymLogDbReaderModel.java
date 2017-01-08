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
		if (settings.getSettingByKey(KEY_BACKUPFILE) != null) {
			backupFile.set(settings.getSettingByKey(KEY_BACKUPFILE));
		}
		backupFile.addListener((obs, o, n) -> settings.setSettingByKey(KEY_BACKUPFILE, n));

		deleteBackupFile = new SimpleBooleanProperty();
		deleteBackupFile.set("true".equals(settings.getSettingByKey(KEY_DELETEBACKUPFILE)));
		deleteBackupFile.addListener((obs, o, n) -> settings.setSettingByKey(KEY_DELETEBACKUPFILE, Boolean.toString(n)));
	}

	public StringProperty getBackupFile() {
		return backupFile;
	}

	public BooleanProperty getDeleteBackupFile() {
		return deleteBackupFile;
	}

}
