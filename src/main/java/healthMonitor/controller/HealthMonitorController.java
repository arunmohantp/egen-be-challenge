package healthMonitor.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import HealthRules.WeightRulesService;
import healthMonitor.dataManagment.HealthStatusProcessor;

@RestController
public class HealthMonitorController {

	
	@Autowired
	HealthStatusProcessor app;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public CommonMessage postit(@RequestBody HealthStatusRequestPojo healthStatus) {
		CommonMessage commonMessage= new CommonMessage();
		try{
		app.addData(healthStatus.getTimeStamp(),healthStatus.getValue());
		commonMessage.setMessage("Success");
		}catch(Exception e){
			commonMessage.setMessage("Error inserting data");
			
		}
		return commonMessage;
	}
	
	@RequestMapping(value = { "/read" })
	public List<HealthStatusResponsePojo> getAllMetricsAndAlerts() {
		return app.showAllValues();
	}

	
	@RequestMapping("/readByTimeRange")
	public List<HealthStatusResponsePojo> greeting(@RequestParam(value = "start") Long start,
			@RequestParam(value = "end") Long end) {
		return app.getByDateTime(start, end);
		
	}

	@RequestMapping
	public String defaultMethod() {
		return "I can handle only these url patters:-"
				+ "http://localhost:port/read, "
				+ "http://localhost:port/readByTimeRange , "
				+ "http://localhost:port/create";
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public CommonMessage handleException(Exception e) {
	    return new CommonMessage("Something bad has happened");
	}
}
