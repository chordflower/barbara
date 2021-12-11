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
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.concurrent.Computable;
import org.apache.commons.lang3.concurrent.Memoizer;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A description of a dependency of this plugin, this is an immutable class aka a value class.
 *
 * @author carddamom
 */
public final class PluginDependency implements Serializable {

  private static final Logger LOGGER = LoggerFactory.getLogger( PluginDependency.class );

  /**
   * The id of the plugin dependency
   *
   */
  @SerializedName( "$id" )
  @Expose
  private UUID id;

  /**
   * The version of the plugin dependency
   *
   */
  @SerializedName( "version" )
  @Expose
  private String version;

  @Serial
  private final static long serialVersionUID = 2122204548688377365L;

  private static final Memoizer< Pair< UUID, String >, PluginDependency > MEMOIZER = new Memoizer<>( new MemoizableOperation( ), true );

  /**
   * For gson usage...
   */
  @Contract( pure = true )
  private PluginDependency( ) {

    this.id = null;
    this.version = null;

  }

  /**
   * Creates a new immutable instance of plugin dependency.
   *
   * Note: This method uses memoize, so any repeated parameters will return the same immutable instance.
   *
   * @param id The id of the plugin dependency.
   * @param version The version of the plugin dependency.
   * @return A immutable instance of plugin dependency.
   */
  public static PluginDependency of( UUID id, String version ) throws InterruptedException {

    return Exceptions.log( PluginDependency.LOGGER ).passing( ).function( Exceptions.wrap( ).function( PluginDependency.MEMOIZER::compute ) ).apply( new ImmutablePair<>( id, version ) );

  }

  @Contract( pure = true )
  private PluginDependency( UUID id, String version ) {

    this.id = id;
    this.version = version;

  }

  /**
   * Returns the id of the plugin dependency.
   */
  @Contract( pure = true )
  public UUID Id( ) {

    return this.id;

  }

  /**
   * Returns the version of the plugin dependency.
   */
  @Contract( pure = true )
  public String Version( ) {

    return this.version;

  }

  @Override
  public int hashCode( ) {

    return Objects.hash( this.id, this.version );

  }

  @Contract( value = "null -> false", pure = true )
  @Override
  public boolean equals( Object obj ) {

    if( this == obj ) {

      return true;

    }
    if( !( obj instanceof PluginDependency other ) ) {

      return false;

    }
    return Objects.equals( this.id, other.id ) && Objects.equals( this.version, other.version );

  }

  @Override
  public String toString( ) {

    return new ToStringBuilder( this, ToStringStyle.JSON_STYLE ).append( "id", this.id ).append( "version", this.version ).build( );

  }

  private static final class MemoizableOperation implements Computable< Pair< UUID, String >, PluginDependency > {

    @Contract( "_ -> new" )
    @Override
    public @NotNull PluginDependency compute( @NotNull Pair< UUID, String > val ) {

      return new PluginDependency( val.getKey( ), val.getRight( ) );

    }

  }

}
