package com.pujjr.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;



public class Utils {
	
	public static int seq=0;
	
	public static String convertStr2Utf8(String value) throws UnsupportedEncodingException
	{
		if(value!=null)
		{
			value=new String(value.getBytes("ISO8859-1"),"UTF-8");
		}
		return value;
	}
	/**
	 * 获取日期天数
	 * **/
	public static int getSpaceDay(Date beginDate,Date endDate)
	{
		return (int) ((endDate.getTime()-beginDate.getTime())/(24*60*60*1000)+1);
	}
	/**
	 * 按照指定格式获取当前日期
	 * **/
	public static String getCurrentTime(String format)
	{
		if(format==null||format==""||format.length()==0)
		{
			format="yyyyMMddHHmmss";
		}
		SimpleDateFormat df = new SimpleDateFormat(format);//设置日期格式
		return df.format(new Date());
	}
	/**
	 * 根据日期获取年份
	 * **/
	public static String getYear(Date date)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy");//设置日期格式
		return df.format(date);
	}
	/**
	 * 根据日期获取月份
	 * **/
	public static String getMonth(Date date)
	{
		SimpleDateFormat df = new SimpleDateFormat("MM");//设置日期格式
		return df.format(date);
	}
	/**
	 * 日期转字符串
	 * **/
	public static String getFormatDate(Date date,String format)
	{
		SimpleDateFormat df = new SimpleDateFormat(format);//设置日期格式
		return df.format(date);
	}
	/**
	 * 根据年份获取当年天数
	 * **/
	public static int getYearDays(String year)
	{
		if((Integer.valueOf(year)%4==0&&Integer.valueOf(year)/100!=0)||(Integer.valueOf(year)/400==0))
		{
			return 366;
		}
		else
		{
			return 365;
		}
	}
	/**
	 * 8日期字符串转日期格式
	 * @throws ParseException 
	 * **/
	public static Date str82date(String date) throws ParseException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
		return df.parse(date);
	}
	public static Timestamp str2time(String time) throws ParseException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");//设置日期格式
		return new Timestamp((df.parse(time)).getTime());
	}
	public static String get16UUID()
	{
		String uuid=UUID.randomUUID().toString();
		byte[] outputByteArray;
		 try {
			MessageDigest messageDigest =MessageDigest.getInstance("MD5");
			byte[] inputByteArray=uuid.getBytes();
			messageDigest.update(inputByteArray);
			outputByteArray=messageDigest.digest();
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} 
		 StringBuffer buf = new StringBuffer("");
         for (int offset = 0; offset < outputByteArray.length; offset++) {
            int  i = outputByteArray[offset];
             if (i < 0)
                 i += 256;
             if (i < 16)
                 buf.append("0");
             buf.append(Integer.toHexString(i));
         }
        return buf.toString().substring(8,24);
	}
	
	public static String getFileSuffix(String fileName)
	{
		int indexSuffix;
		if((indexSuffix=fileName.lastIndexOf("."))!=-1)
		{
			return fileName.substring(indexSuffix, fileName.length());
		}
		return fileName;
	}
	
	/** 
	 * Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。 
	 * @param src byte[] data 
	 * @return hex string 
	 */     
	public static String bytesToHexString(byte[] src){  
	    StringBuilder stringBuilder = new StringBuilder("");  
	    if (src == null || src.length <= 0) {  
	        return null;  
	    }  
	    for (int i = 0; i < src.length; i++) {  
	        int v = src[i] & 0xFF;  
	        String hv = Integer.toHexString(v);  
	        if (hv.length() < 2) {  
	            stringBuilder.append(0);  
	        }  
	        stringBuilder.append(hv);  
	    }  
	    return stringBuilder.toString();  
	}  
	/** 
	 * Convert hex string to byte[] 
	 * @param hexString the hex string 
	 * @return byte[] 
	 */  
	public static byte[] hexStringToBytes(String hexString) {  
	    if (hexString == null || hexString.equals("")) {  
	        return null;  
	    }  
	    hexString = hexString.toUpperCase();  
	    int length = hexString.length() / 2;  
	    char[] hexChars = hexString.toCharArray();  
	    byte[] d = new byte[length];  
	    for (int i = 0; i < length; i++) {  
	        int pos = i * 2;  
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
	    }  
	    return d;  
	}  
	/** 
	 * Convert char to byte 
	 * @param c char 
	 * @return byte 
	 */  
	 private static byte charToByte(char c) {  
	    return (byte) "0123456789ABCDEF".indexOf(c);  
	}  
	/**
	 * 克隆List
	 * @param src  源List
	 * **/
	public static List<Object> cloneList(List<Object> src)
	{
		List<Object> dest =new ArrayList<Object>(Arrays.asList(new Object[src.size()]));
		Collections.copy(dest, src);
		return dest;
	}
	
	
}
