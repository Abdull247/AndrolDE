package org.ide.andriod;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.view.View;

public class KeyboardVisibilityHelper {

    private static final String PREFS_NAME = "KeyboardVisibilityHelperPrefs";
    private static final String KEY_VIEW_MODE_HIDDEN = "view_mode_hidden";

    private final Activity activity;
    private final View rootView;
    private final SharedPreferences prefs;
    private View bottomHolder;

    private boolean isKeyboardShowing = false;
    private boolean isEditorTabActive = true; // Default to true
    private boolean isViewModeHidden;

    public KeyboardVisibilityHelper(Activity activity) {
        this.activity = activity;
        this.rootView = activity.findViewById(android.R.id.content);
        this.prefs = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.isViewModeHidden = prefs.getBoolean(KEY_VIEW_MODE_HIDDEN, false);
    }

    public void bindBottomHolder(View holder) {
        this.bottomHolder = holder;
    }

    // Call this whenever the tab changes in EditorActivity
    public void setEditorTabActive(boolean active) {
        this.isEditorTabActive = active;
    }

    // Call this whenever toggle_view_mode is triggered (lock/hide mode)
    // Persists the state so it survives editor close/reopen.
    public void setViewModeHidden(boolean hidden) {
        this.isViewModeHidden = hidden;
        prefs.edit().putBoolean(KEY_VIEW_MODE_HIDDEN, hidden).apply();
    }

    public boolean isViewModeHidden() {
        return isViewModeHidden;
    }

    public void bindEditor(View editor) {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            rootView.getWindowVisibleDisplayFrame(r);
            int screenHeight = rootView.getRootView().getHeight();
            int keypadHeight = screenHeight - r.bottom;

            boolean isCurrentlyVisible = keypadHeight > screenHeight * 0.15;

            if (isCurrentlyVisible != isKeyboardShowing) {
                isKeyboardShowing = isCurrentlyVisible;

                // Keep state tracking accurate even on non-editor tabs or
                // hidden view mode, but skip bottomHolder animation while
                // inactive or hidden — prevents desync/flash issues.
                if (!isEditorTabActive || isViewModeHidden) {
                    return;
                }

                if (isKeyboardShowing) {
                    hideBottomHolder();
                } else {
                    showBottomHolder();
                }
            }
        });
    }

    private void hideBottomHolder() {
        if (bottomHolder != null) {
            bottomHolder.animate().cancel();
            bottomHolder.animate()
                    .alpha(0f)
                    .setDuration(150)
                    .withEndAction(() -> bottomHolder.setVisibility(View.GONE))
                    .start();
        }
    }

    private void showBottomHolder() {
        if (bottomHolder != null) {
            bottomHolder.animate().cancel();
            bottomHolder.setVisibility(View.VISIBLE);
            bottomHolder.animate()
                    .alpha(1f)
                    .setDuration(200)
                    .start();
        }
    }
}