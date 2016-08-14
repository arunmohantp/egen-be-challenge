package healthMonitor.dataManagment;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import HealthRules.WeightRulesService;
import healthMonitor.controller.HealthStatusResponsePojo;

@Service
public class HealthStatusProcessor {

	private MorphiaService morphiaService;
	private UserDAO userDAO;

	@Autowired
	public HealthStatusProcessor(MorphiaService morphiaService) {
		this.morphiaService = morphiaService;
		this.userDAO = new UserDAOImpl(User.class, morphiaService.getDatastore());
	}

	public void addData(Long timeStamp, Integer weight) {

		// Activate Rules Engine
		WeightRulesService rulesService = new WeightRulesService();
		String alertForCurrentWeight = rulesService.activateWeightRulesEngine(weight);

		Timestamp timestamp = new Timestamp(timeStamp);

		Date date = new Date(timestamp.getYear(), timestamp.getMonth(), 3);
		int seconds = timestamp.getSeconds() + (timestamp.getMinutes() * 60);

		int hour = timestamp.getHours();

		User.DailyHealthStatus.HourlyHealthStatus.HealthData healthData = buildHealthStatus(seconds,
				alertForCurrentWeight, weight);
		User.DailyHealthStatus.HourlyHealthStatus hourlyHealthStatus = createHourlyHealthStatus(seconds, hour,
				healthData);

		if (userDAO.hasCurrentHourAlreadyInDb(date, hour)) {
			userDAO.updateHealthStatusBySeconds(date, healthData, seconds, hour);

		} else if (userDAO.hasCurrentDateAlreadyInDb(date)) {
			userDAO.updateHourlyHealthStatus(date, hourlyHealthStatus);
		} else {

			User.DailyHealthStatus dailyHealthStatus = createDailyHealthStatus(date, hourlyHealthStatus);

			List<User.DailyHealthStatus> dailylist = new ArrayList<User.DailyHealthStatus>();
			dailylist.add(dailyHealthStatus);

			User user1 = new User(dailylist, "userId");

			userDAO.save(user1);

		}

		showAllValues();

	}

	private User.DailyHealthStatus createDailyHealthStatus(Date date,
			User.DailyHealthStatus.HourlyHealthStatus hourlyHealthStatus) {
		User.DailyHealthStatus dailyHealthStatus = new User.DailyHealthStatus();
		dailyHealthStatus.setDay(date);
		List<User.DailyHealthStatus.HourlyHealthStatus> hours = new ArrayList<User.DailyHealthStatus.HourlyHealthStatus>();
		hours.add(hourlyHealthStatus);
		dailyHealthStatus.setHourly_status(hours);
		return dailyHealthStatus;
	}

	private User.DailyHealthStatus.HourlyHealthStatus createHourlyHealthStatus(int seconds, int hour,
			User.DailyHealthStatus.HourlyHealthStatus.HealthData healthData) {
		User.DailyHealthStatus.HourlyHealthStatus hourlyHealthStatus = new User.DailyHealthStatus.HourlyHealthStatus();

		hourlyHealthStatus.setHour(hour);
		hourlyHealthStatus.setHealthData(createHealthDataMap(seconds, healthData));
		return hourlyHealthStatus;
	}

	private Map<Integer, User.DailyHealthStatus.HourlyHealthStatus.HealthData> createHealthDataMap(int seconds,
			User.DailyHealthStatus.HourlyHealthStatus.HealthData healthData) {
		Map<Integer, User.DailyHealthStatus.HourlyHealthStatus.HealthData> healthMap = new HashMap<>();
		healthMap.put(seconds, healthData);
		return healthMap;
	}

	public User.DailyHealthStatus.HourlyHealthStatus.HealthData buildHealthStatus(Integer second, String alert,
			Integer weight) {
		User.DailyHealthStatus.HourlyHealthStatus.HealthData data = new User.DailyHealthStatus.HourlyHealthStatus.HealthData();
		data.setWeight(weight);
		data.setRule(alert);
		return data;
	}

	public List<HealthStatusResponsePojo> showAllValues() {

		User values = userDAO.getAll("arun");
		List<HealthStatusResponsePojo> healthStatusResponsePojos = createHealthStatusResponseList(values);
		return healthStatusResponsePojos;
	}

	private List<HealthStatusResponsePojo> createHealthStatusResponseList(User values) {
		List<HealthStatusResponsePojo> healthStatusResponsePojos = new ArrayList<>();
		List<User.DailyHealthStatus> dailyStatus = values.getdaily_health_status();
		for (User.DailyHealthStatus daily : dailyStatus) {
			System.out.print("For day:" + daily.day);

			List<User.DailyHealthStatus.HourlyHealthStatus> hourlyHealthStatus = daily.getHourly_status();

			for (User.DailyHealthStatus.HourlyHealthStatus hourly : hourlyHealthStatus) {
				System.out.print("For Hour:" + hourly.hour);

				Set<Integer> seconds = hourly.healthData.keySet();
				for (Integer s : seconds) {
					User.DailyHealthStatus.HourlyHealthStatus.HealthData hd = hourly.healthData.get(s);
					System.out.println("For Second:" + s + "--" + hd.weight + "----" + hd.rule);
					HealthStatusResponsePojo healthStatusResponsePojo = new HealthStatusResponsePojo();
					healthStatusResponsePojo.setAlert(hd.getRule());
					healthStatusResponsePojo.setWeight(String.valueOf(hd.weight));
					int minutes = s / 60;
					int actualSeconds = s % 60;
					Date d = new Date(daily.day.getYear(), daily.day.getDate(), hourly.hour, minutes, actualSeconds);
					healthStatusResponsePojo.setDate(d);
					healthStatusResponsePojos.add(healthStatusResponsePojo);
				}

			}

		}

		return healthStatusResponsePojos;
	}

	public List<HealthStatusResponsePojo> getByDateTime(Long start, Long end) {
		Timestamp stime = new Timestamp(start);
		Date startDate = new Date(stime.getYear(), stime.getMonth(), stime.getDate());
		Timestamp etime = new Timestamp(end);
		Date endDate = new Date(etime.getYear(), etime.getMonth(), etime.getDate());
		User user = userDAO.getByDateTime(startDate, endDate);
		return createHealthStatusResponseList(user);

	}
}
