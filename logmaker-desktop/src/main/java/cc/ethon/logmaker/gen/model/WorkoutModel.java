package cc.ethon.logmaker.gen.model;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import cc.ethon.logmaker.Set;
import cc.ethon.logmaker.Workout;
import cc.ethon.logmaker.formula.MaxEstimator;

public class WorkoutModel {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("E, d.L.u", Locale.getDefault());
	private static final DateTimeFormatter DURATION_FORMATTER = DateTimeFormatter.ofPattern("H'h' m'min'", Locale.getDefault());
	private static final DecimalFormat WEIGHT_FORMATTER = new DecimalFormat("0.##");

	private final Workout workout;
	private final MaxEstimator maxEstimator;
	private final WorkoutLogModel workoutLog;

	public WorkoutModel(Workout workout, MaxEstimator maxEstimator, WorkoutLogModel workoutLog) {
		super();
		this.workout = workout;
		this.maxEstimator = maxEstimator;
		this.workoutLog = workoutLog;
	}

	public String getDate() {
		return workout.getDate().format(DATE_FORMATTER);
	}

	public String getName() {
		return workout.getName().isPresent() ? workout.getName().get() : null;
	}

	public String getDuration() {
		return workout.getDuration().format(DURATION_FORMATTER);
	}

	public String getWeightLifted() {
		return WEIGHT_FORMATTER.format(workout.getWeightLifted());
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

}
