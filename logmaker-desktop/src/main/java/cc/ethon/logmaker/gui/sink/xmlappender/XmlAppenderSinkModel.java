package cc.ethon.logmaker.gui.sink.xmlappender;

import cc.ethon.logmaker.Settings;
import cc.ethon.logmaker.gui.sink.SingleFileSinkModel;

public class XmlAppenderSinkModel extends SingleFileSinkModel {

	private static final String KEY_SINKFILE = XmlAppenderSinkModel.class.getSimpleName() + ".sinkFile";

	@Override
	protected String getFileKey() {
		return KEY_SINKFILE;
	}

	public XmlAppenderSinkModel(Settings settings) {
		super(settings);
	}

}
