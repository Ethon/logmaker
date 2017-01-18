package cc.ethon.logmaker.gen.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
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
		return new WeightModel(set.getWeight());
	}

	public int getRepetitions() {
		return set.getReps();
	}

	public Date getTimeDone() {
		int seconds = set.getTimeDone();
		final int hours = seconds / 3600;
		seconds -= hours * 3600;
		final int minutes = seconds / 60;
		seconds -= minutes * 60;
		final LocalTime lt = LocalTime.of(hours, minutes, seconds);
		final Instant instant = lt.atDate(LocalDate.of(2000, 1, 1)).atZone(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}

	public DistanceModel getDistance() {
		return new DistanceModel(set.getDistance());
	}

	public WeightModel getErm() {
		return new WeightModel(set.estimateErm(maxEstimator));
	}

	public boolean isNoWeight() {
		final ExerciseType type = set.getExercise().getType();
		if (type != ExerciseType.WeightReps && type != ExerciseType.WeightTime) {
			return true;
		}
		return set.getWeight() == 0;
	}

	public boolean isErmRecord() {
		return workoutExercise.isErmRecord(set);
	}

	@Override
	public String toString() {
		return "SetModel [set=" + set + "]";
	}

}
