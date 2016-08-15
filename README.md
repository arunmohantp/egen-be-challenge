# egen-be-challenge
Please provide the local mongodb url in application.properties file present in the src/main/resources. 

This project gave me a quick opportunity to learn mongoDb, morphia, Easy rule engine as I’m using this for first time, thanks for that.

This project uses Spring4 and SpringBoot, which is completely annotation driven.

While storing the time series health data application does only one insert(for every day) and many updates(Each 5 second).
Values are stored as collection of days and inside it as collection of hours and then collection of seconds which updates every 5 seconds.


Mongo db connection and data management is done using morphia.
Still I need to use aggregation and map reduce for better and accurate performance.

Under weight and Over weight are identified using Easy Rule Engine.

<div>Read API(GET): http://localhost:8080/read.</div>

<div>Read By Time API(GET):  http://localhost:8080/readByTimeRange?start=1487418180000&end=1487564820000.</div>

<div>Create API (POST) : http://localhost:8080/create.</div>


