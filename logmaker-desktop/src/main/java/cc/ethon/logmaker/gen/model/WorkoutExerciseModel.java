package cc.ethon.logmaker.gen.model;

import java.time.Duration;
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

	public boolean isTimeOnly() {
		return exercise.getExercise().getType() == ExerciseType.Time;
	}

	public boolean isRepsOnly() {
		return exercise.getExercise().getType() == ExerciseType.Reps;
	}

	public boolean isWeightForReps() {
		return exercise.getExercise().getType() == ExerciseType.WeightReps;
	}

	public boolean isWeightForTime() {
		return exercise.getExercise().getType() == ExerciseType.WeightTime;
	}

	public boolean isDistanceForTime() {
		return exercise.getExercise().getType() == ExerciseType.DistanceTime;
	}

	public WeightModel getTotalWeight() {
		return new WeightModel(exercise.getTotalWeightLifted());
	}

	public int getTotalReps() {
		return exercise.getTotalReps();
	}

	public Duration getDuration() {
		return exercise.getDuration();
	}

	@Override
	public String toString() {
		return "WorkoutExerciseModel [exercise=" + exercise + "]";
	}

}
