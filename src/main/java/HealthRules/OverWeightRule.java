package HealthRules;

public class OverWeightRule extends BasicWeightRule {

	@Override
	public boolean evaluate() {
		if (currentWeight == null)
			return false;

		int markerWeight = Constants.base_weight + Constants.base_weight * (Constants.marker_percentage / 100);
		if (currentWeight.weight > markerWeight) {
			return true;
		}

		return false;
	}

	@Override
	public String getName() {
		return "Over Weight";
	}

	@Override
	public void execute() {
		currentWeight.setAlertMessage("Over Weight");
	}
}