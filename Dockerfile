FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/employee-management-system.jar /app/app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "/app/app.jar"]