FROM openjdk:17
COPY build/libs/*.jar user-flow-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "user-flow-api.jar"]