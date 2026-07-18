/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2002 - 2026 by Pentaho Canada Inc. : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2030-06-15
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

class CallCheckFolder implements Callable<Boolean> {
  private boolean isFileCreated;
  private final Logger log = LogManager.getLogger( CallCheckFolder.class );
  private final String path;

  public CallCheckFolder( final String path ) {
    this.path = path;
  }

  @Override
  public Boolean call() {

    try ( WatchService watcher = FileSystems.getDefault().newWatchService() ) {

      final Path dir = Paths.get( this.path );
      dir.register( watcher, StandardWatchEventKinds.ENTRY_CREATE );

      while ( !this.isFileCreated ) {
        this.log.debug( "Start watching..." );
        WatchKey key;
        try {
          key = watcher.take();
        } catch ( final InterruptedException ex ) {
          this.log.error( ex.toString() );
          break;
        }

        for ( final WatchEvent<?> event : key.pollEvents() ) {
          final WatchEvent.Kind<?> kind = event.kind();

          if ( kind == StandardWatchEventKinds.OVERFLOW ) {
            continue;
          }

          @SuppressWarnings( "unchecked" )
          final WatchEvent<Path> ev = (WatchEvent<Path>) event;
          final Path fileName = ev.context();

          this.log.info( kind.name() + ": " + fileName );
          if ( kind == StandardWatchEventKinds.ENTRY_CREATE ) {
            this.isFileCreated = true;
            this.log.info( "The file was created: " + fileName );
          }
        }

        final boolean valid = key.reset();
        if ( !valid ) {
          break;
        }
      }
    } catch ( final Exception e ) {
      this.log.error( e.toString() );
    }

    this.log.debug( "Stop watched!" );
    return this.isFileCreated;
  }
}

public class DirectoryWatcher {
  // Log instance
  private final Logger log = LogManager.getLogger( DirectoryWatcher.class );
  //Time to wait for a file or something new get in directory
  private final long waitTimeout = 30;

  /**
   * The default constructor.
   */
  public DirectoryWatcher() {
    super();
  }

  /**
   * Constructor
   *
   * @param path
   * @return
   */
  public boolean WatchForCreate( final String path ) {
    return this.WatchForCreate( path, this.waitTimeout );
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
  private boolean WatchForCreate( final String path, final long timeout ) {
    this.log.debug( "WatchForCreate::Enter" );
    boolean bFileCreated = false;
    final ExecutorService executor = Executors.newSingleThreadExecutor();

    final CallCheckFolder r = new CallCheckFolder( path );
    final Future<Boolean> future = executor.submit( r );

    try {
      this.log.debug( "Started..." );
      bFileCreated = future.get( timeout, TimeUnit.SECONDS );
      this.log.debug( "Finished!" );
    } catch ( final TimeoutException e ) {
      this.log.warn( e );
      future.cancel( true );
    } catch ( final Exception e ) {
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
