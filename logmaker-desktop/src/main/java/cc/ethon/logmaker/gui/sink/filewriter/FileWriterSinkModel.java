package cc.ethon.logmaker.gui.sink.filewriter;

import cc.ethon.logmaker.Settings;
import cc.ethon.logmaker.gui.sink.SingleFileSinkModel;

public class FileWriterSinkModel extends SingleFileSinkModel {

	private static final String KEY_SINKFILE = FileWriterSinkModel.class.getSimpleName() + ".sinkFile";

	@Override
	protected String getFileKey() {
		return KEY_SINKFILE;
	}

	public FileWriterSinkModel(Settings settings) {
		super(settings);
	}

}
