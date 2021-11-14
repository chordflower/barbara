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
package cc.chordflower.desktop.barbara.utilities;

import cc.chordflower.desktop.barbara.exceptions.UnableToDetermineSystemException;
import org.apache.commons.lang3.SystemUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

/**
 * Contains information about the user specific paths, for:
 *
 * - Application Cache;
 * - Application Configuration;
 * - Application Data;
 * - Application Log Directory;
 * - User Home Directory;
 * - Temporary Directory;
 * - User Desktop Directory;
 * - User Documents Directory;
 * - User Downloads Directory;
 * - User Music Directory;
 * - User Pictures Directory;
 * - User Public Directory;
 * - User Templates Directory;
 * - User Video Directory.
 *
 * For the three supported operating systems:
 *
 * - Windows;
 * - MacOS;
 * - Linux;
 *
 * @author carddamom
 *
 */
public abstract class UserPathConfiguration {

  private static class LinuxUserPathConfiguration extends UserPathConfiguration {

    private final Path videoDirectory;

    private final Path temporaryDirectory;

    private final Path templatesDirectory;

    private final Path publicDirectory;

    private final Path picturesDirectory;

    private final Path musicDirectory;

    private final Path home;

    private final Path downloadsDirectory;

    private final Path documentsDirectory;

    private final Path desktopDirectory;

    private final Path applicationLogsDirectory;

    private final Path applicationData;

    private final Path applicationConfiguration;

    private final Path applicationCache;

    public LinuxUserPathConfiguration( ) {

      var homedir = Objects.requireNonNull( SystemUtils.USER_HOME, "Unable to determine user homedir" );
      var username = Objects.requireNonNull( SystemUtils.USER_NAME, "Unable to determine user name" );
      var tmpdir = Objects.requireNonNull( SystemUtils.JAVA_IO_TMPDIR, "Unable to determine temporary directory location" );
      var xdg_datahome = System.getenv( "XDG_DATA_HOME" );
      var xdg_confighome = System.getenv( "XDG_CONFIG_HOME" );
      var xdg_cachehome = System.getenv( "XDG_CACHE_HOME" );
      var xdg_statehome = System.getenv( "XDG_STATE_HOME" );
      var xdg_desktop_dir = System.getenv( "XDG_DESKTOP_DIR" );
      var xdg_documents_dir = System.getenv( "XDG_DOCUMENTS_DIR" );
      var xdg_download_dir = System.getenv( "XDG_DOWNLOAD_DIR" );
      var xdg_music_dir = System.getenv( "XDG_MUSIC_DIR" );
      var xdg_pictures_dir = System.getenv( "XDG_PICTURES_DIR" );
      var xdg_publicshare_dir = System.getenv( "XDG_PUBLICSHARE_DIR" );
      var xdg_templates_dir = System.getenv( "XDG_TEMPLATES_DIR" );
      var xdg_videos_dir = System.getenv( "XDG_VIDEOS_DIR" );

      this.applicationData = xdg_datahome != null ? Paths.get( xdg_datahome, "barbara" ) : Paths.get( homedir, ".local", "share", "barbara" );
      this.applicationConfiguration = xdg_confighome != null ? Paths.get( xdg_confighome, "barbara" ) : Paths.get( homedir, ".config", "barbara" );
      this.applicationCache = xdg_cachehome != null ? Paths.get( xdg_cachehome, "barbara" ) : Paths.get( homedir, ".cache", "barbara" );
      this.applicationLogsDirectory = xdg_statehome != null ? Paths.get( xdg_statehome, "barbara" ) : Paths.get( homedir, ".local", "state", "barbara" );
      this.temporaryDirectory = Paths.get( tmpdir, username, "barbara" );
      this.home = Paths.get( homedir );
      this.desktopDirectory = xdg_desktop_dir != null ? Paths.get( xdg_desktop_dir ) : Paths.get( homedir, "Desktop" );
      this.documentsDirectory = xdg_documents_dir != null ? Paths.get( xdg_documents_dir ) : Paths.get( homedir, "Documents" );
      this.downloadsDirectory = xdg_download_dir != null ? Paths.get( xdg_download_dir ) : Paths.get( homedir, "Downloads" );
      this.musicDirectory = xdg_music_dir != null ? Paths.get( xdg_music_dir ) : Paths.get( homedir, "Music" );
      this.picturesDirectory = xdg_pictures_dir != null ? Paths.get( xdg_pictures_dir ) : Paths.get( homedir, "Pictures" );
      this.publicDirectory = xdg_publicshare_dir != null ? Paths.get( xdg_publicshare_dir ) : Paths.get( homedir, "Public" );
      this.templatesDirectory = xdg_templates_dir != null ? Paths.get( xdg_templates_dir ) : Paths.get( homedir, "Templates" );
      this.videoDirectory = xdg_videos_dir != null ? Paths.get( xdg_videos_dir ) : Paths.get( homedir, "Videos" );

    }

