package cc.ethon.logmaker.gen.model;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import cc.ethon.logmaker.Exercise.ExerciseType;
import cc.ethon.logmaker.Set;
import cc.ethon.logmaker.WorkoutExercise;
import cc.ethon.logmaker.formula.MaxEstimator;

public class WorkoutExerciseModel {

	private final WorkoutExercise exercise;
	private final MaxEstimator maxEstimator;
	private final WorkoutModel workout;

	public WorkoutExerciseModel(WorkoutExercise exercise, MaxEstimator maxEstimator, WorkoutModel workout) {
		super();
		this.exercise = exercise;
		this.maxEstimator = maxEstimator;
		this.workout = workout;
	}

	public String getName() {
		return exercise.getExercise().getName();
	}

	public List<SetModel> getSets() {
		return exercise.getSets().stream().map(set -> new SetModel(set, maxEstimator, this)).collect(Collectors.toList());
	}

	public boolean isErmRecord(Set set) {
		return workout.isErmRecord(set, exercise.getSets());
	}

	public ExerciseType getType() {
		return exercise.getExercise().getType();
	}

	public boolean isTimeOnly() {
		return getType() == ExerciseType.Time;
	}

	public boolean isRepsOnly() {
		return getType() == ExerciseType.Reps;
	}

	public boolean isWeightForReps() {
		return getType() == ExerciseType.WeightReps;
	}

	public boolean isWeightForTime() {
		return getType() == ExerciseType.WeightTime;
	}

	public boolean isDistanceForTime() {
		return getType() == ExerciseType.DistanceTime;
	}

	public WeightModel getTotalWeight() {
		return new WeightModel(exercise.getTotalWeightLifted());
	}

	public int getTotalReps() {
		return exercise.getTotalReps();
	}

	public Date getTotalTimeDone() {
		int seconds = exercise.getTotalTimeDone();
		final int hours = seconds / 3600;
		seconds -= hours * 3600;
		final int minutes = seconds / 60;
		seconds -= minutes * 60;
		final LocalTime lt = LocalTime.of(hours, minutes, seconds);
		final Instant instant = lt.atDate(LocalDate.of(2000, 1, 1)).atZone(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}

	public DistanceModel getTotalDistance() {
		return new DistanceModel(exercise.getTotalDistance());
	}

	public Duration getDuration() {
		return exercise.getDuration();
	}

	@Override
	public String toString() {
		return "WorkoutExerciseModel [exercise=" + exercise + "]";
	}

}
