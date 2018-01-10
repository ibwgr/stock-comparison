#!/bin/sh
# Make sure tests fails if a command ends without 0
set -e
sleep 3
echo "--------------------------------------------------------"
echo "   Running Docker-Deployment-Test and End-To-End-Tests  "
echo "--------------------------------------------------------"
echo "calling http://localhost:8080/rest/comparison/performance?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231"
actualResponse1=$(curl 'http://localhost:8080/rest/comparison/performance?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231')
if [[ ${actualResponse1} == *"comparisonOutputElements"* ]]; then
  echo "==> Test passed"
  resultCode1 = 0
else
  echo "==> Test failed"
  resultCode1 = 1
fi
echo "calling http://localhost:8080/rest/comparison/correlation?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231"
actualResponse2=$(curl 'http://localhost:8080/rest/comparison/correlation?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231')
if [[ ${actualResponse2} == *"comparisonOutputElements"* ]]; then
  echo "==> Test passed"
  resultCode2 = 0
else
  echo "==> Test failed"
  resultCode2 = 1
fi
if [ "$resultCode1" == 1 ] || [ "$resultCode2" == 1 ] ; then
 exit 1
else
 exit 0
fi
