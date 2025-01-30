FROM amazoncorretto:21-alpine-jdk

COPY target/kamabye-0.0.1-SNAPSHOT.jar /app.jar
COPY src/main/resources /app/BOOT-INF/classes/

ENTRYPOINT [ "java", "-jar", "/app.jar" ]