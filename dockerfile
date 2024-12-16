# Use maven to build the application
FROM maven:3.8-openjdk-17 AS build

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# Use OpenJDK for the runtime environment
FROM openjdk:17

WORKDIR /app
COPY --from=build /app/target/myweb-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
