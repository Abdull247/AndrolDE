package org.ide.andriod;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import io.github.rosemoe.sora.widget.CodeEditor;

public class WordWrapHelper {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_WORD_WRAP = "wordwrap_checked";

    private final SharedPreferences prefs;
    private CodeEditor editor;

    public WordWrapHelper(Activity activity) {
        this.prefs = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void bindEditor(CodeEditor editor) {
        this.editor = editor;
        // Apply persisted state immediately once the editor is bound
        applyWordWrap(isWordWrapEnabled());
    }

    public boolean isWordWrapEnabled() {
        return prefs.getBoolean(KEY_WORD_WRAP, false);
    }

    public void setWordWrapEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_WORD_WRAP, enabled).apply();
        applyWordWrap(enabled);
    }

    public void toggleWordWrap() {
        setWordWrapEnabled(!isWordWrapEnabled());
    }

    private void applyWordWrap(boolean enabled) {
        if (editor == null) return;
        editor.setWordwrap(enabled);
    }
}