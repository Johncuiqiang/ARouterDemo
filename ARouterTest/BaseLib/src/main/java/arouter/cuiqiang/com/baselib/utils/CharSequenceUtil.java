package arouter.cuiqiang.com.baselib.utils;

import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;

public class CharSequenceUtil {
    public static String charSequenceToHtml(CharSequence text) {
        return charSequenceToHtml(text, null);
    }

    public static String charSequenceToHtml(CharSequence text, String defaultText) {
        if (TextUtils.isEmpty(text)) {
            return defaultText;
        }

        if (text instanceof SpannableString) {
            defaultText = Html.toHtml((SpannableString) text);
            return defaultText.toString();
        } else if (text instanceof String) {
            String[] sequences = ((String) text).split("\\r?\\n");
            if (sequences.length == 0)
                return "";
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < sequences.length - 1) {
                sb.append(Html.escapeHtml(sequences[i]));
                sb.append("<br>");
                i += 1;
            }
            sb.append(Html.escapeHtml(sequences[(sequences.length - 1)]));
            return sb.toString();
        }
        return text.toString();
    }

    public static CharSequence htmlToCharSequence(String html) {
        if (html == null)
            return null;
        return trimTrailingWhitespace(Html.fromHtml(html));
    }

    private static CharSequence trimTrailingWhitespace(CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            return text;
        }
        int i = text.length();
        while ((i > 0) && (Character.isWhitespace(text.charAt(i - 1)))) {
            i -= 1;
        }
        return text.subSequence(0, i);
    }
}
