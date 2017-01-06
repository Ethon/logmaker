package cc.ethon.logmaker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

import cc.ethon.logmaker.formula.MaxEstimator;

public class WorkoutLog {

	private final List<Workout> workouts;

	public WorkoutLog() {
		workouts = new ArrayList<Workout>();
	}

	public WorkoutLog(List<Workout> log) {
		super();
		this.workouts = log;
	}

	public void addWorkout(Workout workout) {
		workouts.add(workout);
	}

	public Optional<Set> getBestSetByExercise(Exercise exercise, List<Set> except, MaxEstimator estimator) {
		final HashSet<Set> exclude = new HashSet<Set>(except);
		return workouts.stream().map(wo -> wo.getBestSetByExercise(exercise, estimator)).filter(set -> set != null && !exclude.contains(set))
				.max((s1, s2) -> estimator.compareSets(s1, s2));
	}

	public OptionalDouble getExerciseErmRecord(Exercise exercise, List<Set> except, MaxEstimator estimator) {
		final Optional<Set> bestSet = getBestSetByExercise(exercise, except, estimator);
		return bestSet.isPresent() ? OptionalDouble.of(bestSet.get().estimateErm(estimator)) : OptionalDouble.empty();
	}

	public List<Workout> getWorkouts() {
		return workouts;
	}

	@Override
	public String toString() {
		return "WorkoutLog [workouts=" + workouts + "]";
	}

}
