package healthMonitor.controller;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
public class HealthStatusResponsePojo {
	Date date;
	String alert;
	String weight;
	

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAlert() {
		return alert;
	}
	public void setAlert(String alert) {
		this.alert = alert;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	

}
