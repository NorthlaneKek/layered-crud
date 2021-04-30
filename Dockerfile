FROM maven:3.6-openjdk-11-slim as app
COPY . .
RUN mkdir /app && mvn package -DskipTests \
               && cp ./target/crud-test.jar /app \
               && cp ./src/main/resources/application.properties /app
CMD ["java","-jar","/app/crud-test.jar"]
