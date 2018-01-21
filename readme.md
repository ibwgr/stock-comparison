# Semesterarbeit "Stock Quotes API"
## Dieter Biedermann, Reto Kaufmann </br>NDS HF Applikationsentwicklung, IBW Chur 2018/01



| Travis Build/Test Status | Sonar Continuous Code Quality Status |
|---|---|
| <a href="https://travis-ci.org/ibwgr/stock-comparison" target="_blank"><img src="https://travis-ci.org/ibwgr/stock-comparison.svg?branch=master"/></a> | <a href="https://sonarcloud.io/dashboard?id=groupId%3Astock-comparison" target="_blank"><img src="https://sonarcloud.io/api/badges/gate?key=groupId:stock-comparison"/></a> |


### Schritte zum Start der Anwendung:

1.) mvn clean package

2.) docker-compose up

3.) folgende Beispiel URL's können im Browser aufgerufen werden:
* http://localhost:8080/rest/comparison/performance?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231
* http://localhost:8080/rest/comparison/correlation?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231


#### Zusatzinfo
Wenn lokal ein Server wie Glassfish oder Tomcat verwendet wird, dann sind dir URL's wie folgt aufzurufen:
* http://localhost:8080/stock-comparison-1.0-SNAPSHOT/rest/comparison/performance?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231
* http://localhost:8080/stock-comparison-1.0-SNAPSHOT/rest/comparison/correlation?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231

### Anwendung im Web deployed:

Heroku mit Docker Deployment:
* --offen--

OpenShift (als Maven Build und WAR Deployment): 
* http://test-appl-test-proj.193b.starter-ca-central-1.openshiftapps.com/stock-comparison-1.0-SNAPSHOT/rest/comparison/performance?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231
* http://test-appl-test-proj.193b.starter-ca-central-1.openshiftapps.com/stock-comparison-1.0-SNAPSHOT/rest/comparison/correlation?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231


