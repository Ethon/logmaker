package cc.ethon.logmaker.gen.model;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import cc.ethon.logmaker.Workout;

public class WorkoutModel {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("E, d.L.u", Locale.getDefault());

	private final Workout workout;

	public WorkoutModel(Workout workout) {
		this.workout = workout;
	}

	public String getDate() {
		return workout.getDate().format(DATE_FORMATTER);
	}

	public String getName() {
		return workout.getName().isPresent() ? workout.getName().get() : null;
	}

}
