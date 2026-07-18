REM ******************************************************************************
REM
REM Pentaho
REM
REM Copyright (C) 2026 by Pentaho Canada Inc. : http://www.pentaho.com
REM
REM Use of this software is governed by the Business Source License included
REM in the LICENSE.TXT file.
REM
REM Change Date: 2030-06-15
REM ******************************************************************************
echo ON

set param.lib.selenium.standalone=lib\selenium-server-standalone-2.44.0.jar
set param.browser=*firefox C:\Program Files (x86)\Mozilla Firefox\firefox.exe
set param.host=http://localhost:8080/
set param.testsuite=src-html\test_suite_cdf_tutorial.html
set param.testsuite.results=results.html
set param.options=

java -jar %param.lib.selenium.standalone% %param.options% -htmlSuite "%param.browser%" "%param.host%" "%param.testsuite%" "%param.testsuite.results%"