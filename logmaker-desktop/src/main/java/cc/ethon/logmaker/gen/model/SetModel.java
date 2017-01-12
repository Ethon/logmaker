package cc.ethon.logmaker.gen.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import cc.ethon.logmaker.Exercise.ExerciseType;
import cc.ethon.logmaker.Set;
import cc.ethon.logmaker.formula.MaxEstimator;

public class SetModel {

	private final Set set;
	private final MaxEstimator maxEstimator;
	private final WorkoutExerciseModel workoutExercise;

	public SetModel(Set set, MaxEstimator maxEstimator, WorkoutExerciseModel workoutExercise) {
		super();
		this.set = set;
		this.maxEstimator = maxEstimator;
		this.workoutExercise = workoutExercise;
	}

	public Date getTime() {
		final Instant instant = set.getTime().atDate(LocalDate.of(2000, 1, 1)).atZone(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}

	public WeightModel getWeight() {
		return new WeightModel((int) (set.getWeight() * 1000));
	}

	public int getRepetitions() {
		return set.getReps();
	}

	public String getTimeDone() {
		int seconds = set.getTimeDone();
		final int hours = seconds / 3600;
		seconds -= hours * 3600;
		final int minutes = seconds / 60;
		seconds -= minutes * 60;
		return String.format("%dh %dmin %ds", hours, minutes, seconds);
	}

	public String getDistance() {
		int meters = set.getDistance();
		final int kiloMeters = meters / 1000;
		meters -= kiloMeters * 1000;
		return String.format("%dkm %dm", kiloMeters, meters);
	}

	public WeightModel getErm() {
		return new WeightModel((int) (maxEstimator.estimate(set) * 1000));
	}

	public boolean isNoWeight() {
		final ExerciseType type = set.getExercise().getType();
		if (type != ExerciseType.WeightReps && type != ExerciseType.WeightTime) {
			return true;
		}
		return set.getWeight() == 0.0;
	}

	public boolean isErmRecord() {
		return workoutExercise.isErmRecord(set);
	}

	@Override
	public String toString() {
		return "SetModel [set=" + set + "]";
	}

}
