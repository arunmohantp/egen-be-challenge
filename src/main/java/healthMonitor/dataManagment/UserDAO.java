package healthMonitor.dataManagment;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.DAO;

import healthMonitor.dataManagment.User.DailyHealthStatus;
import healthMonitor.dataManagment.User.DailyHealthStatus.HourlyHealthStatus;
import healthMonitor.dataManagment.User.DailyHealthStatus.HourlyHealthStatus.HealthData;

public interface UserDAO extends DAO<User, ObjectId> {

	public User getByDateTime(Date start, Date end);

	public User getAll(String userId);

	public boolean hasCurrentHourAlreadyInDb(Date date, int hour);

	public boolean hasCurrentDateAlreadyInDb(Date date);

	public boolean updateHourlyHealthStatus(Date date, User.DailyHealthStatus.HourlyHealthStatus hourlyHealthStatus);

	public boolean updateHealthStatusBySeconds(Date date,
			User.DailyHealthStatus.HourlyHealthStatus.HealthData healthData, Integer seconds, Integer hours);

}