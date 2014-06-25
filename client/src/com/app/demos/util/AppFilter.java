package com.app.demos.util;

import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import com.app.demos.R;

public class AppFilter {

	public static Spanned getHtml (String text) {
		return Html.fromHtml(text);
	}
	
	/* used by list classes */
	public static void setHtml (TextView tv, String text) {
		if (tv.getId() == R.id.tpl_list_blog_text_content ||
			tv.getId() == R.id.tpl_list_blog_text_comment ||
			tv.getId() == R.id.tpl_list_comment_content
			) {
			tv.setText(AppFilter.getHtml(text));
		} else {
			tv.setText(text);
		}
	}
}