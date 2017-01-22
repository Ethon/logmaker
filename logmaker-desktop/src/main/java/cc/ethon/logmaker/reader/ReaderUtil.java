package cc.ethon.logmaker.reader;

import cc.ethon.logmaker.Exercise.ExerciseType;

public class ReaderUtil {

	public static ExerciseType determineExerciseType(boolean hasReps, boolean hasWeight, boolean hasTimeDone, boolean hasDistance) {
		if (hasReps) {
			if (hasWeight) {
				return ExerciseType.WeightReps;
			} else {
				return ExerciseType.Reps;
			}
		} else if (hasTimeDone) {
			if (hasDistance) {
				return ExerciseType.DistanceTime;
			} else if (hasWeight) {
				return ExerciseType.WeightTime;
			} else {
				return ExerciseType.Time;
			}
		}
		throw new IllegalArgumentException("Unsupported exercise type");
	}

}
