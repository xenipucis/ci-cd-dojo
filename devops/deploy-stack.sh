#!/bin/bash

network=dd-network
stack=ddninja
# Set environment variables used in docker compose
prepare_environment() {
  local OPTIND
  # Script arguments
    while getopts "n:" o; do
        case "${o}" in
            n)
                network=${OPTARG}
                ;;
        esac
    done
    shift $((OPTIND-1))

    # Export environment variables
    export NETWORK=$network
    export STACK=$stack

    # Create stack network
    docker network create --driver overlay --attachable $NETWORK
}

echo_stack_details() {
    echo NETWORK=$NETWORK
    echo STACK=$STACK
}

case $1 in
  start-stack)
    prepare_environment "${@:2}"
    echo_stack_details
    docker stack deploy --compose-file docker-compose.yml $STACK
    sleep 5
    ;;
  stop-stack)
    prepare_environment "${@:2}"
    docker stack rm $(docker stack ls --format '{{.Name}}' | grep $STACK)
    docker container prune -f
    ;;
esac
