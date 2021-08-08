FROM bellsoft/liberica-openjdk-alpine:8u292-10
EXPOSE 8080:8080
RUN mkdir app

COPY app/build/install/app /app/
COPY app/build/resources/main /app/bin
WORKDIR /app/bin


CMD ["./app"]