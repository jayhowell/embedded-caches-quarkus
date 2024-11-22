#!/usr/bin/env bash
# export EP=http://localhost:8080/api
export EP=http://embedded-caches-quarkus-quarkusinfinispan.apps.cluster-76dcf.76dcf.sandbox3025.opentlc.com/api/lambda

curl --header "Content-Type: application/json" \
  --request GET \
  $EP 

echo " "
