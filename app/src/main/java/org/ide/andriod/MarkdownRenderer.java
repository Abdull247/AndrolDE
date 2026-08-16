package org.ide.andriod;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MarkdownRenderer {

    public static void renderStructured(Context context, String jsonArrayString, LinearLayout container) {

        container.removeAllViews();

        try {

            JSONArray blocks = new JSONArray(jsonArrayString);

            for (int i = 0; i < blocks.length(); i++) {

                JSONObject block = blocks.getJSONObject(i);
                String type = block.getString("type");

                switch (type) {

                    case "paragraph":
                        addParagraph(context, container, block.getString("text"));
                        break;

                    case "heading":
                        int level = block.getInt("level");
                        addHeading(context, container,
                                block.getString("text"),
                                level == 1 ? 24 :
                                level == 2 ? 20 :
                                18);
                        break;

                    case "divider":
                        addDivider(context, container);
                        break;

                    case "code":
                        addCodeBlock(context, container,
                                block.getString("content"));
                        break;

                    case "list_item":
                        addBullet(context, container,
                                block.getString("text"));
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addHeading(Context context, LinearLayout container, String text, int sizeSp) {

        TextView tv = new TextView(context);
        tv.setText(text);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(sizeSp);
        tv.setTypeface(Typeface.DEFAULT_BOLD);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        params.setMargins(0, 20, 0, 10);
        tv.setLayoutParams(params);

        container.addView(tv);
    }

    private static void addParagraph(Context context, LinearLayout container, String text) {

        TextView tv = new TextView(context);
        tv.setText(text);
        tv.setTextColor(Color.parseColor("#DDDDDD"));
        tv.setTextSize(16);
        tv.setLineSpacing(0, 1.2f);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        params.setMargins(0, 6, 0, 6);
        tv.setLayoutParams(params);

        container.addView(tv);
    }

    private static void addCodeBlock(Context context, LinearLayout container, String code) {

        LinearLayout wrapper = new LinearLayout(context);
        wrapper.setOrientation(LinearLayout.VERTICAL);
        wrapper.setBackgroundResource(R.drawable.code_background);
        wrapper.setPadding(24, 24, 24, 24);

        LinearLayout.LayoutParams wrapperParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        wrapperParams.setMargins(0, 20, 0, 20);
        wrapper.setLayoutParams(wrapperParams);

        HorizontalScrollView scroll = new HorizontalScrollView(context);

        TextView tv = new TextView(context);
        tv.setText(code.trim());
        tv.setTextColor(Color.parseColor("#00FFAA"));
        tv.setTypeface(Typeface.MONOSPACE);
        tv.setTextSize(14);

        scroll.addView(tv);
        wrapper.addView(scroll);

        container.addView(wrapper);
    }

    private static void addDivider(Context context, LinearLayout container) {

        LinearLayout divider = new LinearLayout(context);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        2
                );

        params.setMargins(0, 20, 0, 20);
        divider.setLayoutParams(params);
        divider.setBackgroundColor(Color.parseColor("#444444"));

        container.addView(divider);
    }

    private static void addBullet(Context context, LinearLayout container, String text) {

        TextView tv = new TextView(context);
        tv.setText("• " + text);
        tv.setTextColor(Color.parseColor("#DDDDDD"));
        tv.setTextSize(16);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        params.setMargins(20, 4, 0, 4);
        tv.setLayoutParams(params);

        container.addView(tv);
    }
}