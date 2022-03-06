#!/bin/bash

oneTimeSetUp() {
  export FABRIC_CFG_PATH=$PWD/../config/

  if [[ -z "$GITHUB_WORKSPACE" ]]; then
    GITHUB_WORKSPACE=~/cocome-hyperledger/
  fi

  source $GITHUB_WORKSPACE/src/test/shell/as-org1.sh

  rm -f $GITHUB_WORKSPACE/cocome.tar.gz
  rm -rf "$GITHUB_WORKSPACE/build/install/"
  pushd "$GITHUB_WORKSPACE"
  ./gradlew installDist
  popd
  peer lifecycle chaincode package $GITHUB_WORKSPACE/cocome.tar.gz --path $GITHUB_WORKSPACE/build/install/cocome --lang java --label cocome_1.0

}

setUp() {
  ./network.sh down
  ./network.sh up createChannel

  # start a subshell due to export variables.
  (
    export CORE_PEER_TLS_ENABLED=true
    export CORE_PEER_LOCALMSPID="Org1MSP"
    export CORE_PEER_TLS_ROOTCERT_FILE=${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt
    export CORE_PEER_MSPCONFIGPATH=${PWD}/organizations/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp
    export CORE_PEER_ADDRESS=localhost:7051
    # The package ID is the combination of the chaincode label and a hash of the chaincode binaries. Every peer will generate the same package ID.
    packgeId=$(peer lifecycle chaincode install $GITHUB_WORKSPACE/cocome.tar.gz 2>&1 | grep -o -P '(?<=identifier:\s).+:[\da-f]+$')

    peer lifecycle chaincode approveformyorg -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --channelID mychannel --name cocome --version 1.0 --package-id $packgeId --sequence 1 --tls --cafile ${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem

    export CORE_PEER_LOCALMSPID="Org2MSP"
    export CORE_PEER_TLS_ROOTCERT_FILE=${PWD}/organizations/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/tls/ca.crt
    export CORE_PEER_MSPCONFIGPATH=${PWD}/organizations/peerOrganizations/org2.example.com/users/Admin@org2.example.com/msp
    export CORE_PEER_ADDRESS=localhost:9051
    peer lifecycle chaincode install $GITHUB_WORKSPACE/cocome.tar.gz

    peer lifecycle chaincode approveformyorg -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --channelID mychannel --name cocome --version 1.0 --package-id $packgeId --sequence 1 --tls --cafile ${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem

    peer lifecycle chaincode commit -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --channelID mychannel --name cocome --version 1.0 --sequence 1 --tls --cafile ${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem --peerAddresses localhost:7051 --tlsRootCertFiles ${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt --peerAddresses localhost:9051 --tlsRootCertFiles ${PWD}/organizations/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/tls/ca.crt
  )
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

testManageItem() {
  pci -C mychannel -n cocome --waitForEvent -c '{"function":"ManageItemCRUDServiceImpl:createItem","Args":["1","cookies","10","10","9"]}' || fail

  docker stop "$(docker ps -n 1 --filter 'name=dev' --format '{{.ID}}')"

  peer chaincode query -C mychannel -n cocome -c '{"function":"ManageItemCRUDServiceImpl:queryItem","Args":["1"]}' || fail

  docker stop "$(docker ps -n 1 --filter 'name=dev' --format '{{.ID}}')"

  pci -C mychannel -n cocome --waitForEvent -c '{"function":"ManageItemCRUDServiceImpl:modifyItem","Args":["1","Pepperidge farm cookies","12","5","10"]}' || fail

  docker stop "$(docker ps -n 1 --filter 'name=dev' --format '{{.ID}}')"

  pci -C mychannel -n cocome --waitForEvent -c '{"function":"ManageItemCRUDServiceImpl:deleteItem","Args":["1"]}' || fail

  if pci -C mychannel -n cocome --waitForEvent -c '{"function":"ManageItemCRUDServiceImpl:deleteItem","Args":["1"]}'; then
    fail 'Cannot delete the same item twice'
  fi
}

testCashPayment() {
  pci -C mychannel -n cocome --waitForEvent -c '{"function":"ManageItemCRUDServiceImpl:createItem","Args":["1","cookies","10","10","9"]}' || fail

  pci -C mychannel -n cocome --waitForEvent -c '{"function":"ManageStoreCRUDServiceImpl:createStore","Args":["1","Target","Weyburn","false"]}' || fail
  pci -C mychannel -n cocome --waitForEvent -c '{"function":"ManageCashDeskCRUDServiceImpl:createCashDesk","Args":["1","1","false"]}' || fail
  pci -C mychannel -n cocome --waitForEvent -c '{"function":"CoCoMESystemImpl:openStore","Args":["1"]}' || fail
  pci -C mychannel -n cocome --waitForEvent -c '{"function":"CoCoMESystemImpl:openCashDesk","Args":["1"]}' || fail
  pci -C mychannel -n cocome --waitForEvent -c '{"function":"ProcessSaleServiceImpl:makeNewSale","Args":[]}' || fail

  pci -C mychannel -n cocome --waitForEvent -c '{"function":"ProcessSaleServiceImpl:enterItem","Args":["1","2"]}' || fail
  output=$(pci -C mychannel -n cocome --waitForEvent -c '{"function":"ProcessSaleServiceImpl:endSale","Args":[]}' 2>&1 |
            sed -n -r 's/.+status:200[[:space:]]+payload:"(.+)"[[:space:]]*$/\1/p' )

  assertEquals "Total sale amount is incorrect." "20.0" "$output"
}

source shunit2
