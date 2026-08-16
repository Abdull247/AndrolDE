package com.apk.builder.logger;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {
    
	private List<Log> mData;
	private int tagColor = 0xFF6750A4;
	private int messageColor = 0xFF1C1B1F;
	private int errorColor = 0xFFB3261E;
	private int warningColor = 0xFFB25000;
	private int successColor = 0xFF2E7D32;

	public LogAdapter(List<Log> data) {
		mData = data;
	}

	public void setColors(int tagColor, int messageColor, int errorColor, int warningColor, int successColor) {
		this.tagColor = tagColor;
		this.messageColor = messageColor;
		this.errorColor = errorColor;
		this.warningColor = warningColor;
		this.successColor = successColor;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		Context context = parent.getContext();
		boolean dark = (context.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK)
				== android.content.res.Configuration.UI_MODE_NIGHT_YES;
		if (dark) {
			tagColor = 0xFFD0BCFF;
			messageColor = 0xFFE6E1E5;
			errorColor = 0xFFF2B8B5;
			warningColor = 0xFFFFB3A0;
			successColor = 0xFF7BC67E;
		}
		return new ViewHolder(new FrameLayout(parent.getContext()));
	}

	@Override
	public void onBindViewHolder (ViewHolder holder, int position) {
		Log log = mData.get(position);

		String tag = log.getTag();
		CharSequence msg = log.getMessage();

		SpannableStringBuilder sb = new SpannableStringBuilder();
		int baseStart = sb.length();
		sb.append("[");
		sb.append(tag);
		sb.append("] ");
		sb.setSpan(new ForegroundColorSpan(tagColor), baseStart, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sb.setSpan(new StyleSpan(Typeface.BOLD), baseStart, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		int msgStart = sb.length();
		String messageText = msg == null ? "" : msg.toString();
		sb.append(messageText);
		int color = messageColor;
		String lower = messageText.toLowerCase();
		if (lower.contains("error") || lower.contains("failed") || lower.contains("exception")
				|| lower.contains("cannot find") || lower.contains("unable to")) {
			color = errorColor;
		} else if (lower.contains("warn") || lower.contains("warning")) {
			color = warningColor;
		} else if (lower.contains("success") || lower.contains("build complete")) {
			color = successColor;
		}
		sb.setSpan(new ForegroundColorSpan(color), msgStart, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		holder.mText.setText(sb);
	}

	@Override
	public int getItemCount() {
		return mData.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
	    
		public TextView mText;
		
		public ViewHolder(View view) {
			super(view);
			
			mText = new TextView(view.getContext());
			mText.setTextSize(12);
			mText.setTypeface(Typeface.MONOSPACE);
			mText.setPadding(dp(view.getContext(), 8), dp(view.getContext(), 3), dp(view.getContext(), 8), dp(view.getContext(), 3));
			mText.setTextColor(0xFF1C1B1F);
			((ViewGroup) view).addView(mText);
		}
	}

	private static int dp(Context context, float value) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
	}
}
