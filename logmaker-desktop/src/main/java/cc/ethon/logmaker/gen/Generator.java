package cc.ethon.logmaker.gen;

import java.io.PrintStream;

import cc.ethon.logmaker.WorkoutLog;
import cc.ethon.logmaker.formula.MaxEstimator;

public interface Generator {

	public void gen(PrintStream out, WorkoutLog log, MaxEstimator maxEstimator) throws Exception;

	public void genLastWorkout(PrintStream out, WorkoutLog log, MaxEstimator maxEstimator) throws Exception;

	public void genLastWorkoutToClipboard(WorkoutLog log, MaxEstimator maxEstimator) throws Exception;
}
