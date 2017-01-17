package cc.ethon.logmaker.gen.model;

import java.time.Duration;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import cc.ethon.logmaker.Set;
import cc.ethon.logmaker.Workout;
import cc.ethon.logmaker.formula.MaxEstimator;

public class WorkoutModel {

	private final Workout workout;
	private final MaxEstimator maxEstimator;
	private final WorkoutLogModel workoutLog;

	public WorkoutModel(Workout workout, MaxEstimator maxEstimator, WorkoutLogModel workoutLog) {
		super();
		this.workout = workout;
		this.maxEstimator = maxEstimator;
		this.workoutLog = workoutLog;
	}

	public Date getDate() {
		return Date.from(workout.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public String getName() {
		return workout.getName().isPresent() ? workout.getName().get() : null;
	}

	public Duration getDuration() {
		return workout.getDuration();
	}

	public WeightModel getWeightLifted() {
		return new WeightModel(workout.getWeightLifted());
	}

	public int getSetCount() {
		return workout.getSetCount();
	}

	public List<WorkoutExerciseModel> getExercises() {
		return workout.getExercises().stream().map(exercise -> new WorkoutExerciseModel(exercise, maxEstimator, this)).collect(Collectors.toList());
	}

	public boolean isErmRecord(Set set, List<Set> except) {
		return workoutLog.isErmRecord(set, except);
	}

	@Override
	public String toString() {
		return "WorkoutModel [workout=" + workout + "]";
	}

}
