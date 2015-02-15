package edu.ycp.cs.dh.acegwt.client.ace;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.*;

/**
 * Wrapper for UiBinder widget.
 */
public class AceEditorWidget extends Composite implements RequiresResize, HasText, TakesValue<String> {

	private final AceEditor editor;

	private String themeName; 
	
	private String mode;

	private boolean useWorker = false;
	
	private int fontSize = -1;

	private AceEditorCallback onChangeHandler;

	private AceEditorCallback onChangeCursorHandler;

	private String text;

	private boolean useSoftTabs = false;

	private int tabSize = -1;

	private boolean hScrollBarAlwaysVisible = false;

	private boolean showGutter = true;

	private boolean readOnly = false;

	private boolean highlightSelectedWord = true;

	private boolean enableBasicAutocompletion = false;

	private boolean useWrapMode = true;

	
	public AceEditorWidget() {
		editor = new AceEditor();
		initWidget( editor );
	}

	@Override
	public void setValue(String value) {
		this.setText( value );
	}

	@Override
	public String getValue() {
		return this.getText();
	}

	@Override
	public void onResize() {
		if ( isAttached() ) {
			editor.onResize();
		}
	}

	@Override
	protected void onLoad() {
		editor.startEditor();
		
		applyOptions();
		
		registerHandlers();
	}

	protected void onUnload() {
		editor.destroy();
	}

	private void applyOptions() {
		if ( text != null ) {
			editor.setText( text );
		}
		if ( themeName != null ) {
			editor.setThemeByName( themeName );
		}
		if ( mode != null ) {
			editor.setModeByName( mode );
		}
		if ( fontSize > 0 ) {
			editor.setFontSize( fontSize );
		}
		if ( tabSize > 0 ) {
			editor.setTabSize( tabSize );
		}

		editor.setUseWorker( useWorker );
		editor.setUseSoftTabs( useSoftTabs );

		//todo add another options
	}

	private void registerHandlers() {
		if ( onChangeHandler != null ) {
			editor.addOnChangeHandler( onChangeHandler );
		}
		if ( onChangeCursorHandler != null ) {
			editor.addOnCursorPositionChangeHandler( onChangeCursorHandler );
		}
	}

	public void redisplay( boolean focused ) {
		if ( !isAttached() ) {
			throw new IllegalStateException(
					"Widget should be attached to the browser's document before call redisplay");
		}

		editor.redisplay( focused );
	}

	/**
	 * Set the theme.
	 *
	 * @param theme the theme (one of the values in the {@link edu.ycp.cs.dh.acegwt.client.ace.AceEditorTheme}
	 *              enumeration)
	 */
	public void setTheme(final AceEditorTheme theme) {
		setThemeByName( theme.getName() );
	}

	/**
	 * Set the theme by name.
	 *
	 * @param themeName the theme name (e.g., "twilight")
	 */
	public void setThemeByName(String themeName) {
		if ( !isAttached() ) {
			this.themeName = themeName;
		} else {
			editor.setModeByName( themeName );
		}
    }
	
	/**
	 * Set the mode.
	 *
	 * @param mode the mode (one of the values in the
	 *             {@link edu.ycp.cs.dh.acegwt.client.ace.AceEditorMode} enumeration)
	 */
	public void setMode(final AceEditorMode mode) {
		setModeByName( mode.getName() );
	}

	/**
	 * Set the mode by name.
	 *
	 * @param shortModeName name of mode (e.g., "eclipse")
	 */
	public void setModeByName(String shortModeName) {
		if ( !isAttached() ) {
			this.mode = shortModeName;
		} else {
			editor.setModeByName( shortModeName );
		}
	}

	/**
	 * Setting focus to the editor.
	 *
	 * @param focused boolean value
	 */
	public void setFocus( boolean focused ) {
		if ( !isAttached() ) {
			throw new IllegalStateException(
					"Widget should be attached to the browser's document before call setFocus");
		}
		
		editor.setFocus( focused );
    }

	/**
	 * Enable or disable syntax validation in the presence of proper worker                                  .
	 * @see <a href="https://github.com/ajaxorg/ace/wiki/Syntax-validation                                                       ">Ace syntax validation</a>
	 *
	 * @param useWorker boolean value
	 */
	public void setUseWorker( boolean useWorker ) {
		if ( !isAttached() ) {
			this.useWorker = useWorker;
		} else {
			editor.setUseWorker( useWorker );
		}
	}

