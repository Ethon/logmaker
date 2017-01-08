package cc.ethon.logmaker.formula;

import cc.ethon.logmaker.Set;

public class McGlothinFormula implements MaxEstimator {

	@Override
	public String getName() {
		return "McGlothin";
	}

	@Override
	public double estimate(Set set) {
		if (set.getReps() == 0) {
			return 0;
		}
		if (set.getReps() == 1) {
			return set.getWeight();
		}
		return 100.0 * set.getWeight() / (101.3 - 2.67123 * set.getReps());
	}

	@Override
	public String toString() {
		return getName();
	}

}
