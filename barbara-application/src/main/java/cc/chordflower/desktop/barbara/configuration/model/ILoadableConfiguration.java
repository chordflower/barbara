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

import org.spongepowered.configurate.ScopedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.Serializable;

/**
 * Describes methods to load and save the current configuration values.
 *
 * @author carddamom
 */
public interface ILoadableConfiguration extends Serializable {

  /**
   * Loads the configuration from the given node.
   *
   * @param node The node to load the configuration from.
   */
  void loadFrom( ScopedConfigurationNode< ? > node );

  /**
   * Saves the current configuration to the given node.
   *
   * @param node The node to save the configuration to.
   */
  void saveTo( ScopedConfigurationNode< ? > node ) throws SerializationException;

}