	/**
	 * Register a handler for change events generated by the editor.
	 *
	 * @param callback the change event handler
	 */
	public void addOnChangeHandler(AceEditorCallback callback) {
		if ( !isAttached() ) {
			onChangeHandler = callback;
		} else {
			editor.addOnChangeHandler( callback );
		}
	}

	/**
	 * Register a handler for cursor position change events generated by the editor.
	 *
	 * @param callback the cursor position change event handler
	 */
	public void addOnCursorPositionChangeHandler(AceEditorCallback callback) {
		if ( !isAttached() ) {
			onChangeCursorHandler = callback;
		} else {
			editor.addOnCursorPositionChangeHandler( callback );
		}
	}

	/**
	 * Give font size
	 * @return font size
	 */
	public int getFontSize() {
		if ( !isAttached() ) {
			throw new IllegalStateException(
					"Widget should be attached to the browser's document before call getFontSize");
		}
		
		return editor.getFontSize();
	}

	/**
	 * Set font size.
	 * @param fontSize the font size to set, e.g., "16px"
	 */
	public void setFontSize(String fontSize) {
		editor.setFontSize( fontSize );
	}

	/**
	 * Set integer font size.
	 * @param fontSize the font size to set, e.g., 16
	 */
	public void setFontSize(int fontSize) {
		if ( !isAttached() ) {
			this.fontSize = fontSize;
		} else {
			editor.setFontSize( fontSize );
		}
	}

	/**
	 * Get the complete text in the editor as a String.
	 *
	 * @return the text in the editor
	 */
	public String getText() {
		if ( !isAttached() ) {
			throw new IllegalStateException(
					"Widget should be attached to the browser's document before call getText");
		}
		return editor.getText();
	}


	/**
	 * Set the complete text in the editor from a String.
	 *
	 * @param text the text to set in the editor
	 */
	public void setText(String text) {
		if ( !isAttached() ) {
			this.text = text;
		} else {
			editor.setText( text );
		}
	}

	/**
	 * Get the line of text at the given row number.
	 *
	 * @param row the row number
	 * @return the line of text at that row number
	 */
	public String getLine(int row) {
		if ( !isAttached() ) {
			throw new IllegalStateException(
					"Widget should be attached to the browser's document before call getLine" );
		}
		return editor.getLine( row );
	}

	/**
	 * Insert given text at the cursor.
	 *
	 * @param text text to insert at the cursor
	 */
	public void insertAtCursor(String text) {
		if ( !isAttached() ) {
			throw new IllegalStateException(
					"Widget should be attached to the browser's document before call insertAtCursor" );
		}
		editor.insertAtCursor( text );
	}

	/**
	 * Get the current cursor position.
	 *
	 * @return the current cursor position
	 */
	public AceEditorCursorPosition getCursorPosition() {
		if ( !isAttached() ) {
			throw new IllegalStateException(
					"Widget should be attached to the browser's document before call getCursorPosition" );
		}
		return editor.getCursorPosition();
	}

	/**
	 * Gets the given document position as a zero-based index.
	 *
	 * @param position the position to obtain the absolute index of (base zero)
	 * @return An index to the current location in the document
	 */
	public int getIndexFromPosition(AceEditorCursorPosition position) {
		if ( !isAttached() ) {
			throw new IllegalStateException(
					"Widget should be attached to the browser's document before call getIndexFromPosition" );
		}
		return editor.getIndexFromPosition( position );
	}

	/**
	 * Gets a document position from a supplied zero-based index.
	 *
	 * @param index (base zero)
	 * @return A position object showing the row and column of the supplied index in the document
	 */
	public AceEditorCursorPosition getPositionFromIndex( int index ) {
		if ( !isAttached() ) {
			throw new IllegalStateException(
					"Widget should be attached to the browser's document before call getPositionFromIndex" );
		}
		return editor.getPositionFromIndex( index );
	}

	/**
	 * Set whether or not soft tabs should be used.
	 *
	 * @param useSoftTabs true if soft tabs should be used, false otherwise
	 */
	public void setUseSoftTabs( boolean useSoftTabs ) {
		if ( !isAttached() ) {
			this.useSoftTabs = useSoftTabs;
		} else {
			editor.setUseSoftTabs( useSoftTabs );
		}
	}

