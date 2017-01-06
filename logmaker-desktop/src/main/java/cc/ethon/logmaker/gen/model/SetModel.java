package cc.ethon.logmaker.gen.model;

import java.text.DecimalFormat;

import cc.ethon.logmaker.Set;
import cc.ethon.logmaker.formula.MaxEstimator;

public class SetModel {

	private static final DecimalFormat WEIGHT_FORMATTER = new DecimalFormat("0.##");

	private final Set set;
	private final MaxEstimator maxEstimator;
	private final WorkoutExerciseModel workoutExercise;

	public SetModel(Set set, MaxEstimator maxEstimator, WorkoutExerciseModel workoutExercise) {
		super();
		this.set = set;
		this.maxEstimator = maxEstimator;
		this.workoutExercise = workoutExercise;
	}

	public String getTime() {
		return String.format("%02d:%02d", set.getTime().getHour(), set.getTime().getMinute());
	}

	public String getWeight() {
		return WEIGHT_FORMATTER.format(set.getWeight());
	}

	public int getRepetitions() {
		return set.getReps();
	}

	public String getErm() {
		return WEIGHT_FORMATTER.format(maxEstimator.estimate(set));
	}

	public boolean isNoWeight() {
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
