FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD facebook-service-0.1.0.jar facebook-service.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/facebook-service.jar"]