package cc.ethon.logmaker;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cc.ethon.logmaker.formula.MaxEstimator;

public class Workout {

	private final Optional<String> name;
	private final Map<Exercise, WorkoutExercise> exercises;
	private final LocalDate date;

	// Cache these to avoid excessive computing at every access.
	private boolean changed;
	private List<WorkoutExercise> sortedExercises;

	private List<WorkoutExercise> sortExercises() {
		final List<WorkoutExercise> result = new ArrayList<WorkoutExercise>(exercises.values());

		// First sort exercises by their first set.
		Collections.sort(result, (ex1, ex2) -> ex1.getTemporallyFirstSet().getTime().compareTo(ex2.getTemporallyFirstSet().getTime()));

		// Then sort the sets of all exercises.
		result.stream().forEach(ex -> ex.sortSets());

		return result;
	}

	public Workout(Optional<String> name, LocalDate date) {
		this(name, date, new HashMap<Exercise, WorkoutExercise>());
	}

	public Workout(Optional<String> name, LocalDate date, Map<Exercise, WorkoutExercise> exercises) {
		this.name = name;
		this.date = date;
		this.exercises = exercises;
	}

	public void addSet(Set set) {
		if (!exercises.containsKey(set.getExercise())) {
			exercises.put(set.getExercise(), new WorkoutExercise(set.getExercise()));
		}
		exercises.get(set.getExercise()).addSet(set);
		changed = true;
	}

	public List<WorkoutExercise> getExercises() {
		if (sortedExercises == null || changed == true) {
			sortedExercises = sortExercises();
			changed = false;
		}
		return sortedExercises;
	}

	public WorkoutExercise getFirstExercise() {
		return getExercises().isEmpty() ? null : getExercises().get(0);
	}

	public WorkoutExercise getLastExercise() {
		return getExercises().isEmpty() ? null : getExercises().get(getExercises().size() - 1);
	}

	public Set getBestSetByExercise(Exercise exercise, MaxEstimator estimator) {
		if (!exercises.containsKey(exercise)) {
			return null;
		}
		return exercises.get(exercise).getBestSet(estimator);
	}

	public LocalDate getDate() {
		return date;
	}

	public Duration getDuration() {
		if (getExercises().isEmpty()) {
			return Duration.ofSeconds(0);
		}
		final Set firstSet = getFirstExercise().getTemporallyFirstSet();
		final LocalTime first = firstSet.getTime();
		final LocalTime last = getLastExercise().getTemporallyLastSet().getTime();
		int seconds = (int) first.until(last, ChronoUnit.SECONDS);
		if (firstSet.getTimeDone() > 0) {
			seconds += firstSet.getTimeDone();
		}
		return Duration.ofSeconds(seconds);
	}

	public Duration getTimeBetweenExercises() {
		if (getExercises().isEmpty()) {
			return Duration.ofSeconds(0);
		}

		int seconds = 0;
		WorkoutExercise lastExercise = null;
		for (final WorkoutExercise exercise : getExercises()) {
			if (lastExercise != null) {
				final LocalTime last = lastExercise.getTemporallyLastSet().getTime();
				final LocalTime first = exercise.getTemporallyFirstSet().getTime();
				if (last.until(first, ChronoUnit.SECONDS) > 0) {
					seconds += last.until(first, ChronoUnit.SECONDS);
				}
			}
			lastExercise = exercise;
		}
		return Duration.ofSeconds(seconds);
	}

	public int getSetCount() {
		return getExercises().stream().mapToInt(ex -> ex.getSets().size()).sum();
	}

	public WorkoutExercise getExercise(Exercise exercise) {
		return exercises.get(exercise);
	}

	public int getWeightLifted() {
		return exercises.values().stream().mapToInt(exercise -> exercise.getTotalWeightLifted()).sum();
	}

	public Optional<String> getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Workout [name=" + name + ", exercises=" + exercises + ", date=" + date + "]";
	}

}
