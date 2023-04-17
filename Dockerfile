FROM maven:3.8.3-openjdk-17-slim

EXPOSE 3000

COPY . /usr/src/kalaha
WORKDIR /usr/src/kalaha

RUN mvn package

ENTRYPOINT ["java", "-jar", "/usr/src/kalaha/target/kalaha-0.0.1-SNAPSHOT.jar"]