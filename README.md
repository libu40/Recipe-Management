# **Restaurant Recipe Management System**

## Introduction :

Create a standalone java application which allows users to manage their favourite recipes. It should
allow adding,
updating, removing and fetching recipes.
Additionally, users should be able to filter available recipes based on one or more of the following
criteria:

1. Whether the dish is vegetarian
2. The number of servings
3. Specific ingredients (either include or exclude)
4. Text search within the instructions.

For example, the API should be able to handle the following search requests:

1. All vegetarian recipes
2. Recipes that can serve 4 people and have “potatoes” as an ingredient
3. Recipes without “salmon” as an ingredient that has “oven” in the instructions.

## Requirements :

Please ensure that we have some documentation about the architectural choices and also how to
run the application. The project is expected to be delivered as a GitHub (or any other public git
hosting) repository URL.

All these requirements need to be satisfied:

1. It must be a REST application implemented using Java (use a framework of your choice)
2. Your code should be production-ready.
3. REST API must be documented
4. Data must be persisted in a database
5. Unit tests must be present
6. Integration tests must be present

-----------------------------------------

## **Setup**

#### Requirements :

- Java 17
- Maven 3.x
- Docker

#### **Prerequisites** :

1. Make sure Docker is up and running in your local system.
2. Please clean up the images, containers and volumes to avoid the conflicts while booting up the containers.

**Docker commands** :

1. docker rmi $(docker images -a -q)
2. docker volume rm $(docker volume ls -q)

#### **Build** :

1. Open the terminal which runs on Java 17 and navigate to project root directory ****recipe-api****

2. Trigger **mvn clean install** command from the source path to verify the testcases and build the
   artifact for the
   deployment.

#### ****Deployment**** :

1. Trigger **docker-compose up** to deploy the artifact for building the container along with the
   other resources
   mentioned in the composed file.

**Please be patient as during this initial deployment will cause sometime to pull the images from
the docker registry
and build the image along with
our artifact image for the creation of containers**

#### **Post-Deployment check** :

Once the deployment is a success, We should be able to see the below-listed containers/resources are
created up and
running. This can be checked by docker commands or via docker desktop dashboard.

1. Sonarqube.
2. PostgresDB.
3. Prometheus.
4. Grafana.
5. MySqlDB.
6. RecipeApp

**Docker commands** :

1. **docker image ls**
2. **docker container ls**
3. **docker ps**

-----------------------------------------

## Testing  :

I've tried to make it as much production ready as I could considering the time constraints.
For instance, I've added custom error handling mechanisms, lots of validations on top of
request objects on real life validations where user might enter unwanted character in the name of
the ingredient, very
long name, or he might enter numberOfServing of non-positive number etc.
I didn't put restrictions for user to put the same name for ingredient or the recipe because for
example recipe named "
pasta" might be cooked with different ingredient, but it's still a pasta

I've decided to use simple relational db called H2 and made the relations between ingredients and
recipes as
many-to-many relationship, since many ingredients might be used in many recipes.
Finally, I've tried to cover as much as test cases I could, I have 75 unit and integration tests, I
might have added
more, but I needed to finish the task for today.

The REST endpoints for testing are being attached as a collection in the project source code directory under the name **[postman-collection](https://github.com/libu40/Recipe-Management/blob/main/postman-collection)** 
which can exported and tested locally in the Postman client apart from Swagger OpenAPI.

## **Monitoring** :

**Sonarqube**:

Once all the docker containers are UP,then goto localhost:9000 which will prompt for default login/password which is admin/admin and ask to
update the password. **Please change the password to sonar from admin** as I've configured sonar as the password in the application for measuring the code quality metrics.

Once after that in the terminal, navigate to project root directory and trigger the below command to visualize the metrics.

**mvn clean install sonar:sonar**

**Prometheus with Grafana**:

Once the docker containers for the Application, Prometheus and Grafana are UP, Please initially check whether the below metric URLs are accessible.

1. Application metrics: http://localhost:8080/restaurant/actuator/prometheus
2. Prometheus: http://localhost:9090/targets?search=
3. Grafana: http://localhost:3000/login

The application metrics should be visualized like below post hitting the endpoint which collects from the application.
![](https://github.com/libu40/Recipe-Management/blob/main/data/images/Application_Metrics.png)

The Prometheus status pointing to the target should be UP like as below
![](https://github.com/libu40/Recipe-Management/blob/main/data/images/Prometheus_Status.png)

The Grafana login url is accessible with default username/password: admin/admin
![](https://github.com/libu40/Recipe-Management/blob/main/data/images/Grafana_Login.png)

Once logged in all the above-mentioned Urls, In prometheus dashboard hit the below expression to check whether the application metrics are getting pulled.

#### **_logback_events_total_**

In the grafana, Add the prometheus data source with the below configuration. The only thing to update is the URL.

Please update the url to ****_http://host.docker.internal:9090_**** as shown below.
![](https://github.com/libu40/Recipe-Management/blob/main/data/images/Grafana_Configuration.png)

Create a dashboard and then import the json which I updated in the [](dashboard.json)

Finally, the application can be monitored and visualize the metrics as shown below.
![](https://github.com/libu40/Recipe-Management/blob/main/data/images/Grafana_Dashboard.png)

**OpenAPI with Swagger **:

The application is documented in the below url

1. http://localhost:8080/restaurant/recipe-documentation
2. http://localhost:8080/restaurant/recipe-api-docs

**JMX with JConsole**:

To see the memory footprint and thread deadlock for any leakages, I've tested the application locally with JConsole launching the application locally. This can be done as follows

1. mvn spring-boot:run
2. type JConsole in the terminal, and the select the process id for the application from the dropdown then it can be visualized as below
![](https://github.com/libu40/Recipe-Management/blob/main/data/images/JConsole.png)

## **Implemented** :

The application is implemented in Java 17 with Spring boot 3 and data JPA enabled for persistence
and CRUD operations. As the application to be built is Production ready, the following features were
integrated.

1. **Prometheus with Grafana**
2. **JPA auditing**
3. **Swagger**
4. **MapStruct**
5. **JMX with JConsole**
6. **HikariCP**
7. **Unit test: Junit 5 with Mockito** :Not completed
8. **Integration test: Apache Cucumber** :Not completed
9. **BDD: Groovy with Apache Cucumber** :Not completed
10. **End2End test: mockMVC with Jax-RS** :Not completed
11. **Database: MySQL**
12. **Pagination**
13. **Code quality: SonarQube**
14. **Application dockerized**
15. **Liquibase migration**

## **To-Be-Implemented** :

1. CI/CD with Gitlab.
2. Automatic POM versioning.
3. CheckMarx scanning.
4. Maven automatic dependencies update.
5. Spring security for controller endpoints via Authentication and Authorization (Keycloak).


## Comments :

My thought process to build the application was to be Production ready, adhering to best practices and standards we maintain in terms of code quality and performance wise.
I'm a big fan of TDD and follow the principles of that to build any application in the day-to-day project, But for this project I haven't picked that due to time constraints.
My focus was mostly on monitoring and data migration apart from building the business logic as for any Production ready application monitoring should be in place before deployment.

This build **IS NOT** completed yet as I need to write unit testcases, E2E Integration tests, BDD with Apache cucumber and Json data modelling tests.

Please while reviewing consider this as a request as I need some more time to build this functionality.







