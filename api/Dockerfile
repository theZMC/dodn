FROM maven:3-eclipse-temurin-21 AS build
WORKDIR /tmp/build
COPY pom.xml pom.xml
RUN mvn dependency:go-offline
COPY src src
RUN mvn package -DskipTests

FROM gcr.io/distroless/java21-debian12:nonroot AS dodn-api
COPY --from=build /tmp/build/target/api-*.jar /app/api.jar
ENTRYPOINT ["java", "-jar", "/app/api.jar"]
