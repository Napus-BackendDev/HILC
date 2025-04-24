FROM eclipse-temurin:17-jre
WORKDIR /app
COPY /target/school-major-api-0.0.1-SNAPSHOT.jar /app/school-major-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "school-major-api.jar"]