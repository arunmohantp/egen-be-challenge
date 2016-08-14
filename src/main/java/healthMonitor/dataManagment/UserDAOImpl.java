package healthMonitor.dataManagment;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import healthMonitor.dataManagment.User.DailyHealthStatus;
import healthMonitor.dataManagment.User.DailyHealthStatus.HourlyHealthStatus;
import healthMonitor.dataManagment.User.DailyHealthStatus.HourlyHealthStatus.HealthData;

public class UserDAOImpl extends BasicDAO<User, ObjectId> implements UserDAO {

	public UserDAOImpl(Class<User> entityClass, Datastore ds) {
		super(entityClass, ds);
	}

	@Override
	public User getByDateTime(Date start, Date end) {
		Query<User> query = createQuery().field("daily_health_status.day").lessThanOrEq(end)
				.field("daily_health_status.day").greaterThan(start);
		return query.get();
	}

	@Override
	public User getAll(String userId) {
		// Query<User> query = createQuery().filter("userId", userId);
		return createQuery().get();
	}

	@Override
	public boolean hasCurrentHourAlreadyInDb(Date date, int hour) {

		Query<User> query = createQuery().filter("daily_health_status.day", date)
				.filter("daily_health_status.hourly_status.hour", hour);
		return query.get() != null ? true : false;

	}

	@Override
	public boolean hasCurrentDateAlreadyInDb(Date date) {
		Query<User> query = createQuery().filter("daily_health_status.day", date);
		return query.get() != null ? true : false;
	}

	@Override
	public boolean updateHealthStatusBySeconds(Date date,
			User.DailyHealthStatus.HourlyHealthStatus.HealthData healthData, Integer seconds, Integer hours) {

		Map<Integer, User.DailyHealthStatus.HourlyHealthStatus.HealthData> healthMap = new HashMap<>();
		healthMap.put(987, healthData);
		UpdateOperations<User> updateOperations = createUpdateOperations().disableValidation()
				.set("daily_health_status.0.hourly_status.0.healthData." + seconds, healthData).enableValidation();

		Query<User> query = createQuery().filter("daily_health_status.day", date)
				.filter("daily_health_status.hourly_status.hour", hours);
		update(query, updateOperations);

		return false;
	}

	@Override
	public boolean updateHourlyHealthStatus(Date date, User.DailyHealthStatus.HourlyHealthStatus hourlyHealthStatus) {

		UpdateOperations<User> updateOperations = createUpdateOperations().disableValidation()
				.add("daily_health_status.0.hourly_status", hourlyHealthStatus).enableValidation();

		Query<User> query = createQuery().filter("daily_health_status.day", date);
		update(query, updateOperations);

		return false;
	}

}
