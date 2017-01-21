package cc.ethon.logmaker.gui.reader.redy.db;

import cc.ethon.logmaker.Settings;
import cc.ethon.logmaker.gui.reader.SingleFileReaderModel;

public class RedyGymLogDbReaderModel extends SingleFileReaderModel {

	private static final String KEY_BACKUPFILE = RedyGymLogDbReaderModel.class.getSimpleName() + ".backupFile";
	private static final String KEY_DELETEBACKUPFILE = RedyGymLogDbReaderModel.class.getSimpleName() + ".deleteBackupFile";

	@Override
	protected String getFileKey() {
		return KEY_BACKUPFILE;
	}

	@Override
	protected String getDeleteFileKey() {
		return KEY_DELETEBACKUPFILE;
	}

	public RedyGymLogDbReaderModel(Settings settings) {
		super(settings);
	}

}
