package cc.ethon.logmaker.gui.reader.strong;

import cc.ethon.logmaker.Settings;
import cc.ethon.logmaker.gui.reader.SingleFileReaderModel;

public class StrongCsvReaderModel extends SingleFileReaderModel {

	private static final String KEY_EXPORTFILE = StrongCsvReaderModel.class.getSimpleName() + ".exportFile";
	private static final String KEY_DELETEEXPORTFILE = StrongCsvReaderModel.class.getSimpleName() + ".deleteExportFile";

	@Override
	protected String getFileKey() {
		return KEY_EXPORTFILE;
	}

	@Override
	protected String getDeleteFileKey() {
		return KEY_DELETEEXPORTFILE;
	}

	public StrongCsvReaderModel(Settings settings) {
		super(settings);
	}

}
