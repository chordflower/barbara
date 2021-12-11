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
package cc.chordflower.desktop.barbara.configuration.view;

import cc.chordflower.desktop.barbara.configuration.view.model.BarbaraConfigurationVM;
import cc.chordflower.desktop.barbara.utilities.i18n.BarbaraResourceBundle;
import cc.chordflower.desktop.barbara.utilities.i18n.BarbaraResourceBundle.Keys;
import com.dlsc.formsfx.model.validators.CustomValidator;
import com.dlsc.preferencesfx.PreferencesFx;
import com.dlsc.preferencesfx.PreferencesFxEvent;
import com.dlsc.preferencesfx.model.Category;
import com.dlsc.preferencesfx.model.Setting;
import com.machinezoo.noexception.Exceptions;
import javafx.event.EventHandler;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Objects;

/**
 * This is the view that contains the PreferencesFX dialog.
 *
 * @author carddamom
 */
@Named( "PreferencesView" )
@Singleton
public final class PreferencesView implements EventHandler< PreferencesFxEvent > {

    private static final Logger LOGGER = LoggerFactory.getLogger( PreferencesView.class );

    private final PreferencesFx preferences;

    private final BarbaraConfigurationVM configVM;

    @Inject
    public PreferencesView( BarbaraConfigurationVM configVM, BarbaraResourceBundle bundle ) {

        Objects.requireNonNull( configVM );
        Objects.requireNonNull( bundle );
        this.configVM = configVM;
        //@formatter:off
    this.preferences = PreferencesFx.of( new NullStorageHandler( ),
      Category.of( bundle.getTranslation( Keys.Paths ),
        Setting.of( bundle.getTranslation(Keys.EmailStorageFolder), configVM.Paths( ).EmailPath( ), configVM.Paths( ).EmailPath( ).get( ), true ).validate( CustomValidator.forPredicate( configVM::validatePaths, "The given path is invalid"  ) ),
        Setting.of( bundle.getTranslation(Keys.CalendarStorageFolder), configVM.Paths( ).CalendarPath( ), configVM.Paths( ).CalendarPath( ).get( ), true ).validate( CustomValidator.forPredicate( configVM::validatePaths, "The given path is invalid"  ) ),
        Setting.of( bundle.getTranslation(Keys.NotesStorageFolder), configVM.Paths( ).NotesPath( ), configVM.Paths( ).NotesPath( ).get( ), true ).validate( CustomValidator.forPredicate( configVM::validatePaths, "The given path is invalid"  ) ),
        Setting.of( bundle.getTranslation(Keys.ContactsStorageFolder), configVM.Paths( ).ContactsPath( ), configVM.Paths( ).ContactsPath( ).get( ), true ).validate( CustomValidator.forPredicate( configVM::validatePaths, "The given path is invalid"  ) ),
        Setting.of( bundle.getTranslation(Keys.FilesStorageFolder), configVM.Paths( ).FilesPath( ), configVM.Paths( ).FilesPath( ).get( ), true ).validate( CustomValidator.forPredicate( configVM::validatePaths, "The given path is invalid"  ) ),
        Setting.of( bundle.getTranslation(Keys.MusicStorageFolder), configVM.Paths( ).MusicPath( ), configVM.Paths( ).MusicPath( ).get( ), true ).validate( CustomValidator.forPredicate( configVM::validatePaths, "The given path is invalid"  ) ),
        Setting.of( bundle.getTranslation(Keys.ImagesStorageFolder), configVM.Paths( ).ImagesPath( ), configVM.Paths( ).ImagesPath( ).get( ), true ).validate( CustomValidator.forPredicate( configVM::validatePaths, "The given path is invalid"  ) ),
        Setting.of( bundle.getTranslation(Keys.VideosStorageFolder), configVM.Paths( ).VideosPath( ), configVM.Paths( ).VideosPath( ).get( ), true ).validate( CustomValidator.forPredicate( configVM::validatePaths, "The given path is invalid"  ) )
      )
    ).addEventHandler( PreferencesFxEvent.EVENT_PREFERENCES_SAVED, this )
        .addEventHandler( PreferencesFxEvent.EVENT_PREFERENCES_NOT_SAVED, this )
        .dialogTitle( bundle.getTranslation(Keys.PreferencesDialogTitle) );
    //@formatter:on

    }

    /**
     * Shows the preferencesFx dialog as a modal
     */
    public void showDialog( ) {

        this.preferences.show( true );

    }

    /**
     * Returns the preferencesFx object.
     */
    @Contract( pure = true )
    public PreferencesFx Preferences( ) {

        return this.preferences;

    }

    /**
     * Handles an event from preferencesFx.
     *
     * @param event The event to handle.
     */
    @Override
    public void handle( final PreferencesFxEvent event ) {

        if( Objects.requireNonNull( event ).getEventType( ).equals( PreferencesFxEvent.EVENT_PREFERENCES_SAVED ) ) {

            this.handleSave( );

        } else if( Objects.requireNonNull( event ).getEventType( ).equals( PreferencesFxEvent.EVENT_PREFERENCES_NOT_SAVED ) ) {

            this.handleCancel( );

        }

    }

    private void handleCancel( ) {

        this.configVM.reset( );

    }

    private void handleSave( ) {

        Exceptions.log( PreferencesView.LOGGER, "Unable to save the configuration" ).passing( ).run( Exceptions.wrap( ).runnable( this.configVM::save ) ); //$NON-NLS-1$

    }

}
