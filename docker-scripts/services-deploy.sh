#!/bin/bash

# Deploy the services on the VMs

. get-services.sh $@ # set 'services' array

eval $(docker-machine env siuu-1)


# Create a load balancer

docker service rm proxy

docker service create --name proxy \
    -p 80:80 \
    -p 443:443 \
    -p 8080:8080 \
    --network proxy \
    -e MODE=swarm \
    vfarcic/docker-flow-proxy


# Meanwhile init the services

for service in ${services[@]}; do
    docker pull siuuproject/$service

    docker service rm $service

    docker service create --name $service \
        --network services \
        --network proxy \
        siuuproject/$service
done

sleep 3

# Configure the load balancer

config_uri_prefix="$(docker-machine ip siuu-1):8080/v1/docker-flow-proxy/reconfigure?"

events_config_uri=${config_uri_prefix}
events_config_uri="${events_config_uri}serviceName=events-service"
events_config_uri="${events_config_uri}&servicePath=/event,/events"
events_config_uri="${events_config_uri}&port=8080"
curl $events_config_uri

sleep 10

facebook_config_uri=${config_uri_prefix}
facebook_config_uri="${facebook_config_uri}serviceName=facebook-service"
facebook_config_uri="${facebook_config_uri}&servicePath=/facebook"
facebook_config_uri="${facebook_config_uri}&port=8081"
curl $facebook_config_uri

sleep 10

users_config_uri=${config_uri_prefix}
users_config_uri="${users_config_uri}serviceName=users-service"
users_config_uri="${users_config_uri}&servicePath=/user,/users"
users_config_uri="${users_config_uri}&port=8082"
curl $users_config_uri

sleep 10

venues_config_uri=${config_uri_prefix}
venues_config_uri="${venues_config_uri}serviceName=venues-service"
venues_config_uri="${venues_config_uri}&servicePath=/venue,/venues"
venues_config_uri="${venues_config_uri}&port=8083"
curl $venues_config_uri
