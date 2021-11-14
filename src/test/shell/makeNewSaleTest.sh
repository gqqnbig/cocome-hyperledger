#!/bin/bash

oneTimeSetUp() {
  ./network.sh down
  ./network.sh up createChannel
  ./network.sh deployCC -ccn cocome -ccp $GITHUB_WORKSPACE -ccl java

  export FABRIC_CFG_PATH=$PWD/../config/
  source $GITHUB_WORKSPACE/src/test/shell/as-org1.sh
}

testMakeNewSale() {
  pci -C mychannel -n cocome --waitForEvent -c '{"function":"ManageStoreCRUDServiceImpl:createStore","Args":["1","Target","Weyburn","false"]}' || fail
  pci -C mychannel -n cocome --waitForEvent -c '{"function":"ManageCashDeskCRUDServiceImpl:createCashDesk","Args":["1","1","false"]}' || fail
  pci -C mychannel -n cocome --waitForEvent -c '{"function":"CoCoMESystemImpl:openStore","Args":["1"]}' || fail
  pci -C mychannel -n cocome --waitForEvent -c '{"function":"CoCoMESystemImpl:openCashDesk","Args":["1"]}' || fail

  docker stop "$(docker ps -n 1 --filter 'name=dev' --format '{{.ID}}')"

  pci -C mychannel -n cocome --waitForEvent -c '{"function":"ProcessSaleServiceImpl:makeNewSale","Args":[]}' || fail

  if pci -C mychannel -n cocome --waitForEvent -c '{"function":"ProcessSaleServiceImpl:makeNewSale","Args":[]}'; then
    fail 'Second makeNewSale call should fail.'
  fi
}

source shunit2
