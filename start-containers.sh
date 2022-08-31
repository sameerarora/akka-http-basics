#! /bin/bash

if [ -z "$1" ]
then
      hostname="localhost"
else
      hostname="$1"
fi

export HOST_NAME="${HOST_NAME:-"${hostname}"}"


echo "starting containers for integration tests" '*'
docker-compose -f $PWD/docker-compose-it.yml up -d

echo "waiting for containers to start" '*'
until curl -s -u theseus:theseus_rmq "http://${HOST_NAME}:15672/api/connections" &> /dev/null;
do
  echo -n '.'
  sleep 1
done

echo "Done!!'"