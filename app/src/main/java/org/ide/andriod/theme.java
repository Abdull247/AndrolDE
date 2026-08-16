package org.ide.andriod;

import io.github.rosemoe.sora.widget.EditorColorScheme;

public class theme extends EditorColorScheme {

    @Override
    public void applyDefault() {
        super.applyDefault();

        // Manually define the colors
        int primaryColor = 0xFF6200EE; // Example: Purple 500
        int secondaryColor = 0xFF03DAC6; // Example: Teal 200
        int backgroundColor = 0xFFFFFFFF; // Example: White background
        int surfaceColor = 0xFFFAFAFA; // Example: Light Gray surface
        int errorColor = 0xFFB00020; // Example: Red error color
        int onPrimaryColor = 0xFFFFFFFF; // Example: White text on primary
        int onSecondaryColor = 0xFF000000; // Example: Black text on secondary
        int onBackgroundColor = 0xFF000000; // Example: Black text on background
        int onSurfaceColor = 0xFF000000; // Example: Black text on surface
        int onCommentColor = 0xFF616161; // Example: Gray text on comment
        int dividerColor = 0xFFE0D6F5; // Soft purple-tinted divider matching primary
        int blockLineColor = 0xFFE8E8E8; // Subtle light gray indent guide
        int blockLineCurrentColor = 0xFFD8CFF0; // Slightly deeper muted purple for current block

        // Apply the colors to the editor's color scheme
        setColor(ANNOTATION, primaryColor);
        setColor(FUNCTION_NAME, secondaryColor);
        setColor(IDENTIFIER_NAME, primaryColor);
        setColor(IDENTIFIER_VAR, secondaryColor);
        setColor(LITERAL, errorColor);
        setColor(OPERATOR, primaryColor);
        setColor(COMMENT, onCommentColor);
        setColor(KEYWORD, primaryColor);
        setColor(WHOLE_BACKGROUND, backgroundColor);
        setColor(TEXT_NORMAL, onBackgroundColor);
        setColor(LINE_NUMBER_BACKGROUND, surfaceColor);
        setColor(LINE_NUMBER, onSurfaceColor);
        setColor(LINE_DIVIDER, dividerColor);
        setColor(SCROLL_BAR_THUMB, primaryColor);
        setColor(SCROLL_BAR_THUMB_PRESSED, secondaryColor);
        setColor(SELECTED_TEXT_BACKGROUND, primaryColor);
        setColor(MATCHED_TEXT_BACKGROUND, secondaryColor);
        setColor(CURRENT_LINE, surfaceColor);
        setColor(SELECTION_INSERT, primaryColor);
        setColor(SELECTION_HANDLE, primaryColor);
        setColor(BLOCK_LINE, blockLineColor);
        setColor(BLOCK_LINE_CURRENT, blockLineCurrentColor);
        setColor(AUTO_COMP_PANEL_BG, surfaceColor);
        setColor(AUTO_COMP_PANEL_CORNER, primaryColor);
        setColor(NON_PRINTABLE_CHAR, onSurfaceColor);
    }
}