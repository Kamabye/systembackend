# syntax = docker/dockerfile:1.2
FROM amazoncorretto:21-alpine-jdk

COPY target/kamabye-0.0.1-SNAPSHOT.jar /app.jar
RUN --mount=type=secret,id=_env,dst=/etc/secrets/.env cat /etc/secrets/.env
# COPY .env /app/.env
#RUN export $(grep -v '^#' /app/.env | xargs)

# CMD ["java", "-jar", "/app.jar"]
ENTRYPOINT [ "java", "-jar", "/app.jar" ]