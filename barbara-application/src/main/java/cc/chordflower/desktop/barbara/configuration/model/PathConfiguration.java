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
import cc.chordflower.desktop.barbara.utilities.validator.Directory;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.configurate.ScopedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Contains configurations about some of the default paths used by barbara.
 *
 * @author carddamom
 */
public final class PathConfiguration implements ILoadableConfiguration {

  @SuppressWarnings( "unused" )
  private static final Logger LOGGER = LoggerFactory.getLogger( BarbaraConfiguration.class );

  private static final String VIDEOS_PROPERTY = "videos";

  private static final String MUSIC_PROPERTY = "music";

  private static final String IMAGES_PROPERTY = "images";

  private static final String FILES_PROPERTY = "files";

  private static final String CONTACTS_PROPERTY = "contacts";

  private static final String NOTES_PROPERTY = "notes";

  private static final String EMAIL_PROPERTY = "email";

  private static final String CALENDAR_PROPERTY = "calendar";

  @Directory
  private Path emailPath;

  @Directory
  private Path notesPath;

  @Directory
  private Path calendarPath;

  @Directory
  private Path contactsPath;

  @Directory
  private Path filesPath;

  @Directory
  private Path musicPath;

  @Directory
  private Path imagesPath;

  @Directory
  private Path videosPath;

  public PathConfiguration( ) {

    this.emailPath = Paths.get( UserPathConfiguration.getCurrentUserPathConfiguration( ).DocumentsDirectory( ).toString( ), "barbara", "emails" ).toAbsolutePath( ).normalize( );
    this.notesPath = Paths.get( UserPathConfiguration.getCurrentUserPathConfiguration( ).DocumentsDirectory( ).toString( ), "barbara", PathConfiguration.NOTES_PROPERTY ).toAbsolutePath( ).normalize( );
    this.calendarPath = Paths.get( UserPathConfiguration.getCurrentUserPathConfiguration( ).DocumentsDirectory( ).toString( ), "barbara", "calendar" ).toAbsolutePath( ).normalize( );
    this.contactsPath = Paths.get( UserPathConfiguration.getCurrentUserPathConfiguration( ).DocumentsDirectory( ).toString( ), "barbara", PathConfiguration.CONTACTS_PROPERTY ).toAbsolutePath( ).normalize( );
    this.filesPath = Paths.get( UserPathConfiguration.getCurrentUserPathConfiguration( ).DocumentsDirectory( ).toString( ), "barbara" ).toAbsolutePath( ).normalize( );
    this.musicPath = Paths.get( UserPathConfiguration.getCurrentUserPathConfiguration( ).MusicDirectory( ).toString( ), "barbara" ).toAbsolutePath( ).normalize( );
    this.imagesPath = Paths.get( UserPathConfiguration.getCurrentUserPathConfiguration( ).PicturesDirectory( ).toString( ), "barbara" ).toAbsolutePath( ).normalize( );
    this.videosPath = Paths.get( UserPathConfiguration.getCurrentUserPathConfiguration( ).VideoDirectory( ).toString( ), "barbara" ).toAbsolutePath( ).normalize( );

  }

  /**
   * Loads the configuration from the given node.
   *
   * @param node The node to load the configuration from.
   */
  @Override
  public void loadFrom( ScopedConfigurationNode< ? > node ) {

    Objects.requireNonNull( node, "The given node is null" );

    if( node.hasChild( PathConfiguration.EMAIL_PROPERTY ) ) {

      this.emailPath = Paths.get( node.node( PathConfiguration.EMAIL_PROPERTY ).getString( "" ) );

    }

    if( node.hasChild( PathConfiguration.NOTES_PROPERTY ) ) {

      this.notesPath = Paths.get( node.node( PathConfiguration.NOTES_PROPERTY ).getString( "" ) );

    }

    if( node.hasChild( PathConfiguration.CALENDAR_PROPERTY ) ) {

      this.calendarPath = Paths.get( node.node( PathConfiguration.CALENDAR_PROPERTY ).getString( "" ) );

    }

    if( node.hasChild( PathConfiguration.CONTACTS_PROPERTY ) ) {

      this.contactsPath = Paths.get( node.node( PathConfiguration.CONTACTS_PROPERTY ).getString( "" ) );

    }

    if( node.hasChild( PathConfiguration.FILES_PROPERTY ) ) {

      this.filesPath = Paths.get( node.node( PathConfiguration.FILES_PROPERTY ).getString( "" ) );

    }

    if( node.hasChild( PathConfiguration.MUSIC_PROPERTY ) ) {

      this.musicPath = Paths.get( node.node( PathConfiguration.MUSIC_PROPERTY ).getString( "" ) );

    }

    if( node.hasChild( PathConfiguration.IMAGES_PROPERTY ) ) {

      this.imagesPath = Paths.get( node.node( PathConfiguration.IMAGES_PROPERTY ).getString( "" ) );

    }

    if( node.hasChild( PathConfiguration.VIDEOS_PROPERTY ) ) {

      this.videosPath = Paths.get( node.node( PathConfiguration.VIDEOS_PROPERTY ).getString( "" ) );

    }

  }

