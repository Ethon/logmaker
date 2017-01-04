package cc.ethon.logmaker.gen.model;

import java.util.List;
import java.util.stream.Collectors;

import cc.ethon.logmaker.WorkoutLog;
import cc.ethon.logmaker.formula.MaxEstimator;

public class WorkoutLogModel {

	private final WorkoutLog log;
	private final MaxEstimator maxEstimator;

	public WorkoutLogModel(WorkoutLog log, MaxEstimator maxEstimator) {
		super();
		this.log = log;
		this.maxEstimator = maxEstimator;
	}

	public List<WorkoutModel> getWorkouts() {
		return log.getWorkouts().stream().map(wo -> new WorkoutModel(wo, maxEstimator)).collect(Collectors.toList());
	}

	public String getErmFormulaName() {
		return maxEstimator.getName();
	}

}
