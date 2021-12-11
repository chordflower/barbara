/*
 * Copyright 2021 carddamom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cc.chordflower.desktop.barbara;

import cc.chordflower.desktop.barbara.initial.view.BarbaraEventHandler;
import cc.chordflower.desktop.barbara.initial.view.BarbaraMainWindow;
import cc.chordflower.desktop.barbara.initial.view.BarbaraMainWindow.BarbaraMainWindowEvents;
import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.lang3.SystemUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * This is the main class for the barbara application.
 *
 * @author carddamom
 */
public final class BarbaraApplication extends Application implements BarbaraEventHandler {

  private static Logger LOGGER = LoggerFactory.getLogger( BarbaraApplication.class );

  private Stage primaryStage;

  /**
   * Runs the javafx application.
   *
   * @param args The application arguments.
   */
  public static void main( String[] args ) {

    Application.launch( args );

  }

  /**
   * Creates the main screen.
   *
   * @param primaryStage The stage to use.
   */
  @Override
  public void start( @NotNull Stage primaryStage ) {

    this.primaryStage = primaryStage;

    String css = Objects.requireNonNull( BarbaraApplication.class.getResource( "/modena_dark.css" ) ).toExternalForm( );
    var root = new BarbaraMainWindow( );
    root.addListener( this );

    var scene = new Scene( root, 1280, 1024 );
    scene.getStylesheets( ).add( css );
    primaryStage.setScene( scene );
    primaryStage.show( );
    primaryStage.setTitle( "Barbara" );
  }

  @Override
  public void handle( Event event ) {

    switch( BarbaraMainWindowEvents.fromString( Objects.requireNonNull( event ).getEventType( ).getName( ) ) ) {
      case ENTER_EXIT_FULL_SCREEN -> {
        if( SystemUtils.IS_OS_MAC_OSX ) {
          this.primaryStage.setFullScreen( !this.primaryStage.isFullScreen( ) );
        } else {
          this.primaryStage.setMaximized( !this.primaryStage.isMaximized( ) );
        }
      }
      case HIDE_APP -> {
        this.primaryStage.setIconified( true );
      }
      case QUIT -> {
        this.primaryStage.close( );
        System.exit( 0 );
      }
      case SERVICES -> {
        if( SystemUtils.IS_OS_MAC_OSX ) {
          LOGGER.debug( "Please write something useful on the MacOS" );
        }
      }
      case UNKNOWN -> {
        LOGGER.warn( "I do not know which event this was..." );
      }
    }
  }

}