  /**
   * Saves the current configuration to the given node.
   *
   * @param node The node to save the configuration to.
   */
  @Override
  public void saveTo( ScopedConfigurationNode< ? > node ) throws SerializationException {

    Objects.requireNonNull( node, "The given node is null" );

    node.node( PathConfiguration.CALENDAR_PROPERTY ).set( String.class, this.calendarPath.toString( ) );
    node.node( PathConfiguration.CONTACTS_PROPERTY ).set( String.class, this.contactsPath.toString( ) );
    node.node( PathConfiguration.EMAIL_PROPERTY ).set( String.class, this.emailPath.toString( ) );
    node.node( PathConfiguration.FILES_PROPERTY ).set( String.class, this.filesPath.toString( ) );
    node.node( PathConfiguration.IMAGES_PROPERTY ).set( String.class, this.imagesPath.toString( ) );
    node.node( PathConfiguration.MUSIC_PROPERTY ).set( String.class, this.musicPath.toString( ) );
    node.node( PathConfiguration.NOTES_PROPERTY ).set( String.class, this.notesPath.toString( ) );
    node.node( PathConfiguration.VIDEOS_PROPERTY ).set( String.class, this.videosPath.toString( ) );

  }

  /**
   * Returns the email path.
   */
  @Contract( pure = true )
  public Path getEmailPath( ) {

    return this.emailPath;

  }

  @Contract( mutates = "this" )
  public void setEmailPath( Path emailPath ) {

    this.emailPath = emailPath;

  }

  /**
   * Returns the notes path.
   */
  @Contract( pure = true )
  public Path getNotesPath( ) {

    return this.notesPath;

  }

  @Contract( mutates = "this" )
  public void setNotesPath( Path notesPath ) {

    this.notesPath = notesPath;

  }

  /**
   * Returns the calendar path.
   */
  @Contract( pure = true )
  public Path getCalendarPath( ) {

    return this.calendarPath;

  }

  @Contract( mutates = "this" )
  public void setCalendarPath( Path calendarPath ) {

    this.calendarPath = calendarPath;

  }

  /**
   * Returns the contacts path.
   */
  @Contract( pure = true )
  public Path getContactsPath( ) {

    return this.contactsPath;

  }

  @Contract( mutates = "this" )
  public void setContactsPath( Path contactsPath ) {

    this.contactsPath = contactsPath;

  }

  /**
   * Returns the files path.
   */
  @Contract( pure = true )
  public Path getFilesPath( ) {

    return this.filesPath;

  }

  @Contract( mutates = "this" )
  public void setFilesPath( Path filesPath ) {

    this.filesPath = filesPath;

  }

  /**
   * Returns the music path.
   */
  @Contract( pure = true )
  public Path getMusicPath( ) {

    return this.musicPath;

  }

  @Contract( mutates = "this" )
  public void setMusicPath( Path musicPath ) {

    this.musicPath = musicPath;

  }

  /**
   * Returns the images path.
   */
  @Contract( pure = true )
  public Path getImagesPath( ) {

    return this.imagesPath;

  }

  @Contract( mutates = "this" )
  public void setImagesPath( Path imagesPath ) {

    this.imagesPath = imagesPath;

  }

  /**
   * Returns the videos path.
   */
  @Contract( pure = true )
  public Path getVideosPath( ) {

    return this.videosPath;

  }

  @Contract( mutates = "this" )
  public void setVideosPath( Path videosPath ) {

    this.videosPath = videosPath;

  }

  @Override
  public int hashCode( ) {

    return Objects.hash( this.calendarPath, this.contactsPath, this.emailPath, this.filesPath, this.imagesPath, this.musicPath, this.notesPath, this.videosPath );

  }

  @Contract( value = "null -> false", pure = true )
  @Override
  public boolean equals( Object obj ) {

    if( this == obj ) {

      return true;

    }
    if( !( obj instanceof PathConfiguration other ) ) {

      return false;

    }
    return Objects.equals( this.calendarPath, other.calendarPath ) && Objects.equals( this.contactsPath, other.contactsPath ) && Objects.equals( this.emailPath, other.emailPath ) && Objects.equals( this.filesPath, other.filesPath ) && Objects.equals( this.imagesPath, other.imagesPath ) && Objects.equals( this.musicPath, other.musicPath ) && Objects.equals( this.notesPath, other.notesPath ) && Objects.equals( this.videosPath, other.videosPath );

  }

  @Override
  public String toString( ) {

    //@formatter:off
    return new ToStringBuilder( this, ToStringStyle.JSON_STYLE )
        .append( PathConfiguration.CALENDAR_PROPERTY, this.calendarPath )
        .append( PathConfiguration.CONTACTS_PROPERTY, this.contactsPath )
        .append( PathConfiguration.EMAIL_PROPERTY, this.emailPath )
        .append( PathConfiguration.FILES_PROPERTY, this.filesPath )
        .append( PathConfiguration.IMAGES_PROPERTY, this.imagesPath )
        .append( PathConfiguration.MUSIC_PROPERTY, this.musicPath )
        .append( PathConfiguration.NOTES_PROPERTY, this.notesPath )
        .append( PathConfiguration.VIDEOS_PROPERTY, this.videosPath )
        .build( );
    //@formatter:on

  }

}
