#!/bin/bash

#Initialize VMs and networks

if [ $# -eq 1 ]; then
    hosts_count=$((BASH_ARGV[0]))

    # swarm master

    docker-machine create -d virtualbox siuu-1
    eval $(docker-machine env siuu-1)

    docker swarm init \
        --advertise-addr $(docker-machine ip siuu-1) \
        --listen-addr $(docker-machine ip siuu-1):2377
    swarm_token=$(docker swarm join-token -q worker)


    # networks

    docker network create --driver overlay proxy
    docker network create --driver overlay services


    # servants

    for i in $(seq 2 $hosts_count); do
        docker-machine create -d virtualbox siuu-${i}
        eval $(docker-machine env siuu-${i})
        docker swarm join \
            --token $swarm_token \
            $(docker-machine ip siuu-1):2377
    done
fi