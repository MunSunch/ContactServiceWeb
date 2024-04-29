FROM bellsoft/liberica-openjdk-alpine:17
EXPOSE 8080
WORKDIR /opt/app
ADD ./target/Contacts-0.0.1-SNAPSHOT.jar ./service.jar
CMD ["java", "-jar", "/opt/app/service.jar"]