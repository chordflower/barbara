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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.machinezoo.noexception.Exceptions;
import com.vdurmont.semver4j.Semver;
import com.vdurmont.semver4j.Semver.SemverType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.UnmodifiableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.io.Serializable;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * This class represents a plugin and contains information about it.
 *
 * @author carddamom
 */
public final class PluginDescriptor implements Serializable {

  @SuppressWarnings( "unused" )
  private static final Logger LOGGER = LoggerFactory.getLogger( PluginDescriptor.class );

  /**
   * The plugin name that is shown to the user
   * (Required)
   *
   */
  @SerializedName( "name" )
  @Expose
  @Size( min = 5 )
  @NotNull
  private String name;

  /**
   * The internal plugin id, must be unique
   * (Required)
   *
   */
  @SerializedName( "$id" )
  @Expose
  @NotNull
  private UUID id;

  /**
   * A small description of what the plugin does
   *
   */
  @SerializedName( "description" )
  @Expose
  private String description;

  /**
   * The SPDXLicense id, of the plugin license
   * (Required)
   *
   */
  @SerializedName( "license" )
  @Expose
  @Size( min = 2 )
  @NotNull
  private String license;

  /**
   * A semver string containing the version of the plugin
   * (Required)
   *
   */
  @SerializedName( "version" )
  @Expose
  @Size( min = 1 )
  @NotNull
  private String version;

  /**
   * A list of plugin dependencies of this plugin
   *
   */
  @SerializedName( "dependencies" )
  @Expose
  @Valid
  private List< PluginDependency > dependencies = new ArrayList<>( );

  @Serial
  private final static long serialVersionUID = 2197161139153546992L;

  private transient Path path;

  private final transient List< Path > jarPaths;

  private transient Semver semverVersion;

  /**
   * Creates a new instance of PluginDescriptor.
   *
   *
   * @param name The name of the plugin.
   * @param id The plugin id.
   * @param description The plugin description.
   * @param license The plugin license.
   * @param version The plugin version.
   * @param dependencies The plugin dependencies.
   * @param path The plugin path.
   * @return A plugin descriptor.
   */
  @Contract( "_, _, _, _, _, _, _ -> new" )
  public static @org.jetbrains.annotations.NotNull PluginDescriptor of( String name, UUID id, String description, String license, String version, List< PluginDependency > dependencies, Path path ) {

    return new PluginDescriptor( name, id, description, license, version, dependencies, path );

  }

  private PluginDescriptor( String name, UUID id, String description, String license, String version, List< PluginDependency > dependencies, Path path ) {

    this.name = name;
    this.id = id;
    this.description = description;
    this.license = license;
    this.version = version;
    this.dependencies = dependencies;
    this.path = path;
    this.jarPaths = new ArrayList<>( );

    Exceptions.log( LOGGER ).passing( ).run( ( ) -> Exceptions.wrap( ).run( ( ) -> {
      URI uri = new URI( "jar:file:%s".formatted( this.path ) );
      try( FileSystem pluginFS = FileSystems.newFileSystem( uri, Collections.emptyMap( ) ); ) {
        var matcher = pluginFS.getPathMatcher( "glob:**/*.jar" );
        Files.walkFileTree( pluginFS.getPath( "/" ), new SimpleFileVisitor<>( ) {

          @Override
          public FileVisitResult visitFile( Path file, BasicFileAttributes attrs ) {

            if( matcher.matches( file ) ) {
              jarPaths.add( file );
            }
            return FileVisitResult.CONTINUE;
          }
        } );
      }
    } ) );

    this.semverVersion = new Semver( version, SemverType.NPM );

  }

  /**
   * For usage by gjon
   */
  private PluginDescriptor( ) {

    this.jarPaths = Collections.emptyList( );
  }

  /**
   * Returns the plugin name.
   */
  @Contract( pure = true )
  public String Name( ) {

    return this.name;

  }

  /**
   * Returns the plugin id.
   */
  @Contract( pure = true )
  public UUID Id( ) {

    return this.id;

  }

  /**
   * Returns the plugin description.
   */
  @Contract( pure = true )
  public String Description( ) {

    return this.description;

  }

  /**
   * Returns the plugin spdx license id.
   */
  @Contract( pure = true )
  public String License( ) {

    return this.license;

  }

  /**
   * Returns the plugin version.
   */
  @Contract( pure = true )
  public String Version( ) {

    return this.version;

  }

  /**
   * Returns the plugin dependencies.
   */
  @Contract( pure = true )
  public @org.jetbrains.annotations.NotNull @UnmodifiableView List< PluginDependency > Dependencies( ) {

    return Collections.unmodifiableList( this.dependencies );

  }

  /**
   * Returns the plugin path.
   */
  @Contract( pure = true )
  public Path Path( ) {

    return this.path;

  }

  /**
   * Returns the plugin jar paths.
   */
  @Contract( pure = true )
  public @org.jetbrains.annotations.NotNull @UnmodifiableView List< Path > JarPaths( ) {

    return Collections.unmodifiableList( this.jarPaths );

  }

  /**
   * Returns a new plugin descriptor with the given path information added.
   *
   * @param path The path to use.
   * @return A new instance of plugin descriptor similar to this one, but with the given path information.
   */
  @Contract( "_ -> new" )
  public @org.jetbrains.annotations.NotNull PluginDescriptor withPath( Path path ) {

    return new PluginDescriptor( this.name, this.id, this.description, this.license, this.version, this.dependencies, path );

  }

  @Contract( pure = true )
  public Semver SemverVersion( ) {

    return semverVersion;
  }

  @Contract( pure = true )
  public void validate( ) {

  }

}
