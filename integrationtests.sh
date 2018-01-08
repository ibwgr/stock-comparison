#!/bin/sh
# Make sure tests fails if a command ends without 0
set -e
# sicherheitshalber etwas warten
sleep 5
echo "--------------------------------------------------------"
echo "Running Docker-Deployment-Test and API-Integration-Tests"
echo "--------------------------------------------------------"
expected="Hello Worldxxx"
curl -i ${service_ip}:9000:8080/rest/comparison/performance?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231
#actual=$(curl -I ${service_ip}:8080/rest/comparison/performance?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231)
echo "Expecting:" $expected
echo "Server says:" $actual
if [ "$expected" != "$actual" ]; then
  echo "Test failed"
  exit 1
else
  echo "Test passed"
  exit 0
fi
