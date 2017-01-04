package cc.ethon.logmaker.gen.model;

import java.util.List;
import java.util.stream.Collectors;

import cc.ethon.logmaker.WorkoutExercise;
import cc.ethon.logmaker.formula.MaxEstimator;

public class WorkoutExerciseModel {

	private final WorkoutExercise exercise;
	private final MaxEstimator maxEstimator;

	public WorkoutExerciseModel(WorkoutExercise exercise, MaxEstimator maxEstimator) {
		super();
		this.exercise = exercise;
		this.maxEstimator = maxEstimator;
	}

	public String getName() {
		return exercise.getExercise().getName();
	}

	public List<SetModel> getSets() {
		return exercise.getSets().stream().map(set -> new SetModel(set, maxEstimator)).collect(Collectors.toList());
	}

}
