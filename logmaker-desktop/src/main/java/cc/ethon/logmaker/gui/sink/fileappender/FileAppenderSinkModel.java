package cc.ethon.logmaker.gui.sink.fileappender;

import cc.ethon.logmaker.Settings;
import cc.ethon.logmaker.gui.sink.SingleFileSinkModel;

public class FileAppenderSinkModel extends SingleFileSinkModel {

	private static final String KEY_SINKFILE = FileAppenderSinkModel.class.getSimpleName() + ".sinkFile";

	@Override
	protected String getFileKey() {
		return KEY_SINKFILE;
	}

	public FileAppenderSinkModel(Settings settings) {
		super(settings);
	}

}
