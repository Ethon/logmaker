package cc.ethon.logmaker.gui.readermodel;

import cc.ethon.logmaker.reader.LogReader;
import cc.ethon.logmaker.reader.redy.db.RedyGymLogDbReader;

public class RedyGymLogDbReaderModel extends LogReaderModel {

	@Override
	public String getName() {
		return "Redy Gym Log Database Backup Reader";
	}

	@Override
	public LogReader createReader() {
		return new RedyGymLogDbReader();
	}

}
