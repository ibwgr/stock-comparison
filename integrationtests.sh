#!/bin/sh
# Make sure tests fails if a command ends without 0
set -e
# sicherheitshalber etwas warten
sleep 3
echo "--------------------------------------------------------"
echo "Running Docker-Deployment-Test and API-Integration-Tests"
echo "--------------------------------------------------------"
#expected="Hello Worldxxx"
##wget http://localhost:8080/rest/comparison/performance?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231 -q -O -
curl 'http://localhost:8080/rest/comparison/performance%3Fstock%3DSAGE%26stock%3DGOOGL%26stock%3DORCL%26stock%3DK%26dateFrom%3D20130313%26dateTo%3D20171231'
##actual=$(curl -I ${service_ip}:8080/rest/comparison/performance?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231)
#echo "Expecting:" $expected
#echo "Server says:" $actual
#if [ "$expected" != "$actual" ]; then
#  echo "Test failed"
#  exit 1
#else
#  echo "Test passed"
#  exit 0
#fi
