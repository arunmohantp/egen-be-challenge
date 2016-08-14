package healthMonitor.dataManagment;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClient;


@Service
@PropertySources({ @PropertySource(value = "classpath:application.properties") })
public class MorphiaService {

	private Morphia morphia;
	private Datastore datastore;
	
	@Value("${mongoDb.url}")
	private String mongoDbUrl;

	public MorphiaService() {

		//mongoDbUrl provides the mongo db url configured in the application.property file.
		//application.property is present at src/main/resources
		MongoClient mongoClient = new MongoClient(mongoDbUrl);
		this.morphia = new Morphia();
		String databaseName = "test10";
		this.datastore = morphia.createDatastore(mongoClient, databaseName);
	}

	public Morphia getMorphia() {
		return morphia;
	}

	public void setMorphia(Morphia morphia) {
		this.morphia = morphia;
	}

	public Datastore getDatastore() {
		return datastore;
	}

	public void setDatastore(Datastore datastore) {
		this.datastore = datastore;
	}
	

}