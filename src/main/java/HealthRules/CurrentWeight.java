package HealthRules;

public class CurrentWeight {
	public int weight;
	public String alertMessage;

	public CurrentWeight(int weight) {
		this.weight = weight;

	}

	public String getAlertMessage() {
		return alertMessage;
	}

	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}
	
	

	
}