package cc.ethon.logmaker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cc.ethon.logmaker.formula.MaxEstimator;

public class WorkoutExercise {

	private final Exercise exercise;
	private final List<Set> sets;

	public WorkoutExercise(Exercise exercise) {
		this(exercise, new ArrayList<Set>());
	}

	public WorkoutExercise(Exercise exercise, List<Set> sets) {
		super();
		this.exercise = exercise;
		this.sets = sets;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void addSet(Set set) {
		sets.add(set);
	}

	public List<Set> getSets() {
		return sets;
	}

	public Set getTemporallyFirstSet() {
		return sets.stream().min((s1, s2) -> s1.getTime().compareTo(s2.getTime())).get();
	}

	public Set getTemporallyLastSet() {
		return sets.stream().max((s1, s2) -> s1.getTime().compareTo(s2.getTime())).get();
	}

	public Set getBestSet(MaxEstimator maxEstimator) {
		return sets.stream().max((s1, s2) -> Double.valueOf(maxEstimator.estimate(s1)).compareTo(maxEstimator.estimate(s2))).get();
	}

	public double getWeightLifted() {
		return sets.stream().mapToDouble(set -> set.getWeightLifted()).sum();
	}

	public void sortSets() {
		Collections.sort(sets, (s1, s2) -> s1.getTime().compareTo(s2.getTime()));
	}

}
