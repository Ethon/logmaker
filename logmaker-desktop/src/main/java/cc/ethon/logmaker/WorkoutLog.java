package cc.ethon.logmaker;

import java.util.ArrayList;
import java.util.List;
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

	public OptionalDouble getExerciseErmRecord(Exercise exercise, List<Set> except, MaxEstimator estimator) {
		return workouts.stream().map(wo -> wo.getBestSetByExercise(exercise, estimator)).filter(set -> set != null).mapToDouble(set -> estimator.estimate(set))
				.max();
	}

	public List<Workout> getWorkouts() {
		return workouts;
	}

}
