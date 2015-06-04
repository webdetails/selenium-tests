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
package org.pentaho.ctools.utils;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DirectoryWatcher {
  //Time to wait for a file or something new get in directory
  private static final long WAIT_TIMEOUT = 30;
  //Log instance
  private static final Logger LOG = LogManager.getLogger( DirectoryWatcher.class );

  public static boolean WatchForCreate( String path ) {
    return WatchForCreate( path, WAIT_TIMEOUT );
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
  public static boolean WatchForCreate( final String path, long timeout ) {
    boolean bFileCreated = false;

    try {
      class RunnableObject implements Runnable {

        private Logger log = LogManager.getLogger( RunnableObject.class );

        private boolean isFileCreated;

        public RunnableObject( boolean isFileCreated ) {
          this.isFileCreated = isFileCreated;
        }

        public boolean getValue() {
          return this.isFileCreated;
        }

        @Override
        public void run() {
          try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path dir = Paths.get( path );
            dir.register( watcher, StandardWatchEventKinds.ENTRY_CREATE );

            while ( !this.isFileCreated ) {
              WatchKey key;
              try {
                key = watcher.take();
              } catch ( InterruptedException ex ) {
                this.log.error( ex.getMessage() );
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
            this.log.error( e.getMessage() );
          }
        }
      }

      RunnableObject r = new RunnableObject( bFileCreated );

      ExecutorService executor = Executors.newSingleThreadExecutor();
      executor.submit( r ).get( timeout + 2, TimeUnit.SECONDS );
      executor.shutdown();
      bFileCreated = r.getValue();

    } catch ( Exception e ) {
      LOG.error( e.getMessage() );
      LOG.error( e.toString() );
    }

    return bFileCreated;
  }
}
