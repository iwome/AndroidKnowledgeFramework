package com.bbq.akf.lib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.alibaba.fastjson.JSON;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 说明
 * Created by bangbang.qiu on 2021/2/4.
 */
public class ConvertUtil {
    private ConvertUtil() {
    }

    public static final ConvertUtil getInstance() {
        return ConvertUtil.ExConvertUtilHolder.ecu;
    }

    public final String getInStream2Str(InputStream in) {
        return this.inputStreamToStr(in, Charset.defaultCharset());
    }

    public final String inputStreamToStr(InputStream in, String encoding) {
        return this.inputStreamToStr(in, Charset.forName(encoding));
    }

    public final String inputStreamToStr(InputStream in, Charset encoding) {
        InputStreamReader input = new InputStreamReader(in, encoding);
        StringWriter output = new StringWriter();
        String result = null;

        try {
            char[] buffer = new char[4096];
            boolean var7 = false;

            int n;
            while(-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }

            result = output.toString();
        } catch (IOException var20) {
            var20.printStackTrace();
        } finally {
            if (output != null) {
                output.flush();

                try {
                    output.close();
                } catch (IOException var19) {
                    var19.printStackTrace();
                }
            }

            if (input != null) {
                try {
                    input.close();
                } catch (IOException var18) {
                    var18.printStackTrace();
                }
            }

        }

        return result;
    }

    public final byte[] bitmapToBytes(Bitmap bm) {
        if (bm == null) {
            return null;
        } else {
            byte[] result = null;
            ByteArrayOutputStream baos = null;

            try {
                baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
                result = baos.toByteArray();
            } catch (Exception var13) {
                var13.printStackTrace();
            } finally {
                try {
                    if (baos != null) {
                        baos.flush();
                        baos.close();
                    }
                } catch (IOException var12) {
                    var12.printStackTrace();
                }

            }

            return result;
        }
    }

    public final Bitmap bytesToBitmap(byte[] b) {
        return b.length == 0 ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    public final Drawable bitmapToDrawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    public final Bitmap drawableToBitmap(Drawable drawable) {
        return drawable == null ? null : ((BitmapDrawable)drawable).getBitmap();
    }

    public final String htmlToString(String htmlStr) {
        String result = "";
        String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
        String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
        String regEx_html = "<[^>]+>";

        try {
            Pattern p_script = Pattern.compile(regEx_script, 2);
            Matcher m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll("");
            Pattern p_style = Pattern.compile(regEx_style, 2);
            Matcher m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll("");
            Pattern p_html = Pattern.compile(regEx_html, 2);
            Matcher m_html = p_html.matcher(regEx_html);
            htmlStr = m_html.replaceAll("");
            result = htmlStr;
        } catch (Exception var13) {
            var13.printStackTrace();
        }

        return result;
    }

    public final byte[] objToByte(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;

        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
        } catch (IOException var18) {
            var18.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException var17) {
                    var17.printStackTrace();
                }
            }

            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException var16) {
                    var16.printStackTrace();
                }
            }

        }

        return bytes;
    }

    public final Object byteToObject(byte[] bytes) {
        Object obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;

        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } catch (IOException var21) {
            var21.printStackTrace();
        } catch (ClassNotFoundException var22) {
            var22.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException var20) {
                    var20.printStackTrace();
                }
            }

            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException var19) {
                    var19.printStackTrace();
                }
            }

        }

        return obj;
    }

    public final int getStringToInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception var4) {
            return defValue;
        }
    }

    public final long getStringToLong(String str, long defValue) {
        try {
            return Long.parseLong(str);
        } catch (Exception var5) {
            return defValue;
        }
    }

    public final double getStringToDouble(String str, double defValue) {
        try {
            return Double.parseDouble(str);
        } catch (Exception var5) {
            return defValue;
        }
    }

    public final float getStringToFloat(String str, float defValue) {
        try {
            return Float.parseFloat(str);
        } catch (Exception var4) {
            return defValue;
        }
    }

    public final boolean getStringToBool(String str, boolean defValue) {
        try {
            return Boolean.parseBoolean(str);
        } catch (Exception var4) {
            return defValue;
        }
    }

    public final int dipToPx(Context context, float dpValue) {
        if (context == null) {
            return 0;
        } else {
            float scale = context.getResources().getDisplayMetrics().density;
            return (int)(dpValue * scale + 0.5F);
        }
    }

    public final int pxToDip(Context context, float pxValue) {
        if (context == null) {
            return 0;
        } else {
            float scale = context.getResources().getDisplayMetrics().density;
            return (int)(pxValue / scale + 0.5F);
        }
    }

    public final int pxToSp(Context context, float pxValue) {
        if (context == null) {
            return 0;
        } else {
            float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int)(pxValue / fontScale + 0.5F);
        }
    }

    public final int spToPx(Context context, float spValue) {
        if (context == null) {
            return 0;
        } else {
            float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int)(spValue * fontScale + 0.5F);
        }
    }

    public final <T> List<T> getStringToList(String array, Class<T> cls) {
        ArrayList list = new ArrayList();

        try {
            JSONArray jsonArray = new JSONArray(array);

            for(int i = 0; i < jsonArray.length(); ++i) {
                T t = this.strToObj(jsonArray.get(i).toString(), cls);
                list.add(t);
            }
        } catch (JSONException var7) {
            var7.printStackTrace();
        }

        return list;
    }

    public final <T> T strToObj(String bean, Class<T> cls) {
        if (CommonUtil.isEmpty(bean)) {
            return null;
        } else {
            try {
                return JSON.parseObject(bean, cls);
            } catch (Exception var6) {
                var6.printStackTrace();

                try {
                    return cls.newInstance();
                } catch (InstantiationException var4) {
                    var4.printStackTrace();
                } catch (IllegalAccessException var5) {
                    var5.printStackTrace();
                }

                return null;
            }
        }
    }

    public final <T> String objToStr(T t) {
        return JSON.toJSONString(t);
    }

    public final byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        } else {
            FileInputStream stream = null;
            ByteArrayOutputStream out = null;

            try {
                stream = new FileInputStream(f);
                out = new ByteArrayOutputStream(4096);
                byte[] b = new byte[4096];

                int n;
                while((n = stream.read(b)) != -1) {
                    out.write(b, 0, n);
                }

                byte[] var17 = out.toByteArray();
                return var17;
            } catch (Exception var15) {
            } finally {
                try {
                    if (stream != null) {
                        stream.close();
                    }

                    if (out != null) {
                        out.close();
                    }
                } catch (Exception var14) {
                    var14.printStackTrace();
                }

            }

            return null;
        }
    }

    public final String fromDBCToSBC(String input) {
        char[] c = input.toCharArray();

        for(int i = 0; i < c.length; ++i) {
            if (c[i] == ' ') {
                c[i] = 12288;
            } else if (c[i] < 127) {
                c[i] += 'ﻠ';
            }
        }

        return new String(c);
    }

    public final String fromSBCToDBC(String input) {
        char[] c = input.toCharArray();

        for(int i = 0; i < c.length; ++i) {
            if (c[i] == 12288) {
                c[i] = ' ';
            } else if (c[i] > '\uff00' && c[i] < '｟') {
                c[i] -= 'ﻠ';
            }
        }

        return new String(c);
    }

    public InputStream byteToInputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    private static class ExConvertUtilHolder {
        private static final ConvertUtil ecu = new ConvertUtil();

        private ExConvertUtilHolder() {
        }
    }
}
