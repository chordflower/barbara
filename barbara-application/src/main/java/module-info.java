module barbara.application {
  requires org.apache.commons.lang3;
  requires org.slf4j;
  requires org.spongepowered.configurate;
  requires jakarta.validation;
  requires javax.inject;
  requires com.dlsc.preferencesfx;
  requires noexception;
  requires gson;
  requires dagger;
  requires org.jetbrains.annotations;
  requires org.spongepowered.configurate.gson;
  requires org.spongepowered.configurate.xml;
  requires org.spongepowered.configurate.yaml;
  requires semver4j;
  requires org.controlsfx.controls;

  exports cc.chordflower.desktop.barbara;
  exports cc.chordflower.desktop.barbara.exceptions;
  exports cc.chordflower.desktop.barbara.configuration.model;
  exports cc.chordflower.desktop.barbara.configuration.view;
  exports cc.chordflower.desktop.barbara.configuration.view.model;
  exports cc.chordflower.desktop.barbara.utilities.i18n;
  exports cc.chordflower.desktop.barbara.utilities.layers;
  exports cc.chordflower.desktop.barbara.utilities.validator;
}
