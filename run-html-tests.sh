#!/bin/bash

echo 'Running Selenium Tests'

param_lib_selenium_standalone='lib\selenium-server-standalone.jar'
param_browser='*firefox'
param_host='http://localhost:8080/pentaho/'
param_testsuite='src-html\test_suite_global_jenkins.html'
param_testsuite_results='results.html'
param_options='-timeout 60000 -browserTimeout 60000'

java -jar ${param_lib_selenium_standalone} -htmlSuite "${param_browser}" "${param_host}" "${param_testsuite}" "${param_testsuite_results}" ${param_options}