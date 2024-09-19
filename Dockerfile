FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/api_partners-0.0.1-SNAPSHOT.jar l-delivery-partners-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "l-delivery-partners-app.jar"]