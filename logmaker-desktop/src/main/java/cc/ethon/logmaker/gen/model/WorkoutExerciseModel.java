package cc.ethon.logmaker.gen.model;

import java.util.List;
import java.util.stream.Collectors;

import cc.ethon.logmaker.WorkoutExercise;

public class WorkoutExerciseModel {

	private final WorkoutExercise exercise;

	public WorkoutExerciseModel(WorkoutExercise exercise) {
		super();
		this.exercise = exercise;
	}

	public String getName() {
		return exercise.getExercise().getName();
	}

	public List<SetModel> getSets() {
		return exercise.getSets().stream().map(set -> new SetModel(set)).collect(Collectors.toList());
	}

}
