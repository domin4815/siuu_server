#!/bin/bash

# Build sources and push them to Docker Hub

. get-services.sh $@ # set 'services' array

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