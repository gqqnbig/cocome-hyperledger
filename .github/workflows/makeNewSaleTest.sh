#!/bin/zsh -e

./network.sh down
./network.sh up createChannel
./network.sh deployCC -ccn cocome -ccp $GITHUB_WORKSPACE -ccl java

export FABRIC_CFG_PATH=$PWD/../config/
source $GITHUB_WORKSPACE/.github/workflows/as-org1.sh

pci -C mychannel -n cocome --waitForEvent -c '{"function":"Demo:increase","Args":[]}'

docker ps

docker stop $(docker ps -n 1 --filter 'name=dev' --format '{{.ID}}')

pci -C mychannel -n cocome --waitForEvent -c '{"function":"Demo:increase","Args":[]}'

docker ps


