#!/bin/bash

echo "Vamos iniciar o Docker?"

sleep 10s

docker ps

sleep 5s

CONTAINERS=$(docker ps -q)

if [ -n "$CONTAINERS" ]; then
    echo "Parando containers em execução..."
    docker stop $CONTAINERS
else
    echo "Nenhum container em execução."
fi

sleep 2s

docker ps

docker compose up -d

sleep 2s
