package cc.ethon.logmaker.gen.model;

import java.util.List;
import java.util.stream.Collectors;

import cc.ethon.logmaker.WorkoutLog;

public class WorkoutLogModel {

	private final WorkoutLog log;

	public WorkoutLogModel(WorkoutLog log) {
		this.log = log;
	}

	public List<WorkoutModel> getWorkouts() {
		return log.getWorkouts().stream().map(wo -> new WorkoutModel(wo)).collect(Collectors.toList());
	}

}
