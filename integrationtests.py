import urllib2
import socket

def check_url( url, timeout=5 ):
    print "Testing " + url
    try:
        return urllib2.urlopen(url,timeout=timeout).getcode() == 200
    except urllib2.URLError as e:
          print e.message
        raise Exception('API Integration Test Failed (URLError)')
    except socket.timeout as e:
        print e.message
        raise Exception('API Integration Test Failed (Socket Timeout)')
    except Exception as e:
        print e.message
        raise Exception('API Integration Test Failed (General Exception)')

print "Running API Integration Tests"
print check_url("http://127.0.0.1:8080/rest/comparison/performance?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231")
print check_url("http://127.0.0.1:8080/rest/comparison/correlation?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231")