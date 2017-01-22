package cc.ethon.logmaker.reader.strong;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import cc.ethon.logmaker.Constants;
import cc.ethon.logmaker.Exercise;
import cc.ethon.logmaker.Exercise.ExerciseType;
import cc.ethon.logmaker.Set;
import cc.ethon.logmaker.Workout;
import cc.ethon.logmaker.WorkoutLog;
import cc.ethon.logmaker.reader.LogReader;
import cc.ethon.logmaker.reader.ReaderUtil;

public class StrongCsvReader implements LogReader {

	private static final int CELL_SECONDS = 7;
	private static final int CELL_KILOMETERS = 6;
	private static final int CELL_REPS = 5;
	private static final int CELL_KILOGRAMS = 4;
	private static final int CELL_EXERCISE_NAME = 2;
	private static final int CELL_WORKOUT_NAME = 1;

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private final File csvExportFile;

	public StrongCsvReader(File csvExportFile) {
		super();
		this.csvExportFile = csvExportFile;
	}

	@Override
	public WorkoutLog readLog() throws Exception {
		final WorkoutLog workoutLog = new WorkoutLog();
		try (CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(csvExportFile)), CSVFormat.RFC4180)) {
			final Iterator<CSVRecord> iterator = parser.iterator();
			iterator.next(); // Skip header

			Workout workout = null;
			while (iterator.hasNext()) {
				final CSVRecord rec = iterator.next();
				final LocalDateTime dateTime = LocalDateTime.parse(rec.get(0), DATE_FORMATTER);
				final LocalDate date = LocalDate.from(dateTime);
				final LocalTime time = LocalTime.from(dateTime);
				final String workoutName = rec.get(CELL_WORKOUT_NAME);
				final String exerciseName = rec.get(CELL_EXERCISE_NAME);
				final int grams = (int) (Double.valueOf(rec.get(CELL_KILOGRAMS)) * Constants.GRAMS_PER_KILOGRAM);
				final int reps = Integer.valueOf(rec.get(CELL_REPS));
				final int meters = (int) (Double.valueOf(rec.get(CELL_KILOMETERS)) * Constants.METERS_PER_KILOMETER);
				final int seconds = Integer.valueOf(rec.get(CELL_SECONDS));
				final ExerciseType exerciseType = ReaderUtil.determineExerciseType(reps != 0, grams != 0, seconds != 0, meters != 0);
				final Exercise exercise = new Exercise(exerciseName, exerciseType);

				if (workout == null) {
					workout = new Workout(Optional.of(workoutName), date);
				} else if (!workout.getName().get().equals(workoutName) || !date.equals(workout.getDate())) {
					workoutLog.addWorkout(workout);
					workout = new Workout(Optional.of(workoutName), date);
				}
				workout.addSet(new Set(date, time, exercise, reps, grams, seconds, meters));
			}
			if (!workout.getExercises().isEmpty()) {
				workoutLog.addWorkout(workout);
			}
		}

		return workoutLog;
	}

}
