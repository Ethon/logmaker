package cc.ethon.logmaker.gen.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import cc.ethon.logmaker.Set;
import cc.ethon.logmaker.WorkoutLog;
import cc.ethon.logmaker.formula.MaxEstimator;
import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class WorkoutLogModel {

	private final WorkoutLog log;
	private final MaxEstimator maxEstimator;

	public WorkoutLogModel(WorkoutLog log, MaxEstimator maxEstimator) {
		super();
		this.log = log;
		this.maxEstimator = maxEstimator;
	}

	public List<WorkoutModel> getWorkouts() {
		return log.getWorkouts().stream().map(wo -> new WorkoutModel(wo, maxEstimator, this)).collect(Collectors.toList());
	}

	public String getErmFormulaName() {
		return maxEstimator.getName();
	}

	public boolean isErmRecord(Set set, List<Set> exclude) {
		final Optional<Set> bestSet = log.getBestSetByExercise(set.getExercise(), exclude, maxEstimator);
		if (bestSet.isPresent()) {
			return maxEstimator.compareSets(set, bestSet.get()) > 0;
		}
		return false;
	}

	public TemplateMethodModelEx getFormatSeconds() {
		return new TemplateMethodModelEx() {
			@Override
			public Object exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
				try {
					final int seconds = ((SimpleNumber) args.get(0)).getAsNumber().intValue();
					final String formatString = ((SimpleScalar) args.get(1)).getAsString();
					return DateTimeFormatter.ofPattern(formatString).format(LocalTime.ofSecondOfDay(seconds));
				} catch (final Exception e) {
					throw new TemplateModelException("Usage: formatSeconds(seconds, formatString)", e);
				}
			}
		};
	}

	@Override
	public String toString() {
		return "WorkoutLogModel [log=" + log + "]";
	}

}
