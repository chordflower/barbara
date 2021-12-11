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
package cc.chordflower.desktop.barbara.initial.view;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.event.EventListenerSupport;
import org.apache.commons.lang3.tuple.Pair;
import org.controlsfx.control.StatusBar;
import org.controlsfx.control.TaskProgressView;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * This is the main window for barbara.
 *
 * @author carddamom
 */
public class BarbaraMainWindow extends VBox {

  private static final Logger LOGGER = LoggerFactory.getLogger( BarbaraMainWindow.class );

  private Tab exampleCenterTab;

  private TabPane centerTabPane;

  public enum BarbaraMainWindowEvents {
    ENTER_EXIT_FULL_SCREEN( "EnterExitFullScreen" ),
    HIDE_APP( "HideApp" ),
    HIDE_OTHERS( "HideOthers" ),
    QUIT( "Quit" ),
    SERVICES( "Services" ),
    SHOW_ALL( "ShowAll" ),
    UNKNOWN( "Unknown" );

    private final String eventName;

    @Contract( pure = true )
    BarbaraMainWindowEvents( String eventName ) {

      this.eventName = eventName;
    }

    @Contract( pure = true )
    public String EventName( ) {

      return this.eventName;
    }

    public static BarbaraMainWindowEvents fromString( String event ) {

      for( BarbaraMainWindowEvents item : BarbaraMainWindowEvents.values( ) ) {
        if( Objects.equals( item.eventName, event ) ) {
          return item;
        }
      }
      return BarbaraMainWindowEvents.UNKNOWN;
    }
  }

  private EventListenerSupport< BarbaraEventHandler > actionListeners = EventListenerSupport.create( BarbaraEventHandler.class );

  private MenuItem findMenuOption;

  private MenuItem showHideToolbarMenuEntry;

  private MenuItem aboutBarbaraMenuEntry;

  private MenuItem barbaraHelpMenuEntry;

  private Menu barbaraMenu;

  private TabPane bottomTabPane;

  private ScrollPane centerScrollPane;

  private Pane centralPane;

  private MenuItem closeMenuEntry;

  private MenuItem closeTabMenuEntry;

  private MenuItem copyMenuEntry;

  private MenuItem customizeToolbarMenuEntry;

  private MenuItem cutMenuEntry;

  private MenuItem deleteMenuEntry;

  private Menu editMenu;

  private MenuItem enterExitFullScreenMenuEntry;

  private Tab exampleTab;

  private MenuItem exportAsMenuEntry;

  private Menu fileMenu;

  private Menu findMenuEntry;

  private MenuItem findNextMenuEntry;

  private MenuItem findPreviousMenuEntry;

  private MenuItem findReplaceMenuEntry;

  private SplitPane firstSplitPane;

  private Menu helpMenu;

  private MenuItem hideBarbaraMenuEntry;

  private MenuItem hideOthersMenuEntry;

  private VBox mainPanel;

  private MenuBar menuBar;

  private MenuItem newMenuEntry;

  private MenuItem pageSetupMenuEntry;

  private Pane paneExample;

  private MenuItem pasteMenuEntry;

  private MenuItem preferenceMenuItem;

  private MenuItem printMenuEntry;

  private ScrollPane progressScrollPane;

  private Tab progressTab;

  private TaskProgressView< Task< ? > > progressTaskProgressView;

  private TableColumn< Pair< String, String >, String > propertiesNameColumn;

  private ScrollPane propertiesScrollPane;

  private Tab propertiesTab;

  private TableView< Pair< String, String > > propertiesTableView;

  private TableColumn< Pair< String, String >, String > propertiesValueColumn;

  private MenuItem quitBarbaraMenuEntry;

  private MenuItem redoMenuEntry;

  private ScrollPane scrollPaneExample;

  private SplitPane secondSplitPane;

  private MenuItem selectAllMenuEntry;

  private SeparatorMenuItem separator1;

  private SeparatorMenuItem separator10;

  private SeparatorMenuItem separator11;

  private SeparatorMenuItem separator2;

  private SeparatorMenuItem separator3;

  private SeparatorMenuItem separator4;

  private SeparatorMenuItem separator5;

  private SeparatorMenuItem separator6;

  private SeparatorMenuItem separator7;

  private SeparatorMenuItem separator8;

  private SeparatorMenuItem separator9;

  private MenuItem servicesMenuItem;

  private MenuItem showAllMenuEntry;

  private StatusBar statusBar;

  private TabPane sidebarTabPane;

