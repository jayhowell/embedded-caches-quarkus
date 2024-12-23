#!/usr/bin/env bash
# export EP=http://localhost:8080/api
# export EP=http://embedded-caches-quarkus-quarkusinfinispan.apps.cluster-76dcf.76dcf.sandbox3025.opentlc.com/api

curl --header "Content-Type: application/json" \
  --request POST \
  -d '{"card":[5,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],"course":"Bethpage","currentHole":3,"playerId":"4","playerName":"Shaaf"}' \
  $EP

echo " "
curl --header "Content-Type: application/json" \
  --request POST \
  -d '{"card":[5,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],"course":"Firestone","currentHole":3,"playerId":"3","playerName":"Dan"}' \
  $EP

echo " "

curl --header "Content-Type: application/json" \
  --request POST \
  -d '{"card":[5,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],"course":"Bethpage","currentHole":3,"playerId":"2","playerName":"James"}' \
  $EP

echo " "

curl --header "Content-Type: application/json" \
  --request PATCH \
  -d '{"card":[5,4,4,10,3,0,0,0,0,0,0,0,0,0,0,0,0,0],"course":"Bethpage","currentHole":4,"playerId":"4","playerName":"Shaaf"}' \
  $EP/4

echo " "
