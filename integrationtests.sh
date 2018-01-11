#!/bin/sh
# Make sure tests fails if a command ends without 0
set -e
sleep 3
echo "Running Docker Deployed End-To-End Tests on Travis"
echo "___________           .___          __                   ___________           .___"
echo "\_   _____/ ____    __| _/        _/  |_  ____           \_   _____/ ____    __| _/"
echo " |    __)_ /    \  / __ |  ______ \   __\/  _ \   ______  |    __)_ /    \  / __ | "
echo " |        \   |  \/ /_/ | /_____/  |  | (  <_> ) /_____/  |        \   |  \/ /_/ |"
echo "/_______  /___|  /\____ |          |__|  \____/          /_______  /___|  /\____ |"
echo "        \/     \/      \/                                        \/     \/      \/"
echo "calling http://localhost:8080/rest/comparison/performance?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231"
actualResponse1=$(curl 'http://localhost:8080/rest/comparison/performance?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231')
if [[ ${actualResponse1} == *"comparisonOutputElements"* ]]; then
  echo "==> Test 1 passed"
  echo "calling http://localhost:8080/rest/comparison/correlation?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231"
  actualResponse2=$(curl 'http://localhost:8080/rest/comparison/correlation?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231')
  if [[ ${actualResponse2} == *"comparisonOutputElements"* ]]; then
    echo "==> Test 2 passed"
    exit 0
  else
    echo "==> Test 2 failed"
    exit 1
  fi
else
  echo "==> Test 1 failed"
  exit 1
fi