  private ToolBar toolbar;

  private MenuItem undoMenuEntry;

  private Menu viewMenu;

  public BarbaraMainWindow( ) {

    this.setPrefWidth( 1280 );
    this.setPrefHeight( 1024 );
    this.menuBar = this.createMenuBar( );
    this.toolbar = this.createToolbar( );
    this.firstSplitPane = this.createFirstSplitPane( );
    this.statusBar = this.createStatusBar( );

    VBox.setVgrow( this.firstSplitPane, Priority.ALWAYS );

    this.getChildren( ).addAll( this.menuBar, this.toolbar,
        this.firstSplitPane, this.statusBar );
  }

  private @NotNull StatusBar createStatusBar( ) {

    var statusBar = new StatusBar( );
    statusBar.setText( "Barbara" );

    return statusBar;
  }

  private @NotNull SplitPane createFirstSplitPane( ) {

    var splitPane = new SplitPane( );
    splitPane.setDividerPositions( 0.27984344422700586 );
    splitPane.setPrefWidth( 200 );
    splitPane.setPrefHeight( 160 );

    this.sidebarTabPane = this.createSidebar( );
    this.secondSplitPane = this.createSecondSplitPane( );

    splitPane.getItems( ).addAll( this.sidebarTabPane, this.secondSplitPane );

    return splitPane;
  }

  private @NotNull SplitPane createSecondSplitPane( ) {

    var splitPane = new SplitPane( );
    splitPane.setDividerPositions( 0.6272455089820359 );
    splitPane.setPrefWidth( 200 );
    splitPane.setPrefHeight( 160 );
    splitPane.setOrientation( Orientation.VERTICAL );

    this.centerTabPane = this.createCenterTabPane( );
    this.bottomTabPane = this.createBottomTabPane( );

    splitPane.getItems( ).addAll( this.centerTabPane, this.bottomTabPane );

    return splitPane;
  }

  private @NotNull TabPane createBottomTabPane( ) {

    var tabPane = new TabPane( );
    tabPane.setId( "bottomTabPane" );
    tabPane.setSide( Side.BOTTOM );
    tabPane.setTabClosingPolicy( TabClosingPolicy.UNAVAILABLE );

    this.progressTab = this.createProgressTab( );
    this.propertiesTab = this.createPropertiesTab( );

    tabPane.getTabs( ).addAll( this.progressTab, this.propertiesTab );

    return tabPane;
  }

  private @NotNull Tab createPropertiesTab( ) {

    var tab = new Tab( "Properties" );
    tab.setId( "propertiesTab" );
    tab.setClosable( false );

    this.propertiesScrollPane = new ScrollPane( );
    this.propertiesScrollPane.setVbarPolicy( ScrollBarPolicy.ALWAYS );
    this.propertiesScrollPane.setId( "propertiesScrollPane" );
    this.propertiesScrollPane.setFitToWidth( true );
    this.propertiesScrollPane.setFitToHeight( true );

    this.propertiesTableView = new TableView<>( );
    this.propertiesTableView.setId( "propertiesTableView" );
    this.propertiesTableView.setOnSort( this::onPropertiesTableSort );
    this.propertiesTableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );

    this.propertiesNameColumn = new TableColumn<>( "Name" );
    this.propertiesNameColumn.setId( "propertiesNameColumn" );
    this.propertiesNameColumn.setEditable( false );
    this.propertiesNameColumn.setPrefWidth( 150 );

    this.propertiesValueColumn = new TableColumn<>( "Value" );
    this.propertiesValueColumn.setId( "propertiesValueColumn" );
    this.propertiesValueColumn.setEditable( true );
    this.propertiesValueColumn.setOnEditCancel( this::onPropertiesValueColumnEditCancel );
    this.propertiesValueColumn.setOnEditStart( this::onPropertiesValueColumnEditStart );
    this.propertiesValueColumn.setOnEditCommit( this::onPropertiesValueColumnEditCommit );

    this.propertiesTableView.getColumns( ).addAll( this.propertiesNameColumn, this.propertiesValueColumn );
    this.propertiesScrollPane.setContent( this.propertiesTableView );
    tab.setContent( this.propertiesScrollPane );

