#!/usr/bin/env bash

SERVER_CONTAINER="mysql-server"
cd "$(dirname "$0")"

startServer() {
   docker-compose -p "$SERVER_CONTAINER" up -d --build
}

stopServer() {
   docker-compose -p "$SERVER_CONTAINER" stop
}

getStatus() {
    CONTAINER_ID=$(docker ps -a | grep -v Exit | grep $SERVER_CONTAINER | awk '{print $1}')
    if [[ -z $CONTAINER_ID ]] ; then
        echo 'Not running.'
        return 1
    else
        echo "Running in container: $CONTAINER_ID"
        return 0
    fi
}

destroy() {
        stopServer
        docker-compose rm -f -v
}

case "$1" in
    start)
        startServer
        ;;
    stop)
        stopServer
        ;;
    status)
        getStatus
        ;;
    clean)
        destroy
        ;;
    *)
        echo "Usage: `basename $0`  {start|stop|status|clean}"
        exit 1
        ;;
esac

exit 0