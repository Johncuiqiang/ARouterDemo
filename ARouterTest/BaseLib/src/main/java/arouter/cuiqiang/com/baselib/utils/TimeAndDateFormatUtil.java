package arouter.cuiqiang.com.baselib.utils;

import android.content.Context;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import arouter.cuiqiang.com.baselib.LingContext;

/**
 * Created by cuiqiang on 2018/7/20.
 */

public class TimeAndDateFormatUtil {

    private static final Context appContext = LingContext
            .getApplicatonContext();

    private static final SimpleDateFormat FORMATTER_ZH = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    private static final SimpleDateFormat FORMATTER_ZH_12 = new SimpleDateFormat(
            "yyyy-MM-dd hh:mm:ss", Locale.CHINA);

    private static final SimpleDateFormat FORMATTER_EN = new SimpleDateFormat(
            "EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

    public static final SimpleDateFormat FORMATTER_yMdHms = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat FORMATTER_yMdHm = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm");

    public static final SimpleDateFormat FORMATTER_yMdH = new SimpleDateFormat(
            "yyyy-MM-dd HH");

    public static final SimpleDateFormat FORMATTER_yMd = new SimpleDateFormat(
            "yyyy-MM-dd");

    public static final SimpleDateFormat SUB_CONTENT_DATE_FORMAT = new SimpleDateFormat(
            "yyyyMMdd");
    public static final SimpleDateFormat SUB_CONTENT_DATE_FORMAT_yMdHms = new SimpleDateFormat(
            "yyyyMMddHHmmss");

    public static final SimpleDateFormat FORMATER_yM = new SimpleDateFormat(
            "yyyy-MM");

    public static final SimpleDateFormat FORMATTER_y = new SimpleDateFormat(
            "yyyy");

    public static final SimpleDateFormat FORMATTER_Md = new SimpleDateFormat(
            "MM-dd");

    public static final SimpleDateFormat FORMATTER_MdHm = new SimpleDateFormat(
            "MM-dd HH:mm");

    public static final SimpleDateFormat FORMATTER_Hms = new SimpleDateFormat(
            "HH:mm:ss");

    public static final SimpleDateFormat FORMATTER_Hm = new SimpleDateFormat(
            "HH:mm");

    public static final SimpleDateFormat FORMATTER_M = new SimpleDateFormat(
            "mm");

    /**
     * @param aDateStr
     * @return
     */
    public static String formateDate_MdHm(String aDateStr) {
        String dateStr = null;
        try {
            Date date = FORMATTER_EN.parse(aDateStr.trim());
            dateStr = FORMATTER_MdHm.format(date);
        } catch (Exception e) {
            try {
                Date date = FORMATTER_ZH.parse(aDateStr.trim());
                dateStr = FORMATTER_MdHm.format(date);
            } catch (Exception e1) {
                dateStr = aDateStr;
            }
        }
        return dateStr;
    }

    /**
     * 获取当前的系统时间精确到毫秒
     *
     * @return
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前的系统时间精确到秒
     *
     * @return
     */
    public static long getCurrentTimeOfSeconds() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取当前时间字符串
     *  yyyyMMddHHmmss
     * @return
     */
    public static String getCurrTimeOfDate_yMdHms(){
        return formateDateSub_yMdHms(getCurrentTime());
    }

    public static String getCurrTimeByStyle(SimpleDateFormat formatStyle){
        Date date = new Date(getCurrentTime());
        return formatStyle.format(date);
    }

    /**
     * 格式化时间
     *
     * @param time long
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDate_yMdHms(long time) {
        String dateStr = null;
        try {
            Date date = new Date(time);
            dateStr = FORMATTER_yMdHms.format(date);
        } catch (Exception e) {
            dateStr = "";
        }
        return dateStr;
    }

    /**
     * 格式化时间
     *
     * @param date Date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDate_yMdHms(Date date) {
        String dateStr = null;
        try {
            dateStr = FORMATTER_yMdHms.format(date);
        } catch (Exception e) {
            dateStr = "";
        }
        return dateStr;
    }

    /**
     * 格式化时间
     *
     * @param time long
     * @return yyyy-MM-dd HH:mm
     */
    public static String formateDate_yMdHm(long time) {
        String dateStr = null;
        try {
            Date date = new Date(time);
            dateStr = FORMATTER_yMdHm.format(date);
        } catch (Exception e) {
            dateStr = "";
        }
        return dateStr;
    }

    /**
     * 格式化时间
     *
     * @param date Date
     * @return yyyy-MM-dd HH:mm
     */
    public static String formatDate_yMdHm(Date date) {
        String dateStr = null;
        try {
            dateStr = FORMATTER_yMdHm.format(date);
        } catch (Exception e) {
            dateStr = "";
        }
        return dateStr;
    }

    /**
     * 格式化时间
     *
     * @param time long
     * @return yyyy-MM-dd HH
     */
    public static String formateDate_yMdH(long time) {
        String dateStr = null;
        try {
            Date date = new Date(time);
            dateStr = FORMATTER_yMdH.format(date);
        } catch (Exception e) {
            dateStr = "";
        }
        return dateStr;
    }

    /**
     * 格式化时间
     *
     * @param date Date
     * @return yyyy-MM-dd HH
     */
    public static String formatDate_yMdH(Date date) {
        String dateStr = null;
        try {
            dateStr = FORMATTER_yMdH.format(date);
        } catch (Exception e) {
            dateStr = "";
        }
        return dateStr;
    }

    /**
     * 格式化时间
     *
     * @param time long
     * @return yyyy-MM-dd
     */
    public static String formateDate_yMd(long time) {
        String dateStr = null;
        try {
            Date date = new Date(time);
            dateStr = FORMATTER_yMd.format(date);
        } catch (Exception e) {
            dateStr = "";
        }
        return dateStr;
    }

    /**
     * 格式化时间
     *
     * @param date Date
     * @return yyyy-MM-dd
     */
    public static String formateDate_yMd(Date date) {
        String dateStr = null;
        try {
            dateStr = FORMATTER_yMd.format(date);
        } catch (Exception e) {
            dateStr = "";
        }
        return dateStr;
    }

    /**
     * 格式化时间
     *
     * @param time long
     * @return yyyyMMdd
     */
    public static String formateDateSub_yMd(long time) {
        String dateStr = null;
        try {
            Date date = new Date(time);
            dateStr = SUB_CONTENT_DATE_FORMAT.format(date);
        } catch (Exception e) {
            dateStr = "";
        }
        return dateStr;
    }

    /**
     * 格式化时间
     *
     * @param time long
     * @return yyyyMMdd
     */
    public static String formateDateSub_yMdHms(long time) {
        String dateStr = null;
        try {
            Date date = new Date(time);
            dateStr = SUB_CONTENT_DATE_FORMAT_yMdHms.format(date);
        } catch (Exception e) {
            dateStr = "";
        }
        return dateStr;
    }

    /**
     * 格式化时间
     *
     * @param date Date
     * @return yyyyMMdd
     */
    public static String formateDateSub_yMd(Date date) {
        String dateStr = null;
        try {
            dateStr = SUB_CONTENT_DATE_FORMAT.format(date);
        } catch (Exception e) {
            dateStr = "";
        }
        return dateStr;
    }

    /**
     * 格式化时间
     *
     * @param time long
     * @return yyyy-MM
     */
    public static String formateDate_yM(long time) {
        String dateStr = null;
        try {
            Date date = new Date(time);
            dateStr = FORMATER_yM.format(date);
        } catch (Exception e) {
            dateStr = "";
        }
        return dateStr;
    }

    /**
     * 格式化时间
     *
     * @param date Date
     * @return yyyy-MM
     */
    public static String formateDate_yM(Date date) {
        String dateStr = null;
        try {
            dateStr = FORMATER_yM.format(date);
        } catch (Exception e) {
            dateStr = "";
        }
        return dateStr;
    }

    /**
     * 格式化时间
     *
     * @param time long
     * @return MM-dd
     */
    public static String formateDate_Md(long time) {
        String dateStr = null;
        try {
            Date date = new Date(time);
            dateStr = FORMATTER_Md.format(date);
        } catch (Exception e) {
            dateStr = "";
        }
        return dateStr;
    }

    /**
     * 格式化时间
     *
     * @param date Date
     * @return MM-dd
     */
    public static String formateDate_Md(Date date) {
        String dateStr = null;
        try {
            dateStr = FORMATTER_Md.format(date);
        } catch (Exception e) {
            dateStr = "";
        }
        return dateStr;
    }

    /**
     * 格式化时间
     *
     * @param time long
     * @return yyyy
     */
    public static String formateDate_y(long time) {
        String dateStr = null;
        try {
            Date date = new Date(time);
            dateStr = FORMATTER_y.format(date);
        } catch (Exception e) {
            dateStr = "";
        }
        return dateStr;
    }

    /**
     * 格式化时间
     *
     * @param dateTimeStr yyyy-MM-dd HH:mm:ss
     * @return long
     */
    public static long formateString_long(String dateTimeStr) {
        long time = 0;
        try {
            Date date = FORMATTER_ZH.parse(dateTimeStr);
            time = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * @param timeStr yyyy-MM-dd HH:mm
     * @return
     */
    public static long formateStrTimeToTimestamp(String timeStr){
        long time = 0;
        try {
            Date date = FORMATTER_yMdHm.parse(timeStr);
            time = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }


    /**
     * 格式化时间
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss
     * @return date
     */
    public static Date formateString_date(String dateStr) {
        Date date = null;
        try {
            date = FORMATTER_ZH.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 格式化时间
     *
     * @param time long
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formateDate_yMdHms(long time) {
        String dateStr = null;
        try {
            Date date = new Date(time);
            dateStr = FORMATTER_yMdHms.format(date);
        } catch (Exception e) {
            dateStr = "";
        }
        return dateStr;
    }

    public static long getCurTime12(){
        return formateString_long(FORMATTER_ZH_12.format(Calendar.getInstance().getTime()));
    }


    /**
     * 得到UTC时间，类型为字符串，格式为"yyyy-MM-dd HH:mm"<br />
     * 如果获取失败，返回null
     * @return
     */
    public static String getUTCTimeStr() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm") ;
        StringBuffer UTCTimeBuffer = new StringBuffer();
        // 1、取得本地时间：
        Calendar cal = Calendar.getInstance() ;
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        UTCTimeBuffer.append(year).append("-").append(month).append("-").append(day) ;
        UTCTimeBuffer.append(" ").append(hour).append(":").append(minute) ;
        try{
            format.parse(UTCTimeBuffer.toString()) ;
            return UTCTimeBuffer.toString() ;
        }catch(ParseException e)
        {
            e.printStackTrace() ;
        }
        return null ;
    }

    /**
     * 将UTC时间转换为东八区时间
     * @param UTCTime
     * @return
     */
    public static String getLocalTimeFromUTC(String UTCTime){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'") ;
        java.util.Date UTCDate = null ;
        String localTimeStr = null ;
        try {
            UTCDate = format.parse(UTCTime);
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
            format.setTimeZone(TimeZone.getTimeZone("GMT-8")) ;
            localTimeStr = format.format(UTCDate) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return localTimeStr ;
    }

}
