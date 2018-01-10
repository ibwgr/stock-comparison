#!/bin/sh
# Make sure tests fails if a command ends without 0
set -e
# sicherheitshalber etwas warten
sleep 3
echo "--------------------------------------------------------"
echo "   Running Docker-Deployment-Test and End-To-End-Tests  "
echo "--------------------------------------------------------"
echo "calling http://localhost:8080/rest/comparison/performance?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231"
curl 'http://localhost:8080/rest/comparison/performance?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231'
curl 'http://localhost:8080/rest/comparison/performance?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231'
actual=$(curl 'http://localhost:8080/rest/comparison/performance?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231')
if [[ ${actual} == *"comparisonOutputElements"* ]]; then
  echo "Test passed"
  exit 0
else
  echo "Test failed"
  exit 1
fi
