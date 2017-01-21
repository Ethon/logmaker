package cc.ethon.logmaker.gui.reader.logmaker;

import cc.ethon.logmaker.Settings;
import cc.ethon.logmaker.gui.reader.SingleFileReaderModel;
import cc.ethon.logmaker.gui.reader.redy.csv.RedyGymLogCsvReaderModel;

public class LogmakerXmlReaderModel extends SingleFileReaderModel {

	private static final String KEY_FILE = RedyGymLogCsvReaderModel.class.getSimpleName() + ".file";
	private static final String KEY_DELETEFILE = RedyGymLogCsvReaderModel.class.getSimpleName() + ".deletefile";

	@Override
	protected String getFileKey() {
		return KEY_FILE;
	}

	@Override
	protected String getDeleteFileKey() {
		return KEY_DELETEFILE;
	}

	public LogmakerXmlReaderModel(Settings settings) {
		super(settings);
	}

}
