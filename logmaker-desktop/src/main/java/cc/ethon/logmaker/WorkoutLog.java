package cc.ethon.logmaker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalDouble;

import cc.ethon.logmaker.formula.MaxEstimator;

public class WorkoutLog {

	private final List<Workout> log;

	public WorkoutLog() {
		log = new ArrayList<Workout>();
	}

	public WorkoutLog(List<Workout> log) {
		super();
		this.log = log;
	}

	public void addWorkout(Workout workout) {
		log.add(workout);
	}

	public OptionalDouble getExerciseWendlerERMRecord(String exercise, List<Set> except, MaxEstimator estimator) {
		return getSetsByExercise(exercise, except).stream().mapToDouble(set -> set.estimateERM(estimator)).max();
	}

	public List<Workout> getLog() {
		return log;
	}

	public List<Set> getSetsByExercise(String exercise, List<Set> except) {
		final HashSet<Set> exclude = new HashSet<Set>(except);
		final List<Set> result = new ArrayList<Set>();
		for (final Workout wo : log) {
			for (final Set set : wo.getSets()) {
				if (set.getExercise().equals(exercise) && !exclude.contains(set)) {
					result.add(set);
				}
			}
		}
		return result;
	}
}
