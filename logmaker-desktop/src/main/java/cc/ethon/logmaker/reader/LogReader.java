package cc.ethon.logmaker.reader;

import cc.ethon.logmaker.WorkoutLog;

public interface LogReader {
	public WorkoutLog readLog() throws Exception;
}
