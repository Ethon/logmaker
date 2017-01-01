package cc.ethon.logmaker.reader;

import java.io.File;

import cc.ethon.logmaker.WorkoutLog;

public interface LogReader {
	public WorkoutLog readLog(File input) throws Exception;
}
