package cc.ethon.logmaker.gui.reader.redy.csv;

import cc.ethon.logmaker.Settings;
import cc.ethon.logmaker.gui.reader.SingleFileReaderModel;

public class RedyGymLogCsvReaderModel extends SingleFileReaderModel {

	private static final String KEY_EXPORTFILE = RedyGymLogCsvReaderModel.class.getSimpleName() + ".exportFile";
	private static final String KEY_DELETEEXPORTFILE = RedyGymLogCsvReaderModel.class.getSimpleName() + ".deleteExportFile";

	@Override
	protected String getFileKey() {
		return KEY_EXPORTFILE;
	}

	@Override
	protected String getDeleteFileKey() {
		return KEY_DELETEEXPORTFILE;
	}

	public RedyGymLogCsvReaderModel(Settings settings) {
		super(settings);
	}

}
