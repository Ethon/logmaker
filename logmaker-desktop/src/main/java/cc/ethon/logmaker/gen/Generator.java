package cc.ethon.logmaker.gen;

import java.io.PrintStream;

import cc.ethon.logmaker.WorkoutLog;

public interface Generator {

	public void gen(PrintStream out, WorkoutLog log);

	public void genLastWorkout(PrintStream out, WorkoutLog log);

	public void genLastWorkoutToClipboard(WorkoutLog log);
}
