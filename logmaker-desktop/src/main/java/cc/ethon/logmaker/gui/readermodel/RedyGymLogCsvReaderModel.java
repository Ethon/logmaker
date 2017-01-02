package cc.ethon.logmaker.gui.readermodel;

import cc.ethon.logmaker.reader.LogReader;
import cc.ethon.logmaker.reader.redy.csv.RedyGymLogCsvReader;

public class RedyGymLogCsvReaderModel extends LogReaderModel {

	@Override
	public String getName() {
		return "Redy Gym Log CSV Export Reader";
	}

	@Override
	public LogReader createReader() {
		return new RedyGymLogCsvReader();
	}

}
