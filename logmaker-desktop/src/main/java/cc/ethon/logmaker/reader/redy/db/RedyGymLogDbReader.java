package cc.ethon.logmaker.reader.redy.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import cc.ethon.logmaker.Exercise;
import cc.ethon.logmaker.Exercise.ExerciseType;
import cc.ethon.logmaker.Set;
import cc.ethon.logmaker.Workout;
import cc.ethon.logmaker.WorkoutLog;
import cc.ethon.logmaker.reader.LogReader;

public class RedyGymLogDbReader implements LogReader {

	private static final int TIME = 5;
	private static final int REPS = 4;
	private static final int WEIGHT_TIME = 3;
	private static final int DISTANCE_TIME_M = 2;
	private static final int DISTANCE_TIME_KM = 1;
	private static final int REPS_WEIGHT = 0;

	private static ExerciseType convertType(ExerciseDomainObject exercise) {
		switch (exercise.getType()) {
		case REPS_WEIGHT:
			return ExerciseType.WeightReps;
		case DISTANCE_TIME_KM:
		case DISTANCE_TIME_M:
			return ExerciseType.DistanceTime;
		case WEIGHT_TIME:
			return ExerciseType.WeightTime;
		case REPS:
			return ExerciseType.Reps;
		case TIME:
			return ExerciseType.Time;
		default:
			throw new IllegalArgumentException("Unsupported exercise type " + exercise.getType());
		}
	}

	public RedyGymLogDbReader() {
	}

	@Override
	public WorkoutLog readLog(File input) throws Exception {
		Class.forName("org.sqlite.JDBC");
		final Connection connection = DriverManager.getConnection("jdbc:sqlite:" + input.getAbsolutePath());

		// Iterate all sets.
		final Map<LocalDate, Workout> workouts = new TreeMap<LocalDate, Workout>();
		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("u-M-d H:m:s");
		try (SetDao setDao = new SetDao(connection)) {
			final ExerciseDao exerciseDao = new ExerciseDao(connection);
			try {
				for (final SetDomainObject set : setDao.getAllSets()) {
					final ExerciseDomainObject exerciseDo = exerciseDao.getExerciseById(set.getExerciseId());

					// Remove microsecond part
					// like 2016-11-07 17:07:47.336000
					final String dateString = set.getDate().split("\\.")[0];
					final LocalDate date = LocalDate.parse(dateString, dtf);
					final LocalTime time = LocalTime.parse(dateString, dtf);
					final int timeDone = set.getTime();
					final int reps = set.getRepeats();
					final double weight = set.getWeightGrams() > 0 ? set.getWeightGrams() * 1.0 / 1000 : 0.0;
					final Exercise exercise = new Exercise(exerciseDo.getName(), convertType(exerciseDo));

					Workout wo = workouts.get(date);
					if (wo == null) {
						try (WorkoutRoutineDao workoutRoutineDao = new WorkoutRoutineDao(connection)) {
							final WorkoutRoutineDomainObject matchingWorkout = workoutRoutineDao.getWorkoutRoutineByLastTrainedDate(date);
							if (matchingWorkout != null) {
								wo = new Workout(Optional.of(matchingWorkout.getName()), date);
							} else {
								wo = new Workout(Optional.empty(), date);
							}
						}

						workouts.put(date, wo);
					}
					wo.addSet(new Set(date, time, exercise, reps, weight, timeDone));
				}
			} finally {
				exerciseDao.close();
			}
		}

		final List<Workout> woList = new ArrayList<Workout>(workouts.values());
		return new WorkoutLog(woList);
	}

}
