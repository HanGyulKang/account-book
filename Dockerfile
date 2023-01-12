FROM openjdk:11-jdk
COPY build/libs/account-*.jar app.jar
ENV  PROFILE real
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar","/app.jar"]