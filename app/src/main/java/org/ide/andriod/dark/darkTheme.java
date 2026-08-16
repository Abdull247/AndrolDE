package org.ide.andriod;

import io.github.rosemoe.sora.widget.EditorColorScheme;

public class darkTheme extends EditorColorScheme {

    @Override
    public void applyDefault() {
        super.applyDefault();

        // Manually define the colors for a dark theme
        int primaryColor = 0xFFBB86FC; // Example: Purple 200
        int secondaryColor = 0xFF03DAC6; // Example: Teal 200
        int backgroundColor = 0xFF121212; // Example: Dark Gray background
        int surfaceColor = 0xFF1E1E1E; // Example: Slightly lighter Dark Gray surface
        int errorColor = 0xFFCF6679; // Example: Light Red error color
        int onPrimaryColor = 0xFF000000; // Example: Black text on primary
        int onSecondaryColor = 0xFF000000; // Example: Black text on secondary
        int onBackgroundColor = 0xFFFFFFFF; // Example: White text on background
        int onSurfaceColor = 0xFFFFFFFF; // Example: White text on surface
        int onCommentColor = 0xFF616161; // Example: Gray text on comment
        int dividerColor = 0xFF3A2E4D; // Muted purple-tinted divider matching dark primary
        int blockLineColor = 0xFF2A2A2A; // Subtle gray indent guide, low contrast
        int blockLineCurrentColor = 0xFF4A4458; // Slightly brighter muted purple-gray for current block
        int nonPrintableColor = 0xFF3A3A3A; // Subtle gray whitespace tick marks

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
        setColor(NON_PRINTABLE_CHAR, nonPrintableColor);
    }
}