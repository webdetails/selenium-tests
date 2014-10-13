#!/bin/bash

echo 'Running Selenium Tests'

param_lib_selenium_standalone='lib\selenium-server-standalone-2.42.2.jar'
param_browser='*firefox'
param_host='http://localhost:8080/pentaho/'
param_testsuite='src-html\test_suite_global_jenkins.html'
param_testsuite_results='results.html'
param_options=

java -jar ${param_lib_selenium_standalone} ${param_options} -htmlSuite "${param_browser}" "${param_host}" "${param_testsuite}" "${param_testsuite_results}"


