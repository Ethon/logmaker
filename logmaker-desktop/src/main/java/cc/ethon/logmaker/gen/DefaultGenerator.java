package cc.ethon.logmaker.gen;

import java.io.PrintStream;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.OptionalInt;

import cc.ethon.logmaker.Set;
import cc.ethon.logmaker.Workout;
import cc.ethon.logmaker.WorkoutExercise;
import cc.ethon.logmaker.WorkoutLog;
import cc.ethon.logmaker.formula.MaxEstimator;
import cc.ethon.logmaker.sink.Sink;

@Deprecated
// Deprecated 2017-01-06
// Deprecated DefaultGenerator as the TemplateGenerator already has more
// features and is the better choice for future development
public class DefaultGenerator extends Generator {

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
					final OptionalInt record = log.getExerciseErmRecord(set.getExercise(), wo.getExercise(set.getExercise()).getSets(), maxEstimator);
					if (record.isPresent()) {
						final double recordKg = 1.0 * record.getAsInt() / 1000.0;
						final double delta = 1.0 * (record.getAsInt() - set.estimateErm(maxEstimator)) / 1000.0;
						if (delta > 0) {
							wendlerExtraInfo = String.format(" -> %3skg below record @ %3skg", df.format(delta), df.format(recordKg));
						} else if (delta == 0.0) {
							wendlerExtraInfo = " [color=#008000]-> matched record[/color]";
						} else {
							wendlerExtraInfo = String.format(" [color=#008000]-> %3skg above old record @ %3skg[/color]", df.format(Math.abs(delta)),
									df.format(recordKg));
						}
					}

					final double weightKg = 1.0 * set.getWeight() / 1000.0;
					final double ermKg = 1.0 * set.estimateErm(maxEstimator) / 1000.0;

					out.printf("%7s %3skg x %2d (Wendler ERM: %3skg%s)\n", set.getTime().format(timeFormatter), df.format(weightKg), set.getReps(),
							df.format(ermKg), wendlerExtraInfo);
				} else {
					final double weightKg = 1.0 * set.getWeight() / 1000.0;
					out.printf("%7s %3skg x %2d\n", set.getTime().format(timeFormatter), df.format(Math.abs(weightKg)), set.getReps());
				}
			}
			out.println();
		}
	}

	@Override
	protected void generate(Sink sink, WorkoutLog log, MaxEstimator maxEstimator) throws Exception {
		final PrintStream out = new PrintStream(sink.getOutputStream());
		for (final Workout wo : log.getWorkouts()) {
			genWorkout(out, wo, log, maxEstimator);
		}
		out.flush();
		sink.applyContent();
	}

}
