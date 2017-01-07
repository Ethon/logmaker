package cc.ethon.logmaker.gen;

import cc.ethon.logmaker.WorkoutLog;
import cc.ethon.logmaker.formula.MaxEstimator;

public abstract class Generator {

	public static final int ALL_WORKOUTS = -1;

	protected abstract void generate(Sink sink, WorkoutLog log, MaxEstimator maxEstimator) throws Exception;

	public void generate(Sink sink, WorkoutLog log, MaxEstimator maxEstimator, int count) throws Exception {
		final int workoutCount = log.getWorkouts().size();
		final int toGenerate = count >= 0 ? Math.min(workoutCount, count) : workoutCount;
		WorkoutLog logToGenerate = log;
		if (toGenerate != workoutCount) {
			logToGenerate = new WorkoutLog();
			for (int i = toGenerate; i > 0; --i) {
				logToGenerate.addWorkout(log.getWorkouts().get(workoutCount - i));
			}
		}
		generate(sink, logToGenerate, maxEstimator);
	}
}
