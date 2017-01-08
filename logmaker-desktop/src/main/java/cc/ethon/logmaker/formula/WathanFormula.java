package cc.ethon.logmaker.formula;

import cc.ethon.logmaker.Set;

public class WathanFormula implements MaxEstimator {

	@Override
	public String getName() {
		return "Wathan";
	}

	@Override
	public double estimate(Set set) {
		if (set.getReps() == 0) {
			return 0;
		}
		if (set.getReps() == 1) {
			return set.getWeight();
		}
		return 100.0 * set.getWeight() / (48.8 + 53.8 * Math.pow(Math.E, -0.075 * set.getReps()));
	}

	@Override
	public String toString() {
		return getName();
	}

}
