#!/bin/sh
set -e
echo "--------------------------------------------------------"
echo "Running Docker-Deployment-Test and API-Integration-Tests"
echo "--------------------------------------------------------"
expected="Hello World"
actual=$(curl -I http://127.0.0.1/rest/comparison/performance?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231)
echo "Expecting: $expected"
echo "Server says: $actual"
if [ "$expected" != "$actual" ]; then
  echo "Test failed"
  exit 1
else
  echo "Test passed"
  exit 0
fi
