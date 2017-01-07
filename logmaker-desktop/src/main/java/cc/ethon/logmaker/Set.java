package cc.ethon.logmaker;

import java.time.LocalDate;
import java.time.LocalTime;

import cc.ethon.logmaker.formula.MaxEstimator;

public class Set {

	private final LocalDate date;
	private final LocalTime time;
	private final Exercise exercise;
	private final int reps;
	private final double weight;
	private final int timeDone;
	private final int distance;

	public Set(LocalDate date, LocalTime time, Exercise exercise, int reps, double weight, int timeDone, int distance) {
		super();
		this.date = date;
		this.time = time;
		this.exercise = exercise;
		this.reps = reps;
		this.weight = Math.abs(weight);
		this.timeDone = timeDone;
		this.distance = distance;
	}

	public double estimateErm(MaxEstimator estimator) {
		return estimator.estimate(this);
	}

	public LocalDate getDate() {
		return date;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public int getReps() {
		return reps;
	}

	public LocalTime getTime() {
		return time;
	}

	public double getWeight() {
		return weight;
	}

	public double getWeightLifted() {
		return getWeight() * getReps();
	}

	public int getTimeDone() {
		return timeDone;
	}

	public int getDistance() {
		return distance;
	}

	@Override
	public String toString() {
		return "Set [date=" + date + ", time=" + time + ", exercise=" + exercise + ", reps=" + reps + ", weight=" + weight + ", timeDone=" + timeDone
				+ ", distance=" + distance + "]";
	}

}
