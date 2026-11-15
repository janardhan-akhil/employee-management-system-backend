FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/employee-management-system.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]