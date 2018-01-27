from tomcat
ADD ./target/stock-comparison-1.0.war /usr/local/tomcat/webapps/ROOT.war
ADD ./target/stock-comparison-1.0 /usr/local/tomcat/webapps/ROOT
CMD ["catalina.sh","run"]
EXPOSE 8080
#
#
#  docker build -t stockcomparison_api_container .
#  docker run --rm --name stockcomparison_api_container -it -p 8080:8080 stockcomparison_api_container
#  docker run --rm --name stockcomparison_api_container -itd -p 8080:8080 stockcomparison_api_container
#