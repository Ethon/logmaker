package cc.ethon.logmaker.gen;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.OptionalDouble;

import cc.ethon.logmaker.Set;
import cc.ethon.logmaker.Workout;
import cc.ethon.logmaker.WorkoutExercise;
import cc.ethon.logmaker.WorkoutLog;
import cc.ethon.logmaker.formula.MaxEstimator;

public class DefaultGenerator implements Generator {

	private static void genWorkout(PrintStream out, Workout wo, WorkoutLog log, MaxEstimator maxEstimator) {
		final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("E, d.L.u", Locale.GERMAN);
		final DateTimeFormatter durationFormatter = DateTimeFormatter.ofPattern("H'h' m'min'", Locale.GERMAN);
		final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("'['H':'m']'", Locale.GERMAN);

		final DecimalFormat df = new DecimalFormat("0.##");
		out.printf("[size=150][b][u]%s - %s[/u][/b][/size]\n", wo.getDate().format(dateFormatter), wo.getName().isPresent() ? wo.getName().get() : "<>");
		if (wo.getExercises().isEmpty()) {
			out.println();
			return;
		}

		out.println();
		out.println("[b][u]Stats:[/u][/b]");
		out.printf("[i]Duration:[/i] %s\n", wo.getDuration().format(durationFormatter));
		out.printf("[i]Weight lifted:[/i] %skg\n", df.format(wo.getWeightLifted()));
		out.printf("[i]Sets:[/i] %d\n", wo.getSetCount());
		out.println();

		for (final WorkoutExercise exercise : wo.getExercises()) {
			out.printf("[b][u]%s:[/u][/b]\n", exercise.getExercise().getName());

			for (final Set set : exercise.getSets()) {
				if (set.getWeight() > 0.0) {
					String wendlerExtraInfo = "";
					final OptionalDouble record = log.getExerciseErmRecord(set.getExercise(), wo.getExercise(set.getExercise()).getSets(), maxEstimator);
					if (record.isPresent()) {
						final double delta = record.getAsDouble() - set.estimateErm(maxEstimator);
						if (delta > 0) {
							wendlerExtraInfo = String.format(" -> %3skg below record @ %3skg", df.format(delta), df.format(record.getAsDouble()));
						} else if (delta == 0.0) {
							wendlerExtraInfo = " [color=#008000]-> matched record[/color]";
						} else {
							wendlerExtraInfo = String.format(" [color=#008000]-> %3skg above old record @ %3skg[/color]", df.format(Math.abs(delta)),
									df.format(record.getAsDouble()));
						}
					}

					out.printf("%7s %3skg x %2d (Wendler ERM: %3skg%s)\n", set.getTime().format(timeFormatter), df.format(set.getWeight()), set.getReps(),
							df.format(set.estimateErm(maxEstimator)), wendlerExtraInfo);
				} else {
					out.printf("%7s %3skg x %2d\n", set.getTime().format(timeFormatter), df.format(Math.abs(set.getWeight())), set.getReps());
				}
			}
			out.println();
		}
	}

	@Override
	public void gen(PrintStream out, WorkoutLog log, MaxEstimator maxEstimator) {

		for (final Workout wo : log.getWorkouts()) {
			genWorkout(out, wo, log, maxEstimator);
		}
	}

	@Override
	public void genLastWorkout(PrintStream out, WorkoutLog log, MaxEstimator maxEstimator) {
		if (log.getWorkouts().isEmpty()) {
			return;
		}
		genWorkout(out, log.getWorkouts().get(log.getWorkouts().size() - 1), log, maxEstimator);
	}

	@Override
	public void genLastWorkoutToClipboard(WorkoutLog log, MaxEstimator maxEstimator) {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintStream ps = new PrintStream(baos);
		genLastWorkout(ps, log, maxEstimator);

		final Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		final StringSelection data = new StringSelection(baos.toString());
		c.setContents(data, data);
	}

}
