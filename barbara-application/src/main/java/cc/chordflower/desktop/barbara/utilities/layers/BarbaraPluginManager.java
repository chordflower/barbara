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
package cc.chordflower.desktop.barbara.utilities.layers;

import cc.chordflower.desktop.barbara.exceptions.DuplicatedPluginException;
import cc.chordflower.desktop.barbara.exceptions.InvalidDependencyException;
import com.google.gson.Gson;
import org.jetbrains.annotations.Contract;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Class that manages the barbara plugins.
 *
 * @author carddamom
 */
@Named( "BarbaraPluginManager" )
@Singleton
public class BarbaraPluginManager {

  private final ConcurrentMap< UUID, PluginDescriptor > plugins;

  private final Gson gayson;

  @Contract( pure = true )
  @Inject
  public BarbaraPluginManager( Gson gson ) {

    this.plugins = new ConcurrentHashMap<>( );
    this.gayson = gson;
  }

  /**
   * Tries to add the plugin in the given path to the plugin manager.
   *
   * @param plugin The path of the plugin to add.
   *
   * @throws IOException If an error happens while manipulating the plugin file.
   * @throws InvalidDependencyException If there is an problem with the dependencies of the plugin.
   * @throws DuplicatedPluginException If the plugin is already installed.
   */
  public void addPlugin( Path plugin ) throws IOException, InvalidDependencyException, DuplicatedPluginException {

    // Check if the parameter is not null.
    File pluginFile = Objects.requireNonNull( plugin ).toFile( );

    // Check if the path represents a existing file that ends with the zip extension, ie. is a zip file.
    if( !pluginFile.exists( ) || !pluginFile.isFile( ) || !pluginFile.getName( ).endsWith( "*.zip" ) ) {
      throw new IOException( "Unable to use the given plugin file" );
    }

    PluginDescriptor pluginDescritor;

    // Opens the plugin zip file, and tries to parse the plugin descriptor (in plugin.json on the root
    // of the zip) using Gson into a PluginDescriptor class.
    try {

      URI uri = new URI( "jar:file:%s".formatted( plugin.toString( ) ) );

      String lines;
      try( FileSystem fileSystem = FileSystems.newFileSystem( uri, Collections.emptyMap( ) ) ) {

        Path path = fileSystem.getPath( "/", "plugin.json" );
        if( !path.toFile( ).exists( ) || !path.toFile( ).isFile( ) ) {
          throw new IOException( "Unable to open the plugin.json file" );
        }

        lines = String.join( "\n", Files.readAllLines( path ) );

      }
      pluginDescritor = this.gayson.fromJson( lines, PluginDescriptor.class ).withPath( plugin );

    } catch( URISyntaxException ex ) {
      throw new IOException( "Unable to open the plugin file", ex );
    }

    // Check of plugin already exists...
    if( this.plugins.containsKey( pluginDescritor.Id( ) ) ) {
      throw new DuplicatedPluginException( pluginDescritor.Name( ) );
    }

    // Check of all the dependencies are present...
    for( PluginDependency dependency : pluginDescritor.Dependencies( ) ) {

      if( dependency.Id( ).equals( pluginDescritor.Id( ) ) ) {
        throw new InvalidDependencyException( "A plugin cannot have a dependency on itself" );
      } else if( this.plugins.containsKey( dependency.Id( ) ) ) {
        var existingPlugin = this.plugins.get( dependency.Id( ) );
        if( !existingPlugin.SemverVersion( ).satisfies( dependency.Version( ) ) ) {
          throw new InvalidDependencyException( "Unable to satisfy the dependency on %s of the plugin %s - Version mismatch".formatted(
              existingPlugin.Name( ), pluginDescritor.Name( ) ) );
        }
      } else {
        throw new InvalidDependencyException( "Unable to satisfy the dependency on %s of the plugin %s - Non-existing plugin".formatted(
            dependency.Id( ).toString( ), pluginDescritor.Name( ) ) );
      }

    }

    // Add the plugin to the list of known installed plugins.
    this.plugins.putIfAbsent( pluginDescritor.Id( ), pluginDescritor );

  }

}
