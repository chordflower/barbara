package cc.chordflower.desktop.barbara.utilities.i18n;

import com.machinezoo.noexception.Exceptions;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

@Named( "BarbaraResourceBundle" )
@Singleton
public class BarbaraResourceBundle {

  public enum Keys {

    Paths( "PreferencesView", "PreferencesView.Paths" ),
    EmailStorageFolder( "PreferencesView", "PreferencesView.EmailStorageFolder" ),
    CalendarStorageFolder( "PreferencesView", "PreferencesView.CalendarStorageFolder" ),
    NotesStorageFolder( "PreferencesView", "PreferencesView.NotesStorageFolder" ),
    ContactsStorageFolder( "PreferencesView", "PreferencesView.ContactsStorageFolder" ),
    FilesStorageFolder( "PreferencesView", "PreferencesView.FilesStorageFolder" ),
    MusicStorageFolder( "PreferencesView", "PreferencesView.MusicStorageFolder" ),
    ImagesStorageFolder( "PreferencesView", "PreferencesView.ImagesStorageFolder" ),
    VideosStorageFolder( "PreferencesView", "PreferencesView.VideosStorageFolder" ),
    PreferencesDialogTitle( "PreferencesView", "PreferencesView.PreferencesDialogTitle" ),
    Unknown( "Unknown", "Unknown" );

    private final String KeyPath;

    private final String KeyName;

    @Contract( pure = true )
    Keys( String keyPath, String keyName ) {

      this.KeyPath = keyPath;
      this.KeyName = keyName;

    }

    @Contract( pure = true )
    public String KeyPath( ) {

      return this.KeyPath;

    }

    @Contract( pure = true )
    public String KeyName( ) {

      return this.KeyName;

    }

  }

  private static final Logger LOGGER = LoggerFactory.getLogger( BarbaraResourceBundle.class );

  private static final String BUNDLE_NAME = "translations.messages"; //$NON-NLS-1$

  private final ResourceBundle resourceBundle;

  private final Map< Locale, ResourceBundle > resourceBundles = new ConcurrentHashMap<>( );

  @Inject
  public BarbaraResourceBundle( ) {

    this.resourceBundle = ResourceBundle.getBundle( BarbaraResourceBundle.BUNDLE_NAME );
    this.resourceBundles.putIfAbsent( Locale.getDefault( ), this.resourceBundle );

  }

  public String getTranslation( Keys key ) {

    return Exceptions.log( BarbaraResourceBundle.LOGGER, String.format( "Unable to find message for key %s", key ) ).get( ( ) -> this.resourceBundle.getString( key.KeyName ) ).orElse( key.KeyName );

  }

  public synchronized String getTranslation( Keys key, Locale locale ) {

    var bundle = this.resourceBundles.getOrDefault( locale, Objects.requireNonNull( ResourceBundle.getBundle( BarbaraResourceBundle.BUNDLE_NAME, locale ), "Unable to find bundle for the specified locale" ) ); //$NON-NLS-1$
    this.resourceBundles.putIfAbsent( locale, bundle );
    return Exceptions.log( BarbaraResourceBundle.LOGGER, String.format( "Unable to find message for key %s", key ) ).get( ( ) -> bundle.getString( key.KeyName ) ).orElse( key.KeyName );

  }

}
