FROM maven:3.9.2-amazoncorretto-17
WORKDIR ./
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run
