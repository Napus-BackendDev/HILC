FROM eclipse-temurin:17-jre
WORKDIR /app
COPY school-major-api/target/*.jar /app/school-major-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "school-major-api.jar"]