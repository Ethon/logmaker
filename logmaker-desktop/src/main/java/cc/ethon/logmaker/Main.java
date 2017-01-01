package cc.ethon.logmaker;

import java.io.File;

import cc.ethon.logmaker.gen.DefaultGenerator;
import cc.ethon.logmaker.gen.Generator;
import cc.ethon.logmaker.reader.LogReader;
import cc.ethon.logmaker.reader.redy.db.RedyGymLogDbReader;

public class Main {

	public static void main(String[] args) throws Exception {
		// final LogReader parser = new RedyGymLogCsvReader();
		// final WorkoutLog parsed = parser.readLog(new File(
		// "C:\\Users\\ethon\\Dropbox\\RedyGymLog.csv"));
		//
		// final Generator gen = new DefaultGenerator();
		// gen.genLastWorkoutToClipboard(parsed);

		final LogReader reader = new RedyGymLogDbReader();
		final WorkoutLog log = reader.readLog(new File("C:\\Users\\ethon\\Dropbox\\RedyGymLog.db"));
		final Generator gen = new DefaultGenerator();
		gen.genLastWorkoutToClipboard(log);
	}

}
