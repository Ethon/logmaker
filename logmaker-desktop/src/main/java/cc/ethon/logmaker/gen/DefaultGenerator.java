package cc.ethon.logmaker.gen;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.OptionalDouble;

import cc.ethon.logmaker.Set;
import cc.ethon.logmaker.Workout;
import cc.ethon.logmaker.WorkoutLog;
import cc.ethon.logmaker.formula.MaxEstimator;
import cc.ethon.logmaker.formula.WendlerFormula;

public class DefaultGenerator implements Generator {

	private static void genWorkout(PrintStream out, Workout wo, WorkoutLog log) {
		final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("E, d.L.u", Locale.GERMAN);
		final DateTimeFormatter durationFormatter = DateTimeFormatter.ofPattern("H'h' m'min'", Locale.GERMAN);
		final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("'['H':'m']'", Locale.GERMAN);

		final DecimalFormat df = new DecimalFormat("0.##");
		out.printf("[size=150][b][u]%s - %s[/u][/b][/size]\n", wo.getDate().format(dateFormatter), wo.getName().isPresent() ? wo.getName().get() : "<>");
		if (wo.getSets().isEmpty()) {
			out.println();
			return;
		}

		out.println();
		out.println("[b][u]Stats:[/u][/b]");
		out.printf("[i]Duration:[/i] %s\n", wo.getDuration().format(durationFormatter));
		out.printf("[i]Weight lifted:[/i] %skg\n", df.format(wo.getWeightLifted()));
		out.printf("[i]Sets:[/i] %d\n", wo.getSetCount());

		final MaxEstimator estimator = new WendlerFormula();

		String curEx = null;
		for (final Set set : getSortedSets(wo.getSets())) {
			if (curEx == null || !curEx.equals(set.getExercise())) {
				out.println();
				curEx = set.getExercise();
				out.printf("[b][u]%s:[/u][/b]\n", curEx);
			}

			if (set.getWeight() > 0.0) {
				String wendlerExtraInfo = "";
				final OptionalDouble record = log.getExerciseWendlerERMRecord(set.getExercise(), wo.getSetsByExercise(set.getExercise()), estimator);
				if (record.isPresent()) {
					final double delta = record.getAsDouble() - set.estimateERM(estimator);
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
						df.format(set.estimateERM(estimator)), wendlerExtraInfo);
			} else {
				out.printf("%7s %3skg x %2d\n", set.getTime().format(timeFormatter), df.format(Math.abs(set.getWeight())), set.getReps());
			}
		}
		out.println();
	}

	private static List<Set> getSortedSets(List<Set> in) {
		final Map<String, Integer> exerciseOrder = new HashMap<String, Integer>();
		int curOrder = 0;
		for (final Set set : in) {
			if (!exerciseOrder.containsKey(set.getExercise())) {
				exerciseOrder.put(set.getExercise(), curOrder++);
			}
		}

		final List<Set> result = new ArrayList<Set>(in);
		Collections.sort(result, (a, b) -> {
			final int comp = exerciseOrder.get(a.getExercise()).compareTo(exerciseOrder.get(b.getExercise()));
			if (comp != 0) {
				return comp;
			}
			return a.getTime().compareTo(b.getTime());
		});
		return result;
	}

	@Override
	public void gen(PrintStream out, WorkoutLog log) {

		for (final Workout wo : log.getLog()) {
			genWorkout(out, wo, log);
		}
	}

	@Override
	public void genLastWorkout(PrintStream out, WorkoutLog log) {
		if (log.getLog().isEmpty()) {
			return;
		}
		genWorkout(out, log.getLog().get(log.getLog().size() - 1), log);
	}

	@Override
	public void genLastWorkoutToClipboard(WorkoutLog log) {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintStream ps = new PrintStream(baos);
		genLastWorkout(ps, log);

		final Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		final StringSelection data = new StringSelection(baos.toString());
		c.setContents(data, data);
	}

	private String makePaddedString(int number, int padTo, char padWith) {
		final String asString = String.valueOf(number);
		final StringBuilder builder = new StringBuilder();
		for (int i = asString.length(); i < padTo; ++i) {
			builder.append(padWith);
		}
		builder.append(asString);
		return builder.toString();
	}

	private String makeTimeString(LocalTime time) {
		return String.format("[%s:%s]", makePaddedString(time.getHour(), 2, ' '), makePaddedString(time.getMinute(), 2, ' '));
	}
}