	/**
	 * Set tab size.  (Default is 4.)
	 *
	 * @param tabSize the tab size to set
	 */
	public void setTabSize( int tabSize ) {
		if ( !isAttached() ) {
			this.tabSize = tabSize;
		} else {
			editor.setTabSize( tabSize );
		}
	}

	/**
	 * Go to given line.
	 *
	 * @param line the line to go to
	 */
	public void gotoLine(int line) {
		if ( !isAttached() ) {
			throw new IllegalStateException(
					"Widget should be attached to the browser's document before call gotoLine" );
		}
		editor.gotoLine( line );
	}
	/**
	 * Set whether or not the horizontal scrollbar is always visible.
	 *
	 * @param hScrollBarAlwaysVisible true if the horizontal scrollbar is always
	 *                                visible, false if it is hidden when not needed
	 */
	public void setHScrollBarAlwaysVisible(boolean hScrollBarAlwaysVisible) {
		if ( !isAttached() ) {
			this.hScrollBarAlwaysVisible = hScrollBarAlwaysVisible;
		} else {
			editor.setHScrollBarAlwaysVisible( hScrollBarAlwaysVisible );
		}
	}

	/**
	 * Set whether or not the gutter is shown.
	 *
	 * @param showGutter true if the gutter should be shown, false if it should be hidden
	 */
	public void setShowGutter(boolean showGutter) {
		if ( !isAttached() ) {
			this.showGutter = showGutter;
		} else {
			editor.setShowGutter( showGutter );
		}
	}

	/**
	 * Set or unset read-only mode.
	 *
	 * @param readOnly true if editor should be set to readonly, false if the
	 *                 editor should be set to read-write
	 */
	public void setReadOnly(boolean readOnly) {
		if ( !isAttached() ) {
			this.readOnly = readOnly;
		} else {
			editor.setReadOnly( readOnly );
		}
	}
	/**
	 * Set or unset highlighting of currently selected word.
	 *
	 * @param highlightSelectedWord true to highlight currently selected word, false otherwise
	 */
	public void setHighlightSelectedWord(boolean highlightSelectedWord) {
		if ( !isAttached() ) {
			this.highlightSelectedWord = highlightSelectedWord;
		} else {
			editor.setHighlightSelectedWord( highlightSelectedWord );
		}
	}

	public AceEditor getEditor() {
		if ( !isAttached() ) {
			throw new IllegalStateException(
					"Widget should be attached to the browser's document before call getEditor" );
		}
		return editor;
	}

	/**
	 * Set or unset the visibility of the print margin.
	 *
	 * @param showPrintMargin true if the print margin should be shown, false otherwise
	 */
	public native void setShowPrintMargin(boolean showPrintMargin) /*-{
		var editor = this.@edu.ycp.cs.dh.acegwt.client.ace.AceEditorWidget::editor;
		editor.renderer.setShowPrintMargin(showPrintMargin);
	}-*/;

