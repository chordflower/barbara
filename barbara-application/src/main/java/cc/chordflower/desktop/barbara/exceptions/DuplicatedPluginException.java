/*
 * Copyright 2021 carddamom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cc.chordflower.desktop.barbara.exceptions;

/**
 * Exception thrown when the plugin to add is already present in the plugin list.
 *
 * @author carddamom
 */
public class DuplicatedPluginException extends Exception {

  public DuplicatedPluginException( String name ) {

    super( "The plugin %s is already in the plugin list".formatted( name ) );

  }

}
