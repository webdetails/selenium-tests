echo ON

set param.lib.selenium.standalone=lib\selenium-server-standalone-2.44.0.jar
set param.browser=*firefox C:\Program Files (x86)\Mozilla Firefox\firefox.exe
set param.host=http://localhost:8080/pentaho/
set param.testsuite=src-html\test_suite_cde_tutorial.html
set param.testsuite.results=results.html
set param.options=

java -jar %param.lib.selenium.standalone% %param.options% -htmlSuite "%param.browser%" "%param.host%" "%param.testsuite%" "%param.testsuite.results%"