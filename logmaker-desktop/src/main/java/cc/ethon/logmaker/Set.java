package cc.ethon.logmaker;

import java.time.LocalDate;
import java.time.LocalTime;

import cc.ethon.logmaker.formula.MaxEstimator;

public class Set {

	private LocalDate date;
	private LocalTime time;
	private Exercise exercise;
	private int reps;
	private double weight;
	private int timeDone;

	public Set(LocalDate date, LocalTime time, Exercise exercise, int reps, double weight, int timeDone) {
		super();
		this.date = date;
		this.time = time;
		this.exercise = exercise;
		this.reps = reps;
		this.weight = Math.abs(weight);
		this.timeDone = timeDone;
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

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	public void setReps(int reps) {
		this.reps = reps;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getTimeDone() {
		return timeDone;
	}

	public void setTimeDone(int timeDone) {
		this.timeDone = timeDone;
	}

	@Override
	public String toString() {
		return "Set [date=" + date + ", time=" + time + ", exercise=" + exercise + ", reps=" + reps + ", weight=" + weight + ", timeDone=" + timeDone + "]";
	}

}
