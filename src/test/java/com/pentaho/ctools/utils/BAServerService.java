/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2016 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package com.pentaho.ctools.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BAServerService {
  //Log instance
  private final static Logger LOG = LogManager.getLogger( BAServerService.class );
  // The BA Server URL
  private String baURL = "";
  // The BA Server hostname
  private String baHostname = "";
  // The BA Server port
  private String baPort = "";
  // The BA Server service name
  private String baServiceName = "";

  /**
   * The constructor.
   * 
   * @param baURL - BA server url.
   * @param baHostname - BA server hostname.
   * @param baPort - BA server port.
   * @param baServiceName - BA server service name (windows only).
   */
  public BAServerService( final String baURL, final String baHostname, final String baPort, final String baServiceName ) {
    this.baURL = baURL;
    this.baHostname = baHostname;
    this.baPort = baPort;
    this.baServiceName = baServiceName;

    LOG.debug( "BA Url: " + this.baURL );
    LOG.debug( "BA Hostname: " + this.baHostname );
    LOG.debug( "BA Port: " + this.baPort );
    LOG.debug( "BA Service Name: " + this.baServiceName );
  }

  /**
   * Start the BA Server and wait for server start-up.
   */
  public void Start() {
    // you can pass query/start/stop to respective 
    // operation on windows Audio Service while running
    String[] command = { "cmd.exe",
        "/c",
        "sc",
        "start",
        this.baServiceName };
    String output = ExecuteCommand( command );

    if ( output.contains( "START_PENDING" ) ) {
      //Check if Server is listening
      for ( int nTry = 0; nTry < 20; nTry++ ) { //300000 == 5 minutes
        boolean serverIsListening = HttpUtils.ServerListening( this.baHostname, Integer.parseInt( this.baPort ) );
        if ( serverIsListening == true ) {
          LOG.debug( "BA Server is listening NOW!" );
          break;
        } else {
          LOG.debug( "BA Server is NOT listening!" );
          try {
            Thread.sleep( 15000 );
          } catch ( InterruptedException e ) {
            e.printStackTrace();
          }
        }
      }

      //Wait for access URL.
      int status = HttpUtils.GetHttpStatus( this.baURL );
      if ( status == HttpStatus.SC_OK ) {
        LOG.debug( "BA Server is ready to use!" );
      } else {
        LOG.debug( "BA Server is initializing!" );
      }
    } else {
      LOG.error( "The service didn't start." );
    }
  }

  /**
   * Stop the ba server and wait for server not available.
   */
  public void Stop() {
    String[] command = { "cmd.exe",
        "/c",
        "sc",
        "stop",
        this.baServiceName };
    String output = ExecuteCommand( command );

    if ( output.contains( "STOP_PENDING" ) ) {
      //Check if Server is not listening
      for ( int nTry = 0; nTry < 20; nTry++ ) { //300000 == 5 minutes
        boolean serverIsListening = HttpUtils.ServerListening( this.baHostname, Integer.parseInt( this.baPort ) );
        if ( serverIsListening == false ) {
          LOG.debug( "BA Server is NOT listening!" );
          break;
        } else {
          LOG.debug( "BA Server is STILL listening [" + this.baPort + "]!" );
          try {
            Thread.sleep( 15000 );
          } catch ( InterruptedException e ) {
            e.printStackTrace();
          }
        }
      }

      //Wait for access URL broken.
      int status = HttpUtils.GetHttpStatus( this.baURL );
      if ( status == HttpStatus.SC_BAD_REQUEST ) {
        LOG.debug( "BA Server is STOPPED!" );
      } else {
        LOG.debug( "BA Server is shutdown!" );
      }
    } else {
      LOG.error( "The service couldn't stop." );
    }
  }

  /**
   * This method shall execute a windows command to start/stop/query 
   * windows services
   * 
   * @param command
   * @return Output of the command execution.
   */
  private static String ExecuteCommand( String[] command ) {
    String output = "";
    try {
      Process process = new ProcessBuilder( command ).start();

      try (InputStream inputStream = process.getInputStream();
          InputStreamReader inputStreamReader = new InputStreamReader( inputStream );
          BufferedReader bufferedReader = new BufferedReader( inputStreamReader );) {

        String line;
        while ( ( line = bufferedReader.readLine() ) != null ) {
          LOG.debug( "OUTPUT:: " + line );
          output += line;
        }
      } catch ( Exception ex ) {//InputStream
        LOG.debug( "InputStream Exception : " + ex );
      }
    } catch ( Exception ex ) { //Process
      LOG.debug( "Process Exception : " + ex );
    }
    return output;
  }

  /**
   * Restart the BA server, i.e., stop the service and start it again.
   */
  public void Restart() {
    Stop();
    Start();
  }
}
