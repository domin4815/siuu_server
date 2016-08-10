#!/bin/bash

# Build sources and push them to Docker Hub

services=(events-service facebook-service users-service venues-service)

cd ..

docker login

for service in ${services[@]}; do
    cd $service
    mvn package docker:build
    docker tag $service siuuproject/$service:latest
    docker push siuuproject/$service
    cd ..
done

docker logout

cd docker-scripts