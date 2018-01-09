import urllib2
import socket
import traceback

def check_url( url, timeout=5 ):
    print "Testing " + url
    try:
        return urllib2.urlopen(url,timeout=timeout).getcode() == 200
    except urllib2.URLError as e:
        print e.message
        traceback.print_exc()
        raise Exception('API Integration Test Failed (URLError)')
    except socket.timeout as e:
        print e.message
        traceback.print_exc()
        raise Exception('API Integration Test Failed (Socket Timeout)')
    except Exception as e:
        print e.message
        traceback.print_exc()
        raise Exception('API Integration Test Failed (General Exception)')

print "--------------------------------------------------------"
print "Running Docker-Deployment-Test and API-Integration-Tests"
print "--------------------------------------------------------"
print check_url("http://127.0.0.1/rest/comparison/performance?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231")
print check_url("http://127.0.0.1/rest/comparison/correlation?stock=SAGE&stock=GOOGL&stock=ORCL&stock=K&dateFrom=20130313&dateTo=20171231")