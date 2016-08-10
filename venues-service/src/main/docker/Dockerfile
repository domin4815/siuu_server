FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD venues-service-0.1.0.jar venues-service.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/venues-service.jar"]