	/**
	 * Add an annotation to a the local <code>annotations</code> JsArray<AceAnnotation>, but does not set it on the editor
	 *
	 * @param row to which the annotation should be added
	 * @param column to which the annotation applies
	 * @param text to display as a tooltip with the annotation
	 * @param type to be displayed (one of the values in the
	 *             {@link edu.ycp.cs.dh.acegwt.client.ace.AceAnnotationType} enumeration)
	 */
//	public void addAnnotation(final int row, final int column, final String text, final AceAnnotationType type) {
//		annotations.push(AceAnnotation.create(row, column, text, type.getName()));
//	}
//
//	/**
//	 * Set any annotations which have been added via <code>addAnnotation</code> on the editor
//	 */
//	public native void setAnnotations() /*-{
//		var editor = this.@edu.ycp.cs.dh.acegwt.client.ace.AceEditorWidget::editor;
//		var annotations = this.@edu.ycp.cs.dh.acegwt.client.ace.AceEditorWidget::annotations;
//		editor.getSession().setAnnotations(annotations);
//	}-*/;
//
//
//	/**
//	 * Clear any annotations from the editor and reset the local <code>annotations</code> JsArray<AceAnnotation>
//	 */
//	public native void clearAnnotations() /*-{
//		var editor = this.@edu.ycp.cs.dh.acegwt.client.ace.AceEditorWidget::editor;
//		editor.getSession().clearAnnotations();
//		this.@edu.ycp.cs.dh.acegwt.client.ace.AceEditorWidget::resetAnnotations()();
//	}-*/;
//
//	/**
//	 * Remove a command from the editor.
//	 *
//	 * @param command the command (one of the values in the
//	 *             {@link edu.ycp.cs.dh.acegwt.client.ace.AceCommand} enumeration)
//	 */
//	public void removeCommand(final AceCommand command) {
//		removeCommandByName(command.getName());
//	}
//
//	/**
//	 * Execute a command with no arguments. See {@link edu.ycp.cs.dh.acegwt.client.ace.AceCommand}
//	 * values for example.
//	 * @param command the command (one of the values in the
//	 *             {@link edu.ycp.cs.dh.acegwt.client.ace.AceCommand} enumeration)
//	 */
//	public void execCommand(AceCommand command) {
//		execCommand(command, null);
//	}
//
//	/**
//	 * Execute a command with arguments (in case args is not null).
//	 * See {@link edu.ycp.cs.dh.acegwt.client.ace.AceCommand} values for example.
//	 * @param command the command (one of the values in the
//	 *             {@link edu.ycp.cs.dh.acegwt.client.ace.AceCommand} enumeration)
//	 * @param args command arguments (string or map)
//	 */
//	public void execCommand(AceCommand command, AceCommandArgs args) {
//		execCommand(command.getName(), args);
//	}
//
//	/**
//	 * Execute a command possibly containing string argument.
//	 * @param command the command which could be one or two words separated
//	 * 				by whitespaces
//	 */
//	public native void execCommand(String command) /*-{
//		var parts = command.split(/\s+/);
//		this.@edu.ycp.cs.dh.acegwt.client.ace.AceEditorWidget::execCommand(Ljava/lang/String;Ljava/lang/String;)(parts[0], parts[1]);
//	}-*/;
//
//	/**
//	 * Execute a command with arguments (in case args is not null).
//	 * @param command one word command
//	 * @param args command argument
//	 */
//	public void execCommand(String command, String arg) {
//		execCommandHidden(command, arg);
//	}
//
//	/**
//	 * Execute a command with arguments (in case args is not null).
//	 * @param command one word command
//	 * @param args command arguments of type {@link edu.ycp.cs.dh.acegwt.client.ace.AceCommandArgs}
//	 */
//	public void execCommand(String command, AceCommandArgs args) {
//		execCommandHidden(command, args);
//	}
//
//	/**
//	 * Remove commands, that may not be required, from the editor
//	 *
//	 * @param command to be removed, one of
//	 *          "gotoline", "findnext", "findprevious", "find", "replace", "replaceall"
//	 */
//	public native void removeCommandByName(String command) /*-{
//		var editor = this.@edu.ycp.cs.dh.acegwt.client.ace.AceEditorWidget::editor;
//		editor.commands.removeCommand(command);
//	}-*/;
//
//	/**
//	 * Construct java wrapper for registered Ace command.
//	 * @param command name of command
//	 * @return command description
//	 */
//	public native AceCommandDescription getCommandDescription(String command) /*-{
//		var editor = this.@edu.ycp.cs.dh.acegwt.client.ace.AceEditorWidget::editor;
//		var obj = editor.commands.commands[command];
//		if (!obj)
//			return null;
//		return @edu.ycp.cs.dh.acegwt.client.ace.AceCommandDescription::fromJavaScript(Lcom/google/gwt/core/client/JavaScriptObject;)(obj);
//	}-*/;
//
//	/**
//	 * List names of all Ace commands.
//	 * @return names of all Ace commands
//	 */
//	public native List<String> listCommands() /*-{
//		var editor = this.@edu.ycp.cs.dh.acegwt.client.ace.AceEditorWidget::editor;
//		var ret = @java.util.ArrayList::new()();
//		for (var command in editor.commands.commands)
//			ret.@java.util.ArrayList::add(Ljava/lang/Object;)(command);
//		return ret;
//	}-*/;
//
//	/**
//	 * Add user defined command.
//	 * @param description command description
//	 */
//	public native void addCommand(AceCommandDescription description) /*-{
//		var editor = this.@edu.ycp.cs.dh.acegwt.client.ace.AceEditorWidget::editor;
//		var command = description.@edu.ycp.cs.dh.acegwt.client.ace.AceCommandDescription::toJavaScript(Ledu/ycp/cs/dh/acegwt/client/ace/AceEditor;)(this);
//		editor.commands.addCommand(command);
//	}-*/;
//
	/**
	 * Set whether to use wrap mode or not
	 *
	 * @param useWrapMode true if word wrap should be used, false otherwise
	 */
	public void setUseWrapMode(boolean useWrapMode) {
		if ( !isAttached() ) {
			this.useWrapMode = useWrapMode;
		} else {
			editor.setUseWrapMode( useWrapMode );
		}
	}

