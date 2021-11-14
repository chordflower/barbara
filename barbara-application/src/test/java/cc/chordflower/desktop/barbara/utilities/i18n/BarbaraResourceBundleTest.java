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
package cc.chordflower.desktop.barbara.utilities.i18n;

import cc.chordflower.desktop.barbara.utilities.i18n.BarbaraResourceBundle.Keys;
import com.mscharhag.oleaster.matcher.Matchers;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * These are the unit tests for the ResourceBundle manager.
 *
 * @author carddamom
 */
@DisplayName( "ResourceBundle:" )
public class BarbaraResourceBundleTest {

  private static final Logger LOGGER = LoggerFactory.getLogger( BarbaraResourceBundleTest.class );

  private static Locale defaultLocale;

  @BeforeAll
  public static void initAll( ) {

    defaultLocale = Locale.getDefault( );
    Locale.setDefault( Locale.forLanguageTag( "en-AU" ) );

  }

  @AfterAll
  public static void tealAll( ) {

    Locale.setDefault( defaultLocale );

  }

  @Nested
  @DisplayName( "With the default locale" )
  public class DefaultBundle {

    @Test
    @DisplayName( "we should be able to retrieve a valid message" )
    public void testOne( ) {

      BarbaraResourceBundle resourceBundle = new BarbaraResourceBundle( );
      var res = resourceBundle.getTranslation( Keys.Paths );
      Matchers.expect( res ).toEqual( "Paths" );
    }

    @Test
    @DisplayName( "we should not be able to retrieve an invalid message" )
    public void testTwo( ) {

      BarbaraResourceBundle resourceBundle = new BarbaraResourceBundle( );
      var res = resourceBundle.getTranslation( Keys.Unknown );
      Matchers.expect( res ).toEqual( "Unknown" );
    }

  }

  @Nested
  @DisplayName( "With a specific locale" )
  public class SpecificBundle {

    private static final Locale locale = Locale.forLanguageTag( "pt-PT" );

    @Test
    @DisplayName( "we should be able to retrieve a valid message" )
    public void testOne( ) {

      BarbaraResourceBundle resourceBundle = new BarbaraResourceBundle( );
      var res = resourceBundle.getTranslation( Keys.Paths, locale );
      Matchers.expect( res ).toEqual( "Caminhos" );
    }

    @Test
    @DisplayName( "we should not be able to retrieve an invalid message" )
    public void testTwo( ) {

      BarbaraResourceBundle resourceBundle = new BarbaraResourceBundle( );
      var res = resourceBundle.getTranslation( Keys.Unknown, locale );
      Matchers.expect( res ).toEqual( "Unknown" );
    }

  }

}
