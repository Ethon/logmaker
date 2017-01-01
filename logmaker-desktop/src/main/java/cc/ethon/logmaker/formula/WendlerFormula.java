package cc.ethon.logmaker.formula;

import cc.ethon.logmaker.Set;

public class WendlerFormula implements MaxEstimator {

	@Override
	public double estimate(Set set) {
		if (set.getReps() == 0) {
			return 0.0;
		}
		if (set.getReps() == 1) {
			return set.getWeight();
		}
		return set.getWeight() * set.getReps() * 0.0333 + set.getWeight();
	}

}
