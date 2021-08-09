FROM bellsoft/liberica-openjdk-alpine:8u292-10
EXPOSE 8080:8080
RUN mkdir app

COPY app/build/libs/*-all.jar /app/application.jar
COPY app/build/resources/main/dbconfig.properties /app
WORKDIR /app


CMD ["java", "-jar", "application.jar" ]