	/**
	 * Set whether or not autocomplete is enabled.
	 *
	 * @param b true if autocomplete should be enabled, false if not
	 */
	public void setAutocompleteEnabled(boolean b) {
		if ( !isAttached() ) {
			this.enableBasicAutocompletion = enableBasicAutocompletion;
		} else {
			editor.setAutocompleteEnabled( enableBasicAutocompletion );
		}
	}


//	/**
//	 * Removes all existing completers from the langtools<br><br>
//	 * This can be used to disable all completers including local completers, which can be very useful
//	 * when completers are used on very large files (as the local completer tokenizes every word to put in the selected list).<br><br>
//	 * <strong>NOTE:</strong> This method may be removed, and replaced with another solution. It works at point of check-in, but treat this as unstable for now.
//	 */
//	public native static void removeAllExistingCompleters() /*-{
//		var langTools = $wnd.ace.require("ace/ext/language_tools");
//		langTools.setCompleters([]);
//    }-*/;
//
//
//
//	/**
//	 * Add an {@link edu.ycp.cs.dh.acegwt.client.ace.AceCompletionProvider} to provide
//	 * custom code completions.
//	 *
//	 * <strong>Warning</strong>: this is an experimental feature of AceGWT.
//	 * It is possible that the API will change in an incompatible way
//	 * in future releases.
//	 *
//	 * @param provider the {@link edu.ycp.cs.dh.acegwt.client.ace.AceCompletionProvider}
//	 */
//	public native static void addCompletionProvider(AceCompletionProvider provider) /*-{
//		var langTools = $wnd.ace.require("ace/ext/language_tools");
//		var completer = {
//			getCompletions: function(editor, session, pos, prefix, callback) {
//				var callbackWrapper =
//					@edu.ycp.cs.dh.acegwt.client.ace.AceEditorWidget::wrapCompletionCallback(Lcom/google/gwt/core/client/JavaScriptObject;)(callback);
//				var aceEditor = editor._aceGWTAceEditor;
//				provider.@edu.ycp.cs.dh.acegwt.client.ace.AceCompletionProvider::getProposals(Ledu/ycp/cs/dh/acegwt/client/ace/AceEditor;Ledu/ycp/cs/dh/acegwt/client/ace/AceEditorCursorPosition;Ljava/lang/String;Ledu/ycp/cs/dh/acegwt/client/ace/AceCompletionCallback;)(
//					aceEditor,
//					@edu.ycp.cs.dh.acegwt.client.ace.AceEditorCursorPosition::create(II)( pos.row, pos.column ),
//					prefix,
//					callbackWrapper
//				);
//			},
//		    getDocTooltip: function(item) {
//		    	if ( (!item.docHTML) && item.aceGwtHtmlTooltip != null) {
//		        	item.docHTML = item.aceGwtHtmlTooltip;
//		    	}
//		    }
//		};
//		langTools.addCompleter(completer);
//	}-*/;
//
//	/**
//	 * Adds a static marker into this editor.
//	 * @param range		an {@link edu.ycp.cs.dh.acegwt.client.ace.AceRange}.
//	 * @param clazz		a CSS class that must be applied to the marker.
//	 * @param type		an {@link edu.ycp.cs.dh.acegwt.client.ace.AceMarkerType}.
//	 * @param inFront	set to 'true' if the marker must be in front of the text, 'false' otherwise.
//	 * @return	The marker ID. This id can be then use to remove a marker from the editor.
//	 */
//	public native int addMarker(AceRange range, String clazz, AceMarkerType type, boolean inFront) /*-{
//		var editor = this.@edu.ycp.cs.dh.acegwt.client.ace.AceEditorWidget::editor;
//		var markerID = editor.getSession().addMarker(range, clazz, type.@edu.ycp.cs.dh.acegwt.client.ace.AceMarkerType::getName()(), inFront);
//		this.@edu.ycp.cs.dh.acegwt.client.ace.AceEditorWidget::addMarker(ILedu/ycp/cs/dh/acegwt/client/ace/AceRange;)(markerID, range);
//		return markerID;
//	}-*/;
//
//	/**
//	 * Adds a floating marker into this editor (the marker follows lines changes as insertions, suppressions...).
//	 * @param range 	an {@link edu.ycp.cs.dh.acegwt.client.ace.AceRange}.
//	 * @param clazz		a CSS class that must be applied to the marker.
//	 * @param type		an {@link edu.ycp.cs.dh.acegwt.client.ace.AceMarkerType}.
//	 * @return			The marker ID. This id can be then use to remove a marker from the editor.
//	 */
//	public native int addFloatingMarker(AceRange range, String clazz, AceMarkerType type) /*-{
//		var editor = this.@edu.ycp.cs.dh.acegwt.client.ace.AceEditorWidget::editor;
//		range.start = editor.getSession().doc.createAnchor(range.start);
//		range.end = editor.getSession().doc.createAnchor(range.end);
//		return this.@edu.ycp.cs.dh.acegwt.client.ace.AceEditorWidget::addMarker(Ledu/ycp/cs/dh/acegwt/client/ace/AceRange;Ljava/lang/String;Ledu/ycp/cs/dh/acegwt/client/ace/AceMarkerType;Z)
//		(
//			range,
//			clazz,
//			type,
//			false
//		);
//	}-*/;
//
//	/**
//	 * Removes the marker with the specified ID.
//	 * @param markerId	the marker ID.
//	 */
//	public native void removeMarker(int markerId) /*-{
//		var editor = this.@edu.ycp.cs.dh.acegwt.client.ace.AceEditorWidget::editor;
//		editor.getSession().removeMarker(markerId);
//		this.@edu.ycp.cs.dh.acegwt.client.ace.AceEditorWidget::removeRegisteredMarker(I)(markerId);
//	}-*/;
//
//	/**
//	 * Gets all the displayed markers.
//	 * @return A Mapping between markerID and the displayed range.
//	 */
//	public HashMap<Integer, AceRange> getMarkers() {
//		return this.markers;
//	}
//
//	/**
//	 * Remove all the displayed markers.
//	 */
//	public void removeAllMarkers() {
//		for (Integer id : this.markers.keySet()) {
//			removeMarker(id);
//		}
//	}
//
//	private void addMarker(int id, AceRange range) {
//		markers.put(id, range);
//	}
//
//	private void removeRegisteredMarker(int id) {
//		AceRange range = markers.remove(id);
//		range.detach();
//	}
//
//	/**
//	 * Prepare a wrapper around Ace Selection object.
//	 * @return a wrapper around Ace Selection object
//	 */
//	public AceSelection getSelection() {
//		if (selection == null)
//			selection = new AceSelection(getSelectionJS());
//		return selection;
//	}
//
//	private native JavaScriptObject getSelectionJS() /*-{
//		var editor = this.@edu.ycp.cs.dh.acegwt.client.ace.AceEditorWidget::editor;
//		return editor.getSession().getSelection();
//	}-*/;
//
//	/**
//	 * Bind command line and editor. For default implementation of command line
//	 * you can use <code> AceCommandLine cmdLine = new AceDefaultCommandLine(textBox) </code>
//	 * where textBox could be for instance standard GWT TextBox or TextArea.
//	 * @param cmdLine implementation of command line
//	 */
//	public void initializeCommandLine(AceCommandLine cmdLine) {
//		this.commandLine = cmdLine;
//		this.commandLine.setCommandLineListener(new AceCommandLineListener() {
//			@Override
//			public void onCommandEntered(String command) {
//				execCommand(command);
//			}
//		});
//	}

}
