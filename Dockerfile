# syntax=docker/dockerfile:1.7

FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn -q -DskipTests dependency:go-offline

COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -q -DskipTests clean package

#--------------------------------------
FROM eclipse-temurin:21-jre
ENV APP_HOME=/opt/app     JAVA_OPTS=""     SPRING_PROFILES_ACTIVE=dev

RUN useradd -ms /bin/sh appuser
WORKDIR $APP_HOME

COPY --from=build /app/target/*.jar app.jar
RUN chown -R appuser:appuser $APP_HOME
USER appuser

EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]
