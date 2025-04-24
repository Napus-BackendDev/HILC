FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests


FROM eclipse-temurin:17-jre
WORKDIR /app
COPY school-major-api/target/*.jar /app/school-major-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "school-major-api.jar"]