package com.bbq.akf.lib.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.util.SparseArray;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Intent.URI_INTENT_SCHEME;

/**
 * 说明
 * Created by bangbang.qiu on 2021/2/4.
 */
public class CommonUtil {
    private static long lastClickTime;

    private CommonUtil() {
    }

    public static final CommonUtil getInstance() {
        return CommonUtil.ExCommonUtilHolder.ecu;
    }

    public static final boolean isEmpty(String input) {
        return TextUtils.isEmpty(input);
    }

    public static final boolean isEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }

    public static final boolean isEmpty(Set<?> set) {
        return set == null || set.size() == 0;
    }

    public static final boolean isEmpty(Map<?, ?> map) {
        return map == null || map.size() == 0;
    }

    public static final boolean isEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    public static final boolean isEmpty(float[] array) {
        return array == null || array.length == 0;
    }

    public static final boolean isEmpty(long[] array) {
        return array == null || array.length == 0;
    }

    public static final boolean isEmpty(double[] array) {
        return array == null || array.length == 0;
    }

    public static final boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static final boolean isEmpty(byte[] array) {
        return array == null || array.length == 0;
    }

    public static final boolean isEmpty(Object obj) {
        return obj == null;
    }

    public static final boolean isEmpty(int obj) {
        return obj == 0;
    }

    public static final boolean isEmpty(float obj) {
        return obj == 0.0F;
    }

    public static final boolean isEmpty(long obj) {
        return obj == 0L;
    }

    public static final boolean isEmpty(SparseArray obj) {
        return obj == null || obj.size() == 0;
    }

    public static final boolean isEmpty(CharSequence obj) {
        return TextUtils.isEmpty(obj);
    }

    public static final String dealEmpty(String str) {
        return isEmpty(str) ? "" : str;
    }

    public static final String stringUTF8Encode(String str) {
        return stringEncode(str, "UTF-8");
    }

    public static final String stringEncode(String str, String charset) {
        if (!isEmpty(str)) {
            try {
                return URLEncoder.encode(str, charset);
            } catch (Exception var3) {
                var3.printStackTrace();
            }
        }

        return str;
    }

    public static final String stringUTF8Decode(String str) {
        return stringDecode(str, "UTF-8");
    }

    public static final String stringDecode(String str, String charset) {
        if (!isEmpty(str)) {
            try {
                return URLDecoder.decode(str, charset);
            } catch (Exception var3) {
                var3.printStackTrace();
            }
        }

        return str;
    }

    public static final String unicodeDecode(String str) {
        StringBuffer sb = new StringBuffer();
        String[] arr = str.split("\\\\u");
        int len = arr.length;
        sb.append(arr[0]);

        for (int i = 1; i < len; ++i) {
            String tmp = arr[i];
            char c = (char) Integer.parseInt(tmp.substring(0, 4), 16);
            sb.append(c);
            sb.append(tmp.substring(4));
        }

        return sb.toString();
    }

    public static final String unicodeEncode(String strText) {
        StringBuffer strRet = new StringBuffer();

        for (int i = 0; i < strText.length(); ++i) {
            char c = strText.charAt(i);
            if (c > 128) {
                strRet.append("\\u" + Integer.toHexString(c));
            } else {
                strRet.append(c);
            }
        }

        return strRet.toString();
    }

    public static final Double add(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.add(b2).doubleValue();
    }

    public static final Double sub(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.subtract(b2).doubleValue();
    }

    public static final Double mul(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.multiply(b2).doubleValue();
    }

    public static final Double div(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2, 10, 4).doubleValue();
    }

    public static boolean canClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD > 300L) {
            lastClickTime = time;
            return true;
        } else {
            return false;
        }
    }

    public static boolean copyToClipboard(Context context, String text) {
        if (context == null) {
            return false;
        } else {
            try {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Text has been copied to clipboard", text);
                clipboard.setPrimaryClip(clip);
                return true;
            } catch (Exception var4) {
                return false;
            }
        }
    }

    public static String readFromClipboard(Context context) {
        if (context == null) {
            return "";
        } else {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
            if (clipboard == null) {
                return "";
            } else {
                ClipData clip = clipboard.getPrimaryClip();
                if (clip != null) {
                    ClipData.Item item = clip.getItemAt(0);
                    CharSequence text = coerceToText(context, item);
                    if (text != null) {
                        return text.toString();
                    }
                }

                return "";
            }
        }
    }

    private static CharSequence coerceToText(Context context, ClipData.Item item) {
        CharSequence text = item.getText();
        Uri uri = item.getUri();
        if (isEmpty((CharSequence) text) && uri != null) {
            FileInputStream globalStream = null;
            InputStreamReader globalReader = null;
            AssetFileDescriptor globalDescr = null;

            try {
                AssetFileDescriptor descr = context.getContentResolver().openTypedAssetFileDescriptor(uri, "text/*", (Bundle) null);
                globalDescr = descr;
                FileInputStream stream = descr.createInputStream();
                globalStream = stream;
                InputStreamReader reader = new InputStreamReader(stream, getCharset());
                globalReader = reader;
                StringBuilder builder = new StringBuilder(128);
                char[] buffer = new char[8192];

                int len;
                while ((len = reader.read(buffer)) > 0) {
                    builder.append(buffer, 0, len);
                }

                text = builder.toString();
            } catch (FileNotFoundException var18) {
//                ExLogUtil.e("ClippedData", "Unable to open content URI as text\n" + var18);
                var18.printStackTrace();
            } catch (IOException var19) {
//                ExLogUtil.e("ClippedData", "Failure loading text\n" + var19);
                var19.printStackTrace();
            } catch (Exception var20) {
                var20.printStackTrace();
            } finally {
                closeIo(globalStream, globalReader, globalDescr);
            }

            text = isEmpty((CharSequence) text) ? uri.toString() : text;
        }

        Intent intent = item.getIntent();
        if (isEmpty((CharSequence) text) && intent != null) {
            text = intent.toUri(URI_INTENT_SCHEME);
        }

        return (CharSequence) text;
    }

    private static void closeIo(Closeable... closeables) {
        if (!isEmpty((Object[]) closeables)) {
            try {
                Closeable[] var1 = closeables;
                int var2 = closeables.length;

                for (int var3 = 0; var3 < var2; ++var3) {
                    Closeable closeable = var1[var3];
                    if (!isEmpty((Object) closeable)) {
                        closeable.close();
                    }
                }
            } catch (IOException var5) {
                var5.printStackTrace();
            }

        }
    }

    private static Charset getCharset() {
        return Build.VERSION.SDK_INT < 19 ? Charset.forName("UTF-8") : StandardCharsets.UTF_8;
    }

    public static boolean validateMobileNumber(String number) {
        if (null != number && !"".equals(number)) {
            Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$");
            Matcher m = p.matcher(number);
            return m.matches();
        } else {
            return false;
        }
    }

    public static boolean validateEmail(String email) {
        if (null != email && !"".equals(email) && email.length() <= 50) {
            Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
            Matcher m = p.matcher(email);
            return m.matches();
        } else {
            return false;
        }
    }

    public static String getShowupFormatedCellPhone(String cellPhone) {
        if (isEmpty(cellPhone)) {
            return "";
        } else {
            return cellPhone.length() != 11 ? "" : cellPhone.replace(cellPhone.substring(3, 7), "****");
        }
    }

    public static String getFormatedCellPhone(String cellPhone) {
        if (!isEmpty(cellPhone) && cellPhone.length() == 11) {
            StringBuilder sb = new StringBuilder(cellPhone);
            return sb.replace(3, 7, "****").toString();
        } else {
            return "";
        }
    }

    public static String getShowupFormatedUserName(String name) {
        if (isEmpty(name)) {
            return "";
        } else if (!validateEmail(name)) {
            return "";
        } else {
            String str1 = name.substring(0, name.indexOf("@"));
            String str2 = name.substring(name.indexOf("@"), name.length());
            int len = str1.length();
            String str3 = str1.substring(0, 1);
            String str4 = str1.substring(len - 1, len);
            StringBuffer str5 = new StringBuffer();
            if (len > 2) {
                for (int i = 0; i < len - 2; ++i) {
                    str5.append("*");
                }
            }

            return str3 + str5.toString() + str4 + str2;
        }
    }

    public static void setBackgroundAlpha(Context context, float bgAlpha) {
        if (context != null && context instanceof Activity) {
            WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
            lp.alpha = bgAlpha;
            if (bgAlpha == 1.0F) {
                ((Activity) context).getWindow().clearFlags(2);
            } else {
                ((Activity) context).getWindow().addFlags(2);
            }

            ((Activity) context).getWindow().setAttributes(lp);
        }
    }

    public static void addStrikethrough(TextView view, CharSequence text, int start, int end) {
        SpannableString sp = new SpannableString(text);
        sp.setSpan(new StrikethroughSpan(), start, end, 33);
        view.setText(sp);
    }

    public static void addStrikethrough(TextView view, CharSequence text) {
        SpannableString sp = new SpannableString(text);
        sp.setSpan(new StrikethroughSpan(), 0, text.length(), 33);
        view.setText(sp);
    }

    public static String addStrikethrough(CharSequence text) {
        SpannableString sp = new SpannableString(text);
        sp.setSpan(new StrikethroughSpan(), 0, text.length(), 33);
        return sp.toString();
    }

    public static String replaceEmoji(String source, String replacement) {
        StringBuilder result = new StringBuilder();
        if (isEmpty(source)) {
            return source;
        } else {
            boolean replacementIsEmpty = isEmpty(replacement);
            if (!containsEmoji(source)) {
                return source;
            } else {
                int len = source.length();
                char[] dest = new char[len];

                for (int i = 0; i < len; ++i) {
                    char codePoint = source.charAt(i);
                    if (isNotEmojiCharacter(codePoint)) {
                        result.append(codePoint);
                        dest[i] = codePoint;
                    } else if (!replacementIsEmpty) {
                        result.append(replacement);
                    }
                }

                return result.toString();
            }
        }
    }

    public static boolean containsEmoji(String source) {
        if (isEmpty(source)) {
            return false;
        } else {
            int len = source.length();

            for (int i = 0; i < len; ++i) {
                char codePoint = source.charAt(i);
                if (!isNotEmojiCharacter(codePoint)) {
                    return true;
                }
            }

            return false;
        }
    }

    private static boolean isNotEmojiCharacter(char codePoint) {
        return codePoint != 9786 && (codePoint == 0 || codePoint == '\t' || codePoint == '\n' || codePoint == '\r' || codePoint >= ' ' && codePoint <= '\ud7ff' || codePoint >= '\ue000' && codePoint <= '�');
    }

    private static class ExCommonUtilHolder {
        private static final CommonUtil ecu = new CommonUtil();

        private ExCommonUtilHolder() {
        }
    }
}
