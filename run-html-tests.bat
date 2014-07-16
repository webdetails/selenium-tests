echo ON

set param.lib.selenium.standalone=lib\selenium-server-standalone.jar
set param.browser=*firefox
set param.host=http://localhost:8080/pentaho/
set param.testsuite=src-html\test_suite_global_jenkins.html
set param.testsuite.results=results.html
set param.options=-timeout=60 -browserTimeout=60

java -jar %param.lib.selenium.standalone% %param.options% -htmlSuite "%param.browser%" "%param.host%" "%param.testsuite%" "%param.testsuite.results%"