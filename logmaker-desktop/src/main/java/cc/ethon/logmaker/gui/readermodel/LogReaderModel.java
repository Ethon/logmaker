package cc.ethon.logmaker.gui.readermodel;

import cc.ethon.logmaker.reader.LogReader;

public abstract class LogReaderModel {

	public abstract String getName();

	public abstract LogReader createReader();

	@Override
	public String toString() {
		return getName();
	}
}
