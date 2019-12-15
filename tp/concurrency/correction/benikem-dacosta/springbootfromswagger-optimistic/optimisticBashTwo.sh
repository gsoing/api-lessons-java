#!/bin/bash

# # ./optimisticBashOne.sh & ./optimisticBashTwo.sh & wait    | simuler un optimisticLocking

curl --request POST \
  --url http://localhost:8080/api/v1/documents/5df204f706ff31402a460262 \
  --header 'content-type: application/json' \
  --data '{
  "title": "bash 2",
  "body": "string"
}'
