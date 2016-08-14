package healthMonitor.controller;

public class HealthStatusRequestPojo {

    Long timeStamp;
    Integer value;
    
    public HealthStatusRequestPojo()
    {
    	
    }

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
    
  
}