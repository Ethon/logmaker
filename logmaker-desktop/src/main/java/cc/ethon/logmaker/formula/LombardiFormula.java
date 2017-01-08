package cc.ethon.logmaker.formula;

import cc.ethon.logmaker.Set;

public class LombardiFormula implements MaxEstimator {

	@Override
	public String getName() {
		return "Lombardi";
	}

	@Override
	public double estimate(Set set) {
		if (set.getReps() == 0) {
			return 0;
		}
		if (set.getReps() == 1) {
			return set.getWeight();
		}
		return set.getWeight() * Math.pow(set.getReps(), 0.10);
	}

	@Override
	public String toString() {
		return getName();
	}

}
