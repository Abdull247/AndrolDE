package org.ide.andriod;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import io.github.rosemoe.sora.widget.CodeEditor;

public class TextSizeHelper {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_TEXT_SIZE = "editor_text_size";
    private static final float DEFAULT_SIZE_SP = 14f;

    private final SharedPreferences prefs;
    private CodeEditor editor;

    public TextSizeHelper(Activity activity) {
        this.prefs = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void bindEditor(CodeEditor editor) {
        this.editor = editor;
        // Apply persisted size immediately once the editor is bound
        applyTextSize(getTextSize());
    }

    public float getTextSize() {
        return prefs.getFloat(KEY_TEXT_SIZE, DEFAULT_SIZE_SP);
    }

    public void setTextSize(float sizeSp) {
        prefs.edit().putFloat(KEY_TEXT_SIZE, sizeSp).apply();
        applyTextSize(sizeSp);
    }

    private void applyTextSize(float sizeSp) {
        if (editor == null) return;
        editor.setTextSize(sizeSp);
    }
}