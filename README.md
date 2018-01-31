# Demo Metrics Application

Spring Boot application uses <a href="http://metrics.dropwizard.io">Dropwizard Metrics</a> library to generate metrics data and reports it to CSV files.


## Prerequisites

Below are the list of softwares needed.

```
JDK 1.8.0_144
Gradle 4.2.1
```

## Run the application

You can run the application using below gradle wrapper command. 

```
./gradlew build && java -jar build/libs/demo-metrics-0.1.0.jar
```

## Access to RESTful API

Application provides RESTful endpoints to do CRUD operations. You can access these using browser or postman plugin.

For Example 
```
http://localhost:8080/api/v1/customers
```

Please see swagger-ui for complete list of operations available and sample requests and responses.

```
http://localhost:8080/swagger-ui.html
```

## Dropwizard Metrics Output

Application reports metrics data to multiple CSV files by the metric name for every minute. Currently, application uses the following
metric types from Dropwizard library.

* Timer
* Histogram
* Counter
* Meter
 
You can access these files under project home directory using below command.

```
ls -l ./metrics-output/
```

## Built With

* [SPRING INITIALIZR](https://start.spring.io/)- Spring Boot Initializer
* [Dropwizard Metrics](https://metrics.dropwizard.io/)- Dropwizard Metrics Library
* [Gradle](https://gradle.org/) - Gradle Build Tool


## Authors

* **Ram Mannam** *Initial Work*



## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

