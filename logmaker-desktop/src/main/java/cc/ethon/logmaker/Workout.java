package cc.ethon.logmaker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import cc.ethon.logmaker.formula.MaxEstimator;

public class Workout {

	private final List<Set> sets;
	private final LocalDate date;

	public Workout(LocalDate date) {
		this.date = date;
		sets = new ArrayList<Set>();
	}

	public Workout(LocalDate date, List<Set> sets) {
		super();
		this.date = date;
		this.sets = sets;
	}

	public void addSet(Set set) {
		sets.add(set);
	}

	public Set getBestSetByExercise(String exercise, MaxEstimator estimator) {
		Set best = null;
		double weight = Double.MIN_VALUE;
		for (final Set set : sets) {
			if (set.getExercise().equals(exercise) && set.estimateERM(estimator) > weight) {
				best = set;
				weight = set.estimateERM(estimator);
			}
		}
		return best;
	}

	public LocalDate getDate() {
		return date;
	}

	public LocalTime getDuration() {
		if (sets.isEmpty()) {
			return LocalTime.of(0, 0);
		}
		final LocalTime first = sets.get(0).getTime();
		final LocalTime last = sets.get(sets.size() - 1).getTime();
		int min = (int) first.until(last, ChronoUnit.MINUTES);
		final int hours = min / 60;
		min %= 60;
		return LocalTime.of(hours, min);
	}

	public int getSetCount() {
		return sets.size();
	}

	public List<Set> getSets() {
		return sets;
	}

	public List<Set> getSetsByExercise(String exercise) {
		final List<Set> result = new ArrayList<Set>();
		for (final Set set : sets) {
			if (set.getExercise().equals(exercise)) {
				result.add(set);
			}
		}
		return result;
	}

	public double getWeightLifted() {
		return sets.stream().mapToDouble(Set::getWeightLifted).sum();
	}
}
