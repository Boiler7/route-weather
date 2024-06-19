FROM gradle:7.5.1-jdk17 AS build
WORKDIR /home/gradle/project
COPY . .
RUN gradle build --no-daemon

FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
