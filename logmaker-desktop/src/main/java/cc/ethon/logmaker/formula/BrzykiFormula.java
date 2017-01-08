package cc.ethon.logmaker.formula;

import cc.ethon.logmaker.Set;

public class BrzykiFormula implements MaxEstimator {

	@Override
	public String getName() {
		return "Brzycki";
	}

	@Override
	public double estimate(Set set) {
		if (set.getReps() == 0) {
			return 0;
		}
		if (set.getReps() == 1) {
			return set.getWeight();
		}
		return set.getWeight() * (36.0 / (37.0 - set.getReps()));
	}

	@Override
	public String toString() {
		return getName();
	}

}