    return tab;
  }

  private @NotNull Tab createProgressTab( ) {

    var tab = new Tab( "Progress" );
    tab.setId( "progressTab" );
    tab.setClosable( false );

    this.progressScrollPane = new ScrollPane( );
    this.progressScrollPane.setId( "progressScrollPane" );
    this.progressScrollPane.setFitToHeight( true );
    this.progressScrollPane.setFitToWidth( true );
    this.progressScrollPane.setVbarPolicy( ScrollBarPolicy.ALWAYS );

    this.progressTaskProgressView = new TaskProgressView<>( );
    this.progressTaskProgressView.setId( "progressTaskProgressView" );

    this.progressScrollPane.setContent( this.progressTaskProgressView );
    tab.setContent( this.progressScrollPane );

    return tab;
  }

  private @NotNull TabPane createCenterTabPane( ) {

    var tabPane = new TabPane( );
    tabPane.setId( "centerTabPane" );
    tabPane.setTabClosingPolicy( TabClosingPolicy.ALL_TABS );
    tabPane.setSide( Side.TOP );

    this.exampleCenterTab = new Tab( "Example 2" );

    this.centerScrollPane = new ScrollPane( );
    this.centerScrollPane.setFitToWidth( true );
    this.centerScrollPane.setFitToHeight( true );

    this.centralPane = new Pane( );
    this.centralPane.setStyle( "-fx-background-color: #1e90ff;" );

    this.centerScrollPane.setContent( this.centralPane );
    this.exampleCenterTab.setContent( this.centerScrollPane );
    tabPane.getTabs( ).addAll( this.exampleCenterTab );

    return tabPane;
  }

  private @NotNull TabPane createSidebar( ) {

    var tabPane = new TabPane( );
    tabPane.setId( "sidebarTabPane" );
    tabPane.setSide( Side.BOTTOM );
    tabPane.setTabClosingPolicy( TabClosingPolicy.UNAVAILABLE );

    this.exampleTab = new Tab( "Example" );
    this.exampleTab.setClosable( false );

    this.scrollPaneExample = new ScrollPane( );
    this.scrollPaneExample.setId( "scrollPaneExample" );
    this.scrollPaneExample.setFitToHeight( true );
    this.scrollPaneExample.setFitToWidth( true );

    this.paneExample = new Pane( );
    this.paneExample.setStyle( "-fx-background-color: #ff6347;" );

    this.scrollPaneExample.setContent( this.paneExample );
    this.exampleTab.setContent( this.scrollPaneExample );

    tabPane.getTabs( ).addAll( this.exampleTab );

    return tabPane;
  }

  private @NotNull ToolBar createToolbar( ) {

    var toolbar = new ToolBar( );
    toolbar.setPrefHeight( 40 );
    toolbar.setPrefWidth( 200 );

    return toolbar;
  }

  private @NotNull MenuBar createMenuBar( ) {

    var menuBar = new MenuBar( );
    this.barbaraMenu = this.createBarbaraMenu( );
    this.fileMenu = this.createFileMenu( );
    this.editMenu = this.createEditMenu( );
    this.viewMenu = this.createViewMenu( );
    this.helpMenu = this.createHelpMenu( );
    menuBar.getMenus( ).addAll( this.barbaraMenu, this.fileMenu,
        this.editMenu, this.viewMenu, this.helpMenu );

    return menuBar;
  }

  private @NotNull Menu createHelpMenu( ) {

    var menu = new Menu( "Help" );
    menu.setMnemonicParsing( false );

    this.barbaraHelpMenuEntry = BarbaraMainWindow.createMenuItem( "barbaraHelpMenuEntry", "Barbara Help", null, this::onHelpAction );

    menu.getItems( ).addAll( this.barbaraHelpMenuEntry );

    return menu;
  }

  private @NotNull Menu createViewMenu( ) {

    var menu = new Menu( "View" );
    menu.setMnemonicParsing( false );

    this.showHideToolbarMenuEntry = BarbaraMainWindow.createMenuItem( "showHideToolbarMenuEntry", "Show/Hide Toolbar", null, this::onShowHideToolbarAction );
    this.separator10 = BarbaraMainWindow.createSeparator( "separator10" );
    this.customizeToolbarMenuEntry = BarbaraMainWindow.createMenuItem( "customizeToolbarMenuEntry", "Customize Toolbar...", null, this::onCustomizeToolbarAction );
    this.separator11 = BarbaraMainWindow.createSeparator( "separator11" );
    this.enterExitFullScreenMenuEntry = BarbaraMainWindow.createMenuItem( "enterExitFullScreenMenuEntry", "Enter/Exit Full Screen", null, this::onEnterExitFullScreenAction );

    menu.getItems( ).addAll( this.showHideToolbarMenuEntry, this.separator10, this.customizeToolbarMenuEntry, this.separator11, this.enterExitFullScreenMenuEntry );

    return menu;
  }

  private @NotNull Menu createEditMenu( ) {

    var menu = new Menu( "Edit" );
    menu.setMnemonicParsing( false );

    this.undoMenuEntry = BarbaraMainWindow.createMenuItem( "undoMenuEntry", "Undo", null, this::onUndoAction );
    this.undoMenuEntry.setAccelerator( new KeyCodeCombination( KeyCode.Z, KeyCombination.SHORTCUT_DOWN ) );

    this.redoMenuEntry = BarbaraMainWindow.createMenuItem( "redoMenuEntry", "Redo", null, this::onRedoAction );
    this.redoMenuEntry.setAccelerator( new KeyCodeCombination( KeyCode.Z, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN ) );

    this.separator8 = BarbaraMainWindow.createSeparator( "separator8" );

    this.cutMenuEntry = BarbaraMainWindow.createMenuItem( "cutMenuEntry", "Cut", null, this::onCutAction );
    this.cutMenuEntry.setAccelerator( new KeyCodeCombination( KeyCode.X, KeyCombination.SHORTCUT_DOWN ) );

    this.copyMenuEntry = BarbaraMainWindow.createMenuItem( "copyMenuEntry", "Copy", null, this::onCopyAction );
    this.copyMenuEntry.setAccelerator( new KeyCodeCombination( KeyCode.C, KeyCombination.SHORTCUT_DOWN ) );

    this.pasteMenuEntry = BarbaraMainWindow.createMenuItem( "pasteMenuEntry", "Paste", null, this::onPasteAction );
    this.pasteMenuEntry.setAccelerator( new KeyCodeCombination( KeyCode.V, KeyCombination.SHORTCUT_DOWN ) );

    this.deleteMenuEntry = BarbaraMainWindow.createMenuItem( "deleteMenuEntry", "Delete", null, this::onDeleteAction );
    this.deleteMenuEntry.setAccelerator( new KeyCodeCombination( KeyCode.DELETE, KeyCombination.SHORTCUT_DOWN ) );

    this.selectAllMenuEntry = BarbaraMainWindow.createMenuItem( "selectAllMenuEntry", "Select All", null, this::onSelectAllAction );
    this.selectAllMenuEntry.setAccelerator( new KeyCodeCombination( KeyCode.A, KeyCombination.SHORTCUT_DOWN ) );

    this.separator9 = BarbaraMainWindow.createSeparator( "separator9" );

    this.findMenuEntry = this.createFindMenu( );

    menu.getItems( ).addAll( this.undoMenuEntry, this.redoMenuEntry, this.separator8, this.cutMenuEntry, this.copyMenuEntry,
        this.pasteMenuEntry, this.deleteMenuEntry, this.selectAllMenuEntry, this.separator9, this.findMenuEntry );

    return menu;
  }

  private @NotNull Menu createFindMenu( ) {

    var menu = new Menu( "Find" );
    menu.setMnemonicParsing( false );

    this.findMenuOption = BarbaraMainWindow.createMenuItem( "findMenuOption", "Find", null, this::onFindAction );
    this.findMenuOption.setAccelerator( new KeyCodeCombination( KeyCode.F, KeyCombination.SHORTCUT_DOWN ) );

    this.findReplaceMenuEntry = BarbaraMainWindow.createMenuItem( "findReplaceMenuEntry", "Find and Replace…", null, this::onFindAndReplaceAction );
    this.findReplaceMenuEntry.setAccelerator( new KeyCodeCombination( KeyCode.F, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN ) );

    this.findNextMenuEntry = BarbaraMainWindow.createMenuItem( "findNextMenuEntry", "Find Next", null, this::onFindNextAction );
    this.findNextMenuEntry.setAccelerator( new KeyCodeCombination( KeyCode.G, KeyCombination.SHORTCUT_DOWN ) );

    this.findPreviousMenuEntry = BarbaraMainWindow.createMenuItem( "findPreviousMenuEntry", "Find Previous", null, this::onFindPreviousAction );
    this.findPreviousMenuEntry.setAccelerator( new KeyCodeCombination( KeyCode.G, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN ) );

    menu.getItems( ).addAll( this.findMenuOption, this.findReplaceMenuEntry, this.findNextMenuEntry, this.findPreviousMenuEntry );

    return menu;
  }

  private @NotNull Menu createFileMenu( ) {

    var menu = new Menu( "File" );
    menu.setMnemonicParsing( false );

    this.newMenuEntry = BarbaraMainWindow.createMenuItem( "newMenuEntry", "New...", null, this::onNewAction );
    this.newMenuEntry.setAccelerator( new KeyCodeCombination( KeyCode.N, KeyCombination.SHORTCUT_DOWN ) );

    this.separator5 = BarbaraMainWindow.createSeparator( "separator5" );

    this.closeMenuEntry = BarbaraMainWindow.createMenuItem( "closeMenuEntry", "Close", null, this::onCloseAction );
    this.closeMenuEntry.setAccelerator( new KeyCodeCombination( KeyCode.W, KeyCombination.SHORTCUT_DOWN ) );

    this.closeTabMenuEntry = BarbaraMainWindow.createMenuItem( "closeTabMenuEntry", "Close Tab", null, this::onCloseTabAction );
    this.closeTabMenuEntry.setAccelerator( new KeyCodeCombination( KeyCode.W, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN ) );

    this.separator6 = BarbaraMainWindow.createSeparator( "separator6" );
    this.exportAsMenuEntry = BarbaraMainWindow.createMenuItem( "exportAsMenuEntry", "Export As...", null, this::onExportAsAction );
    this.separator7 = BarbaraMainWindow.createSeparator( "separator7" );

    this.pageSetupMenuEntry = BarbaraMainWindow.createMenuItem( "pageSetupMenuEntry", "Page Setup...", null, this::onPageSetupAction );
    this.pageSetupMenuEntry.setAccelerator( new KeyCodeCombination( KeyCode.P, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN ) );

    this.printMenuEntry = BarbaraMainWindow.createMenuItem( "printMenuEntry", "Print...", null, this::onPrintAction );
    this.printMenuEntry.setAccelerator( new KeyCodeCombination( KeyCode.P, KeyCombination.SHORTCUT_DOWN ) );

    menu.getItems( ).addAll( this.newMenuEntry, this.separator5, this.closeMenuEntry, this.closeTabMenuEntry, this.separator6, this.exportAsMenuEntry,
        this.separator7, this.pageSetupMenuEntry, this.printMenuEntry );

    return menu;
  }

  private @NotNull Menu createBarbaraMenu( ) {

    var menu = new Menu( "Barbara" );
    menu.setMnemonicParsing( false );
    menu.setStyle( "-fx-font-weight: bolder;" );

    this.aboutBarbaraMenuEntry = BarbaraMainWindow.createMenuItem( "aboutBarbaraMenuEntry", "About Barbara", "-fx-font-weight: normal;", this::onAboutAction );
    this.separator1 = BarbaraMainWindow.createSeparator( "separator1" );

    this.preferenceMenuItem = BarbaraMainWindow.createMenuItem( "preferenceMenuItem", "Preferences...", "-fx-font-weight: normal;", this::onPreferencesAction );
    this.preferenceMenuItem.setAccelerator( new KeyCodeCombination( KeyCode.COMMA, KeyCombination.SHORTCUT_DOWN ) );

    this.separator2 = BarbaraMainWindow.createSeparator( "separator2" );
    this.servicesMenuItem = BarbaraMainWindow.createMenuItem( "servicesMenuItem", "Services", "-fx-font-weight: normal;", this::onServicesAction );
    this.separator3 = BarbaraMainWindow.createSeparator( "separator3" );

    this.hideBarbaraMenuEntry = BarbaraMainWindow.createMenuItem( "hideBarbaraMenuEntry", "Hide Barbara", "-fx-font-weight: normal;", this::onHideAppAction );
    this.hideBarbaraMenuEntry.setAccelerator( new KeyCodeCombination( KeyCode.H, KeyCombination.SHORTCUT_DOWN ) );

    this.hideOthersMenuEntry = BarbaraMainWindow.createMenuItem( "hideOthersMenuEntry", "Hide Others", "-fx-font-weight: normal;", this::onHideOthersAction );
    this.hideOthersMenuEntry.setAccelerator( new KeyCodeCombination( KeyCode.H, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN ) );

    this.showAllMenuEntry = BarbaraMainWindow.createMenuItem( "showAllMenuEntry", "Show All", "-fx-font-weight: normal;", this::onShowAllAction );
    this.separator4 = BarbaraMainWindow.createSeparator( "separator4" );

    this.quitBarbaraMenuEntry = BarbaraMainWindow.createMenuItem( "quitBarbaraMenuEntry", "Quit Barbara", "-fx-font-weight: normal;", this::onQuitAppAction );
    this.quitBarbaraMenuEntry.setAccelerator( new KeyCodeCombination( KeyCode.Q, KeyCombination.SHORTCUT_DOWN ) );

    menu.getItems( ).addAll( this.aboutBarbaraMenuEntry, this.separator1, this.preferenceMenuItem, this.separator2, this.servicesMenuItem,
        this.separator3, this.hideBarbaraMenuEntry, this.hideOthersMenuEntry, this.showAllMenuEntry, this.separator4, this.quitBarbaraMenuEntry );

    return menu;
  }

  private static @NotNull MenuItem createMenuItem( String id, String text, String style, EventHandler< ActionEvent > action ) {

    var menuItem = new MenuItem( text );

    if( id != null && !id.isEmpty( ) ) {
      menuItem.setId( id );
    }

    if( style != null && !style.isEmpty( ) ) {
      menuItem.setStyle( style );
    }

    if( action != null ) {
      menuItem.setOnAction( action );
    }

    menuItem.setMnemonicParsing( false );

    return menuItem;
  }

  private static @NotNull SeparatorMenuItem createSeparator( String id ) {

    var separator = new SeparatorMenuItem( );

    if( id != null && !id.isEmpty( ) ) {
      separator.setId( id );
    }

    separator.setMnemonicParsing( false );

    return separator;
  }

  private void onAboutAction( ActionEvent event ) {

    LOGGER.info( "Calling about action" );
  }

  private void onCloseAction( ActionEvent event ) {

    LOGGER.info( "Calling close action" );
  }

  private void onCloseTabAction( ActionEvent event ) {

    LOGGER.info( "Calling close tab action" );
  }

  private void onCopyAction( ActionEvent event ) {

    LOGGER.info( "Calling copy action" );
  }

  private void onCustomizeToolbarAction( ActionEvent event ) {

    LOGGER.info( "Calling customize toolbar action" );
  }

  private void onCutAction( ActionEvent event ) {

    LOGGER.info( "Calling cut action" );
  }

  private void onDeleteAction( ActionEvent event ) {

    LOGGER.info( "Calling delete action" );
  }

  private void onEnterExitFullScreenAction( ActionEvent event ) {

    LOGGER.info( "Calling enter/exit full screen action" );
    Objects.requireNonNull( event );
    this.actionListeners.fire( ).handle( new Event( event.getSource( ), event.getTarget( ), new EventType<>( BarbaraMainWindowEvents.ENTER_EXIT_FULL_SCREEN.EventName( ) ) ) );
  }

  private void onErrorsTableSort( ActionEvent event ) {

    LOGGER.info( "Calling errors table sort action" );
  }

  private void onExportAsAction( ActionEvent event ) {

    LOGGER.info( "Calling export as action" );
  }

  private void onFindAction( ActionEvent event ) {

    LOGGER.info( "Calling find action" );
  }

  private void onFindAndReplaceAction( ActionEvent event ) {

    LOGGER.info( "Calling find and replace action" );
  }

  private void onFindNextAction( ActionEvent event ) {

    LOGGER.info( "Calling find next action" );
  }

  private void onFindPreviousAction( ActionEvent event ) {

    LOGGER.info( "Calling find previous action" );
  }

  private void onHelpAction( ActionEvent event ) {

    LOGGER.info( "Calling help action" );
  }

  private void onHideAppAction( ActionEvent event ) {

    LOGGER.info( "Calling hide app action" );
    Objects.requireNonNull( event );
    this.actionListeners.fire( ).handle( new Event( event.getSource( ), event.getTarget( ), new EventType<>( BarbaraMainWindowEvents.HIDE_APP.EventName( ) ) ) );
  }

  private void onHideOthersAction( ActionEvent event ) {

    LOGGER.info( "Calling hide others action" );
    Objects.requireNonNull( event );
    this.actionListeners.fire( ).handle( new Event( event.getSource( ), event.getTarget( ), new EventType<>( BarbaraMainWindowEvents.HIDE_OTHERS.EventName( ) ) ) );
  }

  private void onNewAction( ActionEvent event ) {

    LOGGER.info( "Calling new action" );
  }

  private void onPageSetupAction( ActionEvent event ) {

    LOGGER.info( "Calling page setup action" );
  }

  private void onPasteAction( ActionEvent event ) {

    LOGGER.info( "Calling paste action" );
  }

  private void onPreferencesAction( ActionEvent event ) {

    LOGGER.info( "Calling preferences action" );
  }

  private void onPrintAction( ActionEvent event ) {

    LOGGER.info( "Calling print action" );
  }

  private void onPropertiesValueColumnEditCancel( CellEditEvent< Pair< String, String >, String > event ) {

    LOGGER.info( "Calling value column edit cancel action" );
  }

  private void onPropertiesValueColumnEditCommit( CellEditEvent< Pair< String, String >, String > event ) {

    LOGGER.info( "Calling properties value column edit commit action" );
  }

  private void onPropertiesValueColumnEditStart( CellEditEvent< Pair< String, String >, String > event ) {

    LOGGER.info( "Calling properties value column edit start action" );
  }

  private void onPropertiesTableSort( SortEvent< TableView< Pair< String, String > > > tableViewSortEvent ) {

    LOGGER.debug( "I'm sorting the properties table..." );
  }

  private void onQuitAppAction( ActionEvent event ) {

    LOGGER.info( "Calling quit app action" );
    Objects.requireNonNull( event );
    this.actionListeners.fire( ).handle( new Event( event.getSource( ), event.getTarget( ), new EventType<>( BarbaraMainWindowEvents.QUIT.EventName( ) ) ) );
  }

  private void onRedoAction( ActionEvent event ) {

    LOGGER.info( "Calling redo action" );
  }

  private void onSelectAllAction( ActionEvent event ) {

    LOGGER.info( "Calling select all action" );
  }

  private void onServicesAction( ActionEvent event ) {

    LOGGER.info( "Calling services action" );
    Objects.requireNonNull( event );
    this.actionListeners.fire( ).handle( new Event( event.getSource( ), event.getTarget( ), new EventType<>( BarbaraMainWindowEvents.SERVICES.EventName( ) ) ) );
  }

  private void onShowAllAction( ActionEvent event ) {

    LOGGER.info( "Calling show all action" );
    Objects.requireNonNull( event );
    this.actionListeners.fire( ).handle( new Event( event.getSource( ), event.getTarget( ), new EventType<>( BarbaraMainWindowEvents.SHOW_ALL.EventName( ) ) ) );
  }

  private void onShowHideToolbarAction( ActionEvent event ) {

    LOGGER.info( "Calling show hide toolbar action" );
  }

  private void onUndoAction( ActionEvent event ) {

    LOGGER.info( "Calling undo action" );
  }

  public MenuItem FindMenuOption( ) {

    return this.findMenuOption;
  }

  public MenuItem ShowHideToolbarMenuEntry( ) {

    return this.showHideToolbarMenuEntry;
  }

  public MenuItem AboutBarbaraMenuEntry( ) {

    return this.aboutBarbaraMenuEntry;
  }

  public MenuItem BarbaraHelpMenuEntry( ) {

    return this.barbaraHelpMenuEntry;
  }

  public Menu BarbaraMenu( ) {

    return this.barbaraMenu;
  }

  public TabPane BottomTabPane( ) {

    return this.bottomTabPane;
  }

  public ScrollPane CenterScrollPane( ) {

    return this.centerScrollPane;
  }

  public Pane CentralPane( ) {

    return this.centralPane;
  }

  public MenuItem CloseMenuEntry( ) {

    return this.closeMenuEntry;
  }

  public MenuItem CloseTabMenuEntry( ) {

    return this.closeTabMenuEntry;
  }

  public MenuItem CopyMenuEntry( ) {

    return this.copyMenuEntry;
  }

  public MenuItem CustomizeToolbarMenuEntry( ) {

    return this.customizeToolbarMenuEntry;
  }

  public MenuItem CutMenuEntry( ) {

    return this.cutMenuEntry;
  }

  public MenuItem DeleteMenuEntry( ) {

    return this.deleteMenuEntry;
  }

  public Menu EditMenu( ) {

    return this.editMenu;
  }

  public MenuItem EnterExitFullScreenMenuEntry( ) {

    return this.enterExitFullScreenMenuEntry;
  }

  public Tab ExampleTab( ) {

    return this.exampleTab;
  }

  public MenuItem ExportAsMenuEntry( ) {

    return this.exportAsMenuEntry;
  }

  public Menu FileMenu( ) {

    return this.fileMenu;
  }

  public Menu FindMenuEntry( ) {

    return this.findMenuEntry;
  }

  public MenuItem FindNextMenuEntry( ) {

    return this.findNextMenuEntry;
  }

  public MenuItem FindPreviousMenuEntry( ) {

    return this.findPreviousMenuEntry;
  }

  public MenuItem FindReplaceMenuEntry( ) {

    return this.findReplaceMenuEntry;
  }

  public SplitPane FirstSplitPane( ) {

    return this.firstSplitPane;
  }

  public Menu HelpMenu( ) {

    return this.helpMenu;
  }

  public MenuItem HideBarbaraMenuEntry( ) {

    return this.hideBarbaraMenuEntry;
  }

  public MenuItem HideOthersMenuEntry( ) {

    return this.hideOthersMenuEntry;
  }

  public VBox MainPanel( ) {

    return this.mainPanel;
  }

  public MenuBar MenuBar( ) {

    return this.menuBar;
  }

  public MenuItem NewMenuEntry( ) {

    return this.newMenuEntry;
  }

  public MenuItem PageSetupMenuEntry( ) {

    return this.pageSetupMenuEntry;
  }

  public Pane PaneExample( ) {

    return this.paneExample;
  }

  public MenuItem PasteMenuEntry( ) {

    return this.pasteMenuEntry;
  }

  public MenuItem PreferenceMenuItem( ) {

    return this.preferenceMenuItem;
  }

  public MenuItem PrintMenuEntry( ) {

    return this.printMenuEntry;
  }

  public ScrollPane ProgressScrollPane( ) {

    return this.progressScrollPane;
  }

  public Tab ProgressTab( ) {

    return this.progressTab;
  }

  public TaskProgressView< Task< ? > > ProgressTaskProgressView( ) {

    return this.progressTaskProgressView;
  }

  public TableColumn< Pair< String, String >, String > PropertiesNameColumn( ) {

    return this.propertiesNameColumn;
  }

  public ScrollPane PropertiesScrollPane( ) {

    return this.propertiesScrollPane;
  }

  public Tab PropertiesTab( ) {

    return this.propertiesTab;
  }

  public TableView< Pair< String, String > > PropertiesTableView( ) {

    return this.propertiesTableView;
  }

  public TableColumn< Pair< String, String >, String > PropertiesValueColumn( ) {

    return this.propertiesValueColumn;
  }

  public MenuItem QuitBarbaraMenuEntry( ) {

    return this.quitBarbaraMenuEntry;
  }

  public MenuItem RedoMenuEntry( ) {

    return this.redoMenuEntry;
  }

  public ScrollPane ScrollPaneExample( ) {

    return this.scrollPaneExample;
  }

  public SplitPane SecondSplitPane( ) {

    return this.secondSplitPane;
  }

  public MenuItem SelectAllMenuEntry( ) {

    return this.selectAllMenuEntry;
  }

  public SeparatorMenuItem Separator1( ) {

    return this.separator1;
  }

  public SeparatorMenuItem Separator10( ) {

    return this.separator10;
  }

  public SeparatorMenuItem Separator11( ) {

    return this.separator11;
  }

  public SeparatorMenuItem Separator2( ) {

    return this.separator2;
  }

  public SeparatorMenuItem Separator3( ) {

    return this.separator3;
  }

  public SeparatorMenuItem Separator4( ) {

    return this.separator4;
  }

  public SeparatorMenuItem Separator5( ) {

    return this.separator5;
  }

  public SeparatorMenuItem Separator6( ) {

    return this.separator6;
  }

  public SeparatorMenuItem Separator7( ) {

    return this.separator7;
  }

  public SeparatorMenuItem Separator8( ) {

    return this.separator8;
  }

  public SeparatorMenuItem Separator9( ) {

    return this.separator9;
  }

  public MenuItem ServicesMenuItem( ) {

    return this.servicesMenuItem;
  }

  public MenuItem ShowAllMenuEntry( ) {

    return this.showAllMenuEntry;
  }

  public StatusBar StatusBar( ) {

    return this.statusBar;
  }

  public ToolBar Toolbar( ) {

    return this.toolbar;
  }

  public MenuItem UndoMenuEntry( ) {

    return this.undoMenuEntry;
  }

  public Menu ViewMenu( ) {

    return this.viewMenu;
  }

  public Tab ExampleCenterTab( ) {

    return this.exampleCenterTab;
  }

  public TabPane CenterTabPane( ) {

    return this.centerTabPane;
  }

  public TabPane SidebarTabPane( ) {

    return this.sidebarTabPane;
  }

  public void addListener( BarbaraEventHandler listener ) {

    this.actionListeners.addListener( listener );
  }

  public void removeListener( BarbaraEventHandler listener ) {

    this.actionListeners.removeListener( listener );
  }

}
