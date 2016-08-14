package healthMonitor.dataManagment;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("healthstatus")
public class User {

	@Id
	private ObjectId objectId;

	@Embedded
	private List<DailyHealthStatus> daily_health_status;

	private String userId;

	public User() {

	}

	public User(List<DailyHealthStatus> daily_health_status, String userId) {
		super();
		this.daily_health_status = daily_health_status;
		this.userId = userId;
	}

	public ObjectId getObjectId() {
		return objectId;
	}

	public void setObjectId(ObjectId objectId) {
		this.objectId = objectId;
	}

	public List<DailyHealthStatus> getdaily_health_status() {
		return daily_health_status;
	}

	public void setdaily_health_status(List<DailyHealthStatus> daily_health_status) {
		this.daily_health_status = daily_health_status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Embedded
	public static class DailyHealthStatus {
		public Date day;
		public List<HourlyHealthStatus> hourly_status;

		public Date getDay() {
			return day;
		}

		public void setDay(Date day) {
			this.day = day;
		}

		public List<HourlyHealthStatus> getHourly_status() {
			return hourly_status;
		}

		public void setHourly_status(List<HourlyHealthStatus> hourly_status) {
			this.hourly_status = hourly_status;
		}

		@Embedded
		public static class HourlyHealthStatus {
			public int hour;
			//Map key is the time in seconds, which stores each event triggered
			Map<Integer, User.DailyHealthStatus.HourlyHealthStatus.HealthData> healthData = new HashMap<>();

			public int getHour() {
				return hour;
			}

			public void setHour(int hour) {
				this.hour = hour;
			}

			public Map<Integer, User.DailyHealthStatus.HourlyHealthStatus.HealthData> getHealthData() {
				return healthData;
			}

			public void setHealthData(Map<Integer, User.DailyHealthStatus.HourlyHealthStatus.HealthData> healthData) {
				this.healthData = healthData;
			}

			@Embedded
			public static class HealthData {
				int weight;
				String rule;

				public int getWeight() {
					return weight;
				}

				public void setWeight(int weight) {
					this.weight = weight;
				}

				public String getRule() {
					return rule;
				}

				public void setRule(String rule) {
					this.rule = rule;
				}

			}

		}

	}

}
