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

import cc.chordflower.desktop.barbara.configuration.model.PathConfiguration;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

/**
 * This is the viewmovel for the path configuration.
 *
 * @author carddamom
 */
public final class PathConfigurationVM {

  private final ObjectProperty< File > emailPath;

  private final ObjectProperty< File > notesPath;

  private final ObjectProperty< File > calendarPath;

  private final ObjectProperty< File > contactsPath;

  private final ObjectProperty< File > filesPath;

  private final ObjectProperty< File > musicPath;

  private final ObjectProperty< File > imagesPath;

  private final ObjectProperty< File > videosPath;

  private final PathConfiguration paths;

  public PathConfigurationVM( @NotNull PathConfiguration paths ) {

    this.paths = Objects.requireNonNull( paths );
    this.calendarPath = new SimpleObjectProperty<>( paths.getCalendarPath( ).toFile( ) );
    this.contactsPath = new SimpleObjectProperty<>( paths.getContactsPath( ).toFile( ) );
    this.emailPath = new SimpleObjectProperty<>( paths.getEmailPath( ).toFile( ) );
    this.filesPath = new SimpleObjectProperty<>( paths.getFilesPath( ).toFile( ) );
    this.imagesPath = new SimpleObjectProperty<>( paths.getImagesPath( ).toFile( ) );
    this.musicPath = new SimpleObjectProperty<>( paths.getMusicPath( ).toFile( ) );
    this.notesPath = new SimpleObjectProperty<>( paths.getNotesPath( ).toFile( ) );
    this.videosPath = new SimpleObjectProperty<>( paths.getVideosPath( ).toFile( ) );

  }

  /**
   * Returns the email path property.
   */
  @Contract( pure = true )
  public ObjectProperty< File > EmailPath( ) {

    return this.emailPath;

  }

  /**
   * Returns the notes path property.
   */
  @Contract( pure = true )
  public ObjectProperty< File > NotesPath( ) {

    return this.notesPath;

  }

  /**
   * Returns the calendar path property.
   */
  @Contract( pure = true )
  public ObjectProperty< File > CalendarPath( ) {

    return this.calendarPath;

  }

  /**
   * Returns the contacts path property.
   */
  @Contract( pure = true )
  public ObjectProperty< File > ContactsPath( ) {

    return this.contactsPath;

  }

  /**
   * Returns the files path property.
   */
  @Contract( pure = true )
  public ObjectProperty< File > FilesPath( ) {

    return this.filesPath;

  }

  /**
   * Returns the music path property.
   */
  @Contract( pure = true )
  public ObjectProperty< File > MusicPath( ) {

    return this.musicPath;

  }

  /**
   * Returns the images path property.
   */
  @Contract( pure = true )
  public ObjectProperty< File > ImagesPath( ) {

    return this.imagesPath;

  }

  /**
   * Returns the videos path property.
   */
  @Contract( pure = true )
  public ObjectProperty< File > VideosPath( ) {

    return this.videosPath;

  }

  void save( ) {

    this.paths.setCalendarPath( this.calendarPath.get( ).toPath( ) );
    this.paths.setContactsPath( this.contactsPath.get( ).toPath( ) );
    this.paths.setEmailPath( this.emailPath.get( ).toPath( ) );
    this.paths.setFilesPath( this.filesPath.get( ).toPath( ) );
    this.paths.setImagesPath( this.imagesPath.get( ).toPath( ) );
    this.paths.setMusicPath( this.musicPath.get( ).toPath( ) );
    this.paths.setNotesPath( this.notesPath.get( ).toPath( ) );
    this.paths.setVideosPath( this.videosPath.get( ).toPath( ) );

  }

}
