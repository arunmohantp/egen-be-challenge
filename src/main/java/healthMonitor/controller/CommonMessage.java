package healthMonitor.controller;

public class CommonMessage {
	String message;
	
	public CommonMessage(String msg){
		this.message=msg;
	}
	
	public CommonMessage(){
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
