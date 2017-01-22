package cc.ethon.logmaker.reader.redy.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import cc.ethon.logmaker.Exercise;
import cc.ethon.logmaker.Set;
import cc.ethon.logmaker.Workout;
import cc.ethon.logmaker.WorkoutLog;
import cc.ethon.logmaker.reader.LogReader;
import cc.ethon.logmaker.reader.ReaderUtil;

public class RedyGymLogCsvReader implements LogReader {

	private final File exportFile;

	private static String getSeparator(BufferedReader in) throws IOException {
		final String line = in.readLine();
		if (!line.startsWith("sep=")) {
			throw new IllegalArgumentException("Could not read separator from stream");
		}
		return line.split("=")[1].trim();
	}

	public RedyGymLogCsvReader(File exportFile) {
		super();
		this.exportFile = exportFile;
	}

	@Override
	public WorkoutLog readLog() throws IOException, ParseException {
		final BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(exportFile)));
		final String separator = getSeparator(in);

		// Skip description line.
		in.readLine();

		// Iterate all lines.
		final Map<LocalDate, Workout> workouts = new TreeMap<LocalDate, Workout>();
		String line;
		final NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("u/M/d H:m");
		while ((line = in.readLine()) != null && !line.isEmpty()) {
			final String[] parts = processLine(line, separator);

			final LocalDate date = LocalDate.parse(parts[0], dtf);
			final LocalTime time = LocalTime.parse(parts[0], dtf);

			final boolean hasReps = !parts[2].isEmpty();
			final int reps = hasReps ? Integer.valueOf(parts[2]) : 0;

			final boolean hasWeight = !parts[3].isEmpty();
			final int weight = hasWeight ? (int) (nf.parse(parts[3]).doubleValue() * 1000) : 0;

			final boolean hasTimeDone = !parts[4].isEmpty();
			int timeDone = 0;
			if (hasTimeDone) {
				final String[] timeParts = parts[4].split(":");
				int i = 0;
				final int hours = timeParts.length == 3 ? Integer.valueOf(timeParts[i++]) : 0;
				final int minutes = Integer.valueOf(timeParts[i++]);
				final int seconds = Integer.valueOf(timeParts[i]);
				timeDone = hours * 3600 + minutes * 60 + seconds;
			}

			final boolean hasDistance = !parts[5].isEmpty();
			final int distance = hasDistance ? Integer.valueOf(parts[5]) : 0;

			final Exercise exercise = new Exercise(parts[1], ReaderUtil.determineExerciseType(hasReps, hasWeight, hasTimeDone, hasDistance));

			Workout wo = workouts.get(date);
			if (wo == null) {
				wo = new Workout(Optional.empty(), date);
				workouts.put(date, wo);
			}

			wo.addSet(new Set(date, time, exercise, reps, weight, timeDone, distance));
		}

		final List<Workout> woList = new ArrayList<Workout>(workouts.values());
		return new WorkoutLog(woList);
	}

	private String[] processLine(String line, String separator) throws ParseException {
		final String[] parts = line.split(separator, -10);
		if (parts.length < 7) {
			throw new ParseException("Illegal line: Error tokenizing", 0);
		}

		final List<String> result = new ArrayList<String>();
		String merged = "";
		for (final String part : parts) {
			if (merged.length() == 0) {
				if (!part.contains("\"")) {
					result.add(part);
				} else {
					final int pos = part.indexOf('"');
					if (pos != 0) {
						throw new ParseException("Illegal cell: Delimiter expected to start cell", 0);
					}
					merged = part + ",";
				}
			} else {
				if (!part.contains("\"")) {
					merged = merged + part + ",";
				} else {
					final int pos = part.indexOf('"');
					if (pos != part.length() - 1) {
						throw new ParseException("Illegal cell: Delimiter expected to end cell", 0);
					}
					merged = merged + part + ",";
					result.add(merged.substring(1, merged.length() - 1));
					merged = "";
				}
			}
		}
		return result.toArray(new String[result.size()]);
	}

}
