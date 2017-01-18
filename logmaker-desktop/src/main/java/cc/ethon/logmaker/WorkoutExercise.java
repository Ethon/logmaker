package cc.ethon.logmaker;

import java.time.Duration;
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

	public int getTotalWeightLifted() {
		return sets.stream().mapToInt(set -> set.getWeightLifted()).sum();
	}

	public int getTotalReps() {
		return sets.stream().mapToInt(set -> set.getReps()).sum();
	}

	public int getTotalTimeDone() {
		return sets.stream().mapToInt(set -> set.getTimeDone()).sum();
	}

	public Duration getDuration() {
		Duration duration = Duration.between(getTemporallyFirstSet().getTime(), getTemporallyLastSet().getTime());
		final Set firstSet = getTemporallyFirstSet();
		if (firstSet.getTimeDone() > 0) {
			duration = duration.plusSeconds(firstSet.getTimeDone());
		}
		return duration;
	}

	public void sortSets() {
		Collections.sort(sets, (s1, s2) -> s1.getTime().compareTo(s2.getTime()));
	}

	@Override
	public String toString() {
		return "WorkoutExercise [exercise=" + exercise + ", sets=" + sets + "]";
	}

}
