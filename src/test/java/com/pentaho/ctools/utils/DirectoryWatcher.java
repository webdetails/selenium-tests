/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2015 by Pentaho : http://www.pentaho.com
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

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DirectoryWatcher {
  //Time to wait for a file or something new get in directory
  private final long waitTimeout = 30;
  //Log instance
  private final Logger log = LogManager.getLogger( DirectoryWatcher.class );

  /**
   * Constructor
   * 
   * @param path
   * @return
   */
  public boolean WatchForCreate( String path ) {
    return WatchForCreate( path, this.waitTimeout );
  }

  /**
   * The method will watch a directory for file creation. If a file was created
   * in the directory then close the listener/watcher.
   *
   * @param path   - the directory to watch
   *
   * @return true  - file was created in dir
   *         false - otherwise
   */
  public boolean WatchForCreate( final String path, long timeout ) {
    this.log.debug( "WatchForCreate::Enter" );
    boolean bFileCreated = false;
    ExecutorService executor = Executors.newSingleThreadExecutor();

    CallCheckFolder r = new CallCheckFolder( path );
    Future<Boolean> future = executor.submit( r );

    try {
      this.log.debug( "Started..." );
      bFileCreated = future.get( timeout, TimeUnit.SECONDS );
      this.log.debug( "Finished!" );
    } catch ( TimeoutException e ) {
      future.cancel( true );
    } catch ( Exception e ) {
      this.log.error( e.getMessage() );
      this.log.error( e.toString() );
    }

    /*
     * This will send an interrupted signal to the thread
     * http://stackoverflow.com/questions/2275443/how-to-timeout-a-thread
     */
    executor.shutdownNow();

    this.log.debug( "WatchForCreate::Exit" );
    return bFileCreated;
  }
}

class CallCheckFolder implements Callable<Boolean> {
  private Logger log = LogManager.getLogger( CallCheckFolder.class );
  private boolean isFileCreated;
  private String path;

  public CallCheckFolder( String path ) {
    this.path = path;
  }

  @Override
  public Boolean call() {

    try (WatchService watcher = FileSystems.getDefault().newWatchService()) {

      Path dir = Paths.get( this.path );
      dir.register( watcher, StandardWatchEventKinds.ENTRY_CREATE );

      while ( !this.isFileCreated ) {
        this.log.debug( "Start watching..." );
        WatchKey key;
        try {
          key = watcher.take();
        } catch ( InterruptedException ex ) {
          this.log.error( ex.toString() );
          break;
        }

        for ( WatchEvent<?> event : key.pollEvents() ) {
          WatchEvent.Kind<?> kind = event.kind();

          if ( kind == StandardWatchEventKinds.OVERFLOW ) {
            continue;
          }

          @SuppressWarnings( "unchecked" )
          WatchEvent<Path> ev = (WatchEvent<Path>) event;
          Path fileName = ev.context();

          this.log.info( kind.name() + ": " + fileName );
          if ( kind == StandardWatchEventKinds.ENTRY_CREATE ) {
            this.isFileCreated = true;
            this.log.info( "The file was created: " + fileName );
          }
        }

        boolean valid = key.reset();
        if ( !valid ) {
          break;
        }
      }
    } catch ( Exception e ) {
      this.log.error( e.toString() );
    }

    this.log.debug( "Stop watched!" );
    return this.isFileCreated;
  }
}