    @Contract( pure = true )
    @Override
    public Path ApplicationCache( ) {

      return this.applicationCache;

    }

    @Contract( pure = true )
    @Override
    public Path ApplicationConfiguration( ) {

      return this.applicationConfiguration;

    }

    @Contract( pure = true )
    @Override
    public Path ApplicationData( ) {

      return this.applicationData;

    }

    @Contract( pure = true )
    @Override
    public Path ApplicationLogsDirectory( ) {

      return this.applicationLogsDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path DesktopDirectory( ) {

      return this.desktopDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path DocumentsDirectory( ) {

      return this.documentsDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path DownloadsDirectory( ) {

      return this.downloadsDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path Home( ) {

      return this.home;

    }

    @Contract( pure = true )
    @Override
    public Path MusicDirectory( ) {

      return this.musicDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path PicturesDirectory( ) {

      return this.picturesDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path PublicDirectory( ) {

      return this.publicDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path TemplatesDirectory( ) {

      return this.templatesDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path TemporaryDirectory( ) {

      return this.temporaryDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path VideoDirectory( ) {

      return this.videoDirectory;

    }

  }

  private static class MacOSUserPathConfiguration extends UserPathConfiguration {

    private final Path videoDirectory;

    private final Path temporaryDirectory;

    private final Path templatesDirectory;

    private final Path publicDirectory;

    private final Path picturesDirectory;

    private final Path musicDirectory;

    private final Path home;

    private final Path downloadsDirectory;

    private final Path documentsDirectory;

    private final Path desktopDirectory;

    private final Path applicationLogsDirectory;

    private final Path applicationData;

    private final Path applicationConfiguration;

    private final Path applicationCache;

    public MacOSUserPathConfiguration( ) {

      var homedir = Objects.requireNonNull( SystemUtils.USER_HOME, "Unable to determine user homedir" );
      var library = Paths.get( homedir, "Library" ).toString( );
      var tmpdir = Objects.requireNonNull( SystemUtils.JAVA_IO_TMPDIR, "Unable to determine temporary directory location" );
      var xdg_datahome = System.getenv( "XDG_DATA_HOME" );
      var xdg_confighome = System.getenv( "XDG_CONFIG_HOME" );
      var xdg_cachehome = System.getenv( "XDG_CACHE_HOME" );
      var xdg_statehome = System.getenv( "XDG_STATE_HOME" );
      var xdg_desktop_dir = System.getenv( "XDG_DESKTOP_DIR" );
      var xdg_documents_dir = System.getenv( "XDG_DOCUMENTS_DIR" );
      var xdg_download_dir = System.getenv( "XDG_DOWNLOAD_DIR" );
      var xdg_music_dir = System.getenv( "XDG_MUSIC_DIR" );
      var xdg_pictures_dir = System.getenv( "XDG_PICTURES_DIR" );
      var xdg_publicshare_dir = System.getenv( "XDG_PUBLICSHARE_DIR" );
      var xdg_templates_dir = System.getenv( "XDG_TEMPLATES_DIR" );
      var xdg_videos_dir = System.getenv( "XDG_VIDEOS_DIR" );

      this.applicationData = xdg_datahome != null ? Paths.get( xdg_datahome, "barbara" ) : Paths.get( library, "Application Support", "barbara" );
      this.applicationConfiguration = xdg_confighome != null ? Paths.get( xdg_confighome, "barbara" ) : Paths.get( library, "Preferences", "barbara" );
      this.applicationCache = xdg_cachehome != null ? Paths.get( xdg_cachehome, "barbara" ) : Paths.get( library, "Caches", "barbara" );
      this.applicationLogsDirectory = xdg_statehome != null ? Paths.get( xdg_statehome, "barbara" ) : Paths.get( library, "Logs", "barbara" );
      this.temporaryDirectory = Paths.get( tmpdir, "barbara" );
      this.home = Paths.get( homedir );
      this.desktopDirectory = xdg_desktop_dir != null ? Paths.get( xdg_desktop_dir ) : Paths.get( homedir, "Desktop" );
      this.documentsDirectory = xdg_documents_dir != null ? Paths.get( xdg_documents_dir ) : Paths.get( homedir, "Documents" );
      this.downloadsDirectory = xdg_download_dir != null ? Paths.get( xdg_download_dir ) : Paths.get( homedir, "Downloads" );
      this.musicDirectory = xdg_music_dir != null ? Paths.get( xdg_music_dir ) : Paths.get( homedir, "Music" );
      this.picturesDirectory = xdg_pictures_dir != null ? Paths.get( xdg_pictures_dir ) : Paths.get( homedir, "Pictures" );
      this.publicDirectory = xdg_publicshare_dir != null ? Paths.get( xdg_publicshare_dir ) : Paths.get( homedir, "Public" );
      this.templatesDirectory = xdg_templates_dir != null ? Paths.get( xdg_templates_dir ) : Paths.get( homedir, "Templates" );
      this.videoDirectory = xdg_videos_dir != null ? Paths.get( xdg_videos_dir ) : Paths.get( homedir, "Videos" );

    }

    @Contract( pure = true )
    @Override
    public Path ApplicationCache( ) {

      return this.applicationCache;

    }

    @Contract( pure = true )
    @Override
    public Path ApplicationConfiguration( ) {

      return this.applicationConfiguration;

    }

    @Contract( pure = true )
    @Override
    public Path ApplicationData( ) {

      return this.applicationData;

    }

    @Contract( pure = true )
    @Override
    public Path ApplicationLogsDirectory( ) {

      return this.applicationLogsDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path DesktopDirectory( ) {

      return this.desktopDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path DocumentsDirectory( ) {

      return this.documentsDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path DownloadsDirectory( ) {

      return this.downloadsDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path Home( ) {

      return this.home;

    }

    @Contract( pure = true )
    @Override
    public Path MusicDirectory( ) {

      return this.musicDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path PicturesDirectory( ) {

      return this.picturesDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path PublicDirectory( ) {

      return this.publicDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path TemplatesDirectory( ) {

      return this.templatesDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path TemporaryDirectory( ) {

      return this.temporaryDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path VideoDirectory( ) {

      return this.videoDirectory;

    }

  }

  private static class WindowsUserPathConfiguration extends UserPathConfiguration {

    private final Path videoDirectory;

    private final Path temporaryDirectory;

    private final Path templatesDirectory;

    private final Path publicDirectory;

    private final Path picturesDirectory;

    private final Path musicDirectory;

    private final Path home;

    private final Path downloadsDirectory;

    private final Path documentsDirectory;

    private final Path desktopDirectory;

    private final Path applicationLogsDirectory;

    private final Path applicationData;

    private final Path applicationConfiguration;

    private final Path applicationCache;

    public WindowsUserPathConfiguration( ) {

      var homedir = Objects.requireNonNull( SystemUtils.USER_HOME, "Unable to determine user homedir" );
      var appData = Optional.of( System.getenv( "APPDATA" ) ).orElse( Paths.get( homedir, "AppData", "Roaming" ).toString( ) );
      var localAppData = Optional.of( System.getenv( "LOCALAPPDATA" ) ).orElse( Paths.get( homedir, "AppData", "Local" ).toString( ) );
      var tmpdir = Objects.requireNonNull( SystemUtils.JAVA_IO_TMPDIR, "Unable to determine temporary directory location" );
      var xdg_datahome = System.getenv( "XDG_DATA_HOME" );
      var xdg_confighome = System.getenv( "XDG_CONFIG_HOME" );
      var xdg_cachehome = System.getenv( "XDG_CACHE_HOME" );
      var xdg_statehome = System.getenv( "XDG_STATE_HOME" );
      var xdg_desktop_dir = System.getenv( "XDG_DESKTOP_DIR" );
      var xdg_documents_dir = System.getenv( "XDG_DOCUMENTS_DIR" );
      var xdg_download_dir = System.getenv( "XDG_DOWNLOAD_DIR" );
      var xdg_music_dir = System.getenv( "XDG_MUSIC_DIR" );
      var xdg_pictures_dir = System.getenv( "XDG_PICTURES_DIR" );
      var xdg_publicshare_dir = System.getenv( "XDG_PUBLICSHARE_DIR" );
      var xdg_templates_dir = System.getenv( "XDG_TEMPLATES_DIR" );
      var xdg_videos_dir = System.getenv( "XDG_VIDEOS_DIR" );
      var public_dir = System.getenv( "PUBLIC" );

      this.applicationData = xdg_datahome != null ? Paths.get( xdg_datahome, "barbara" ) : Paths.get( localAppData, "barbara", "Data" );
      this.applicationConfiguration = xdg_confighome != null ? Paths.get( xdg_confighome, "barbara" ) : Paths.get( appData, "barbara", "Config" );
      this.applicationCache = xdg_cachehome != null ? Paths.get( xdg_cachehome, "barbara" ) : Paths.get( localAppData, "barbara", "Cache" );
      this.applicationLogsDirectory = xdg_statehome != null ? Paths.get( xdg_statehome, "barbara" ) : Paths.get( localAppData, "barbara", "Log" );
      this.temporaryDirectory = Paths.get( tmpdir, "barbara" );
      this.home = Paths.get( homedir );
      this.desktopDirectory = xdg_desktop_dir != null ? Paths.get( xdg_desktop_dir ) : Paths.get( homedir, "Desktop" );
      this.documentsDirectory = xdg_documents_dir != null ? Paths.get( xdg_documents_dir ) : Paths.get( homedir, "Documents" );
      this.downloadsDirectory = xdg_download_dir != null ? Paths.get( xdg_download_dir ) : Paths.get( homedir, "Downloads" );
      this.musicDirectory = xdg_music_dir != null ? Paths.get( xdg_music_dir ) : Paths.get( homedir, "Music" );
      this.picturesDirectory = xdg_pictures_dir != null ? Paths.get( xdg_pictures_dir ) : Paths.get( homedir, "Pictures" );
      this.publicDirectory = xdg_publicshare_dir != null ? Paths.get( xdg_publicshare_dir ) : ( public_dir != null ? Paths.get( public_dir ) : null );
      this.templatesDirectory = xdg_templates_dir != null ? Paths.get( xdg_templates_dir ) : Paths.get( appData, "Microsoft", "Windows", "Templates" );
      this.videoDirectory = xdg_videos_dir != null ? Paths.get( xdg_videos_dir ) : Paths.get( homedir, "Videos" );

    }

    @Contract( pure = true )
    @Override
    public Path ApplicationCache( ) {

      return this.applicationCache;

    }

    @Contract( pure = true )
    @Override
    public Path ApplicationConfiguration( ) {

      return this.applicationConfiguration;

    }

    @Contract( pure = true )
    @Override
    public Path ApplicationData( ) {

      return this.applicationData;

    }

    @Contract( pure = true )
    @Override
    public Path ApplicationLogsDirectory( ) {

      return this.applicationLogsDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path DesktopDirectory( ) {

      return this.desktopDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path DocumentsDirectory( ) {

      return this.documentsDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path DownloadsDirectory( ) {

      return this.downloadsDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path Home( ) {

      return this.home;

    }

    @Contract( pure = true )
    @Override
    public Path MusicDirectory( ) {

      return this.musicDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path PicturesDirectory( ) {

      return this.picturesDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path PublicDirectory( ) {

      return this.publicDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path TemplatesDirectory( ) {

      return this.templatesDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path TemporaryDirectory( ) {

      return this.temporaryDirectory;

    }

    @Contract( pure = true )
    @Override
    public Path VideoDirectory( ) {

      return this.videoDirectory;

    }

  }

  @Contract( " -> new" )
  public static @NotNull UserPathConfiguration getCurrentUserPathConfiguration( ) {

    if( SystemUtils.IS_OS_WINDOWS ) {

      return new WindowsUserPathConfiguration( );

    } else if( SystemUtils.IS_OS_MAC_OSX ) {

      return new MacOSUserPathConfiguration( );

    } else if( SystemUtils.IS_OS_UNIX ) {

      return new LinuxUserPathConfiguration( );

    } else {

      throw new UnableToDetermineSystemException( "Unable to determine operating system family for %s" );

    }

  }

  /**
   * Gets the path for the user application cache directory.
   */
  public abstract Path ApplicationCache( );

  /**
   * Gets the path for the user application configuration directory.
   */
  public abstract Path ApplicationConfiguration( );

  /**
   * Gets the path for the user application data directory.
   */
  public abstract Path ApplicationData( );

  /**
   * Gets the path for the user application logs directory.
   */
  public abstract Path ApplicationLogsDirectory( );

  /**
   * Gets the path for the user desktop directory.
   */
  public abstract Path DesktopDirectory( );

  /**
   * Gets the path for the user documents directory.
   */
  public abstract Path DocumentsDirectory( );

  /**
   * Gets the path for the user downloads directory.
   */
  public abstract Path DownloadsDirectory( );

  /**
   * Gets the path for the user home directory.
   */
  public abstract Path Home( );

  /**
   * Gets the path for the user music directory.
   */
  public abstract Path MusicDirectory( );

  /**
   * Gets the path for the user pictures directory.
   */
  public abstract Path PicturesDirectory( );

  /**
   * Gets the path for the user public directory.
   */
  public abstract Path PublicDirectory( );

  /**
   * Gets the path for the user templates directory.
   */
  public abstract Path TemplatesDirectory( );

  /**
   * Gets the path for the user temporary directory.
   */
  public abstract Path TemporaryDirectory( );

  /**
   * Gets the path for the user video directory.
   */
  public abstract Path VideoDirectory( );

}
