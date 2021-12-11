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
package cc.chordflower.desktop.barbara.configuration.model;

import cc.chordflower.desktop.barbara.utilities.UserPathConfiguration;
import com.machinezoo.noexception.Exceptions;
import dagger.Provides;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.configurate.ScopedConfigurationNode;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.xml.XmlConfigurationLoader;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This is the root of the barbara configuration.
 *
 * @author carddamom
 */
public final class BarbaraConfiguration implements ILoadableConfiguration {

  @dagger.Module
  public static class Module {

    @SuppressWarnings( "ResultOfMethodCallIgnored" )
    @Contract( " -> new" )
    @Provides
    public static @NotNull BarbaraConfiguration provideConfiguration( ) {

      Path jsonConfigFile = Paths.get( UserPathConfiguration.getCurrentUserPathConfiguration( ).ApplicationConfiguration( ).normalize( ).toAbsolutePath( ).toString( ),
          "config.json" );

      Path yamlConfigFile = Paths.get( UserPathConfiguration.getCurrentUserPathConfiguration( ).ApplicationConfiguration( ).normalize( ).toAbsolutePath( ).toString( ),
          "config.yaml" );

      Path tomlConfigFile = Paths.get( UserPathConfiguration.getCurrentUserPathConfiguration( ).ApplicationConfiguration( ).normalize( ).toAbsolutePath( ).toString( ),
          "config.toml" );

      Path xmlConfigFile = Paths.get( UserPathConfiguration.getCurrentUserPathConfiguration( ).ApplicationConfiguration( ).normalize( ).toAbsolutePath( ).toString( ),
          "config.xml" );

      AtomicReference< BarbaraConfiguration > configuration = new AtomicReference<>( null );

      if( jsonConfigFile.toFile( ).exists( ) && jsonConfigFile.toFile( ).isFile( ) && jsonConfigFile.toFile( ).canRead( ) ) {
        Exceptions.wrap( ).runnable( ( ) -> {
          GsonConfigurationLoader configurationLoader = GsonConfigurationLoader.builder( )
              .indent( 2 )
              .lenient( true )
              .build( );
          configuration.set( new BarbaraConfiguration( ) );
          configuration.get( ).loadFrom( configurationLoader.load( ) );
        } );
      } else if( yamlConfigFile.toFile( ).exists( ) && yamlConfigFile.toFile( ).isFile( ) && yamlConfigFile.toFile( ).canRead( ) ) {
        Exceptions.wrap( ).runnable( ( ) -> {
          YamlConfigurationLoader configurationLoader = YamlConfigurationLoader.builder( )
              .indent( 2 )
              .nodeStyle( NodeStyle.BLOCK )
              .build( );
          configuration.set( new BarbaraConfiguration( ) );
          configuration.get( ).loadFrom( configurationLoader.load( ) );
        } );
      } else if( xmlConfigFile.toFile( ).exists( ) && xmlConfigFile.toFile( ).isFile( ) && xmlConfigFile.toFile( ).canRead( ) ) {
        Exceptions.wrap( ).runnable( ( ) -> {
          XmlConfigurationLoader configurationLoader = XmlConfigurationLoader.builder( )
              .indent( 2 )
              .includesXmlDeclaration( true )
              .build( );
          configuration.set( new BarbaraConfiguration( ) );
          configuration.get( ).loadFrom( configurationLoader.load( ) );
        } );
      }

      return Objects.requireNonNull( configuration.get( ), "Unable to load the configuration" );
    }

  }

  @SuppressWarnings( "unused" )
  private static final Logger LOGGER = LoggerFactory.getLogger( BarbaraConfiguration.class );

  private static final String PATHS_PROPERTY = "paths";

  private ScopedConfigurationNode< ? > defaultNode;

  @Valid
  private final PathConfiguration paths = new PathConfiguration( );

  public BarbaraConfiguration( ) {

  }

  public BarbaraConfiguration( ScopedConfigurationNode< ? > defaultNode ) {

    this.defaultNode = Objects.requireNonNull( defaultNode, "The defaultNode must not be null" );

  }

  @Contract( pure = true )
  public Optional< ScopedConfigurationNode< ? > > defaultNode( ) {

    return Optional.ofNullable( this.defaultNode );

  }

  @Override
  public void loadFrom( @NotNull ScopedConfigurationNode< ? > node ) {

    Objects.requireNonNull( node );
    this.paths.loadFrom( node.node( BarbaraConfiguration.PATHS_PROPERTY ) );
    this.defaultNode = node;

  }

  public void loadFromDefault( ) {

    this.loadFrom( Objects.requireNonNull( this.defaultNode, "The default node must not be null" ) );

  }

  @Override
  public void saveTo( @NotNull ScopedConfigurationNode< ? > node ) throws SerializationException {

    Objects.requireNonNull( node );
    this.paths.saveTo( node.node( BarbaraConfiguration.PATHS_PROPERTY ) );

  }

  public void saveToDefault( ) throws SerializationException {

    this.saveTo( Objects.requireNonNull( this.defaultNode, "The default node must not be null" ) );

  }

  @Contract( pure = true )
  public PathConfiguration Paths( ) {

    return this.paths;

  }

  @Contract( " -> new" )
  public @NotNull List< ConstraintViolation< BarbaraConfiguration > > validate( ) {

    return new ArrayList<>( Validation.buildDefaultValidatorFactory( ).getValidator( ).validate( this ) );
  }

}
