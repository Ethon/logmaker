package cc.ethon.logmaker.gen.model;

import cc.ethon.logmaker.Set;

public class SetModel {

	private final Set set;

	public SetModel(Set set) {
		super();
		this.set = set;
	}

	public String getTime() {
		return String.format("%02d:%02d", set.getTime().getHour(), set.getTime().getMinute());
	}

	public double getWeight() {
		return set.getWeight();
	}

	public int getRepetitions() {
		return set.getReps();
	}

}
