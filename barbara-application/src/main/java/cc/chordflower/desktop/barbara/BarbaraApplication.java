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

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the main class for the barbara application.
 *
 * @author carddamom
 */
public final class BarbaraApplication extends Application {

  /**
   * Runs the javafx application.
   *
   * @param args The application arguments.
   */
  public static void main( String[ ] args ) {

    Application.launch( args );

  }

  /**
   * Creates the main screen.
   *
   * @param primaryStage The stage to use.
   * @throws Exception If an exception happens.
   */
  @Override
  public void start( Stage primaryStage ) throws Exception {

    primaryStage.show( );

  }

}