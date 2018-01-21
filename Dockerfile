from tomcat
MAINTAINER xyz
ADD ./target/stock-comparison-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
ADD ./target/stock-comparison-1.0-SNAPSHOT /usr/local/tomcat/webapps/ROOT
CMD ["catalina.sh","run"]
#
#
#  docker build -t stockcomparison_api_container .
#  docker run --rm --name stockcomparison_api_container -it -p 8080:8080 testimage
#  docker run --rm --name stockcomparison_api_container -itd -p 8080:8080 testimage
#