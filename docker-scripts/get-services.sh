#!/bin/bash

service_types=(events facebook users venues)
selected_service_types=()

if [[ $# -eq 1 ]]; then
    for service_type in ${service_types[@]}; do
        if [[ ${BASH_ARGV[0]} = $service_type ]]; then
            selected_service_types+=($service_type)
        fi
    done
fi

if [[ ${#selected_service_types[@]} -eq 0 ]]; then
    selected_service_types=${service_types[@]}
fi

services=()
for service_type in ${selected_service_types[@]}; do
    services+=(${service_type}-service)
done