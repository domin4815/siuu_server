FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD events-service-0.1.0.jar events-service.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/events-service.jar"]
EXPOSE 8080