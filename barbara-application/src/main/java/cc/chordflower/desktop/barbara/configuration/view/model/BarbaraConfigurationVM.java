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
package cc.chordflower.desktop.barbara.configuration.view.model;

import cc.chordflower.desktop.barbara.configuration.model.BarbaraConfiguration;
import org.jetbrains.annotations.Contract;
import org.spongepowered.configurate.serialize.SerializationException;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.util.Objects;

/**
 * This is the viewmodel for the barbara configuration.
 *
 * @author carddamom
 */
@Named( "BarbaraConfigurationVM" )
public class BarbaraConfigurationVM {

  private PathConfigurationVM paths;

  private final BarbaraConfiguration configuration;

  @Inject
  public BarbaraConfigurationVM( BarbaraConfiguration configuration ) {

    this.configuration = Objects.requireNonNull( configuration );
    this.paths = new PathConfigurationVM( configuration.Paths( ) );

  }

  /**
   * Returns the viewmodel for the path configuration.
   */
  @Contract( pure = true )
  public final PathConfigurationVM Paths( ) {

    return this.paths;

  }

  public void reset( ) {

    this.paths = new PathConfigurationVM( this.configuration.Paths( ) );

  }

  public void save( ) throws SerializationException {

    this.paths.save( );
    if( !this.configuration.validate( ).isEmpty( ) ) {
      throw new SerializationException( "There are validation errors!" );
    }
    this.configuration.saveToDefault( );

  }

  /**
   *  Validates if a given path is valid or not.
   *
   * @param newPath The new path to validate.
   * @return True if it valid, false otherwise.
   */
  public boolean validatePaths( String newPath ) {

    if( newPath == null || newPath.isEmpty( ) ) {
      return false;
    }
    var folders = new File( newPath );
    return folders.exists( ) && folders.isDirectory( ) && folders.canRead( ) && folders.canWrite( );
  }

}
