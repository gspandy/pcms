package com.pujjr.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
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
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;



public class Utils {
	
	public static int seq=0;
	/**
	 * ��ȡָ���������ڵ�����
	 * tom 2016��10��28��
	 */
	public static String getDayOfMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_MONTH)+"";
	}
	
	/**
    * ���ֽ��ת���Ĵ�д
    * @param money ���ֽ��
    * @return ���Ĵ�д���
    */
    public static String number2Chn(double money) {
    	/**
	     * ���������ִ�д
	     */
	    final String[] CN_UPPER_NUMBER = { "��", "Ҽ", "��", "��", "��",
	            "��", "½", "��", "��", "��" };
	    /**
	     * �����л��ҵ�λ��д�����������������ռλ��
	     */
	    final String[] CN_UPPER_MONETRAY_UNIT = { "��", "��", "Ԫ",
	            "ʰ", "��", "Ǫ", "��", "ʰ", "��", "Ǫ", "��", "ʰ", "��", "Ǫ", "��", "ʰ",
	            "��", "Ǫ" };
	    /**
	     * �����ַ�����
	     */
	    final String CN_FULL = "��";
	    /**
	     * �����ַ�����
	     */
	    final String CN_NEGATIVE = "��";
	    /**
	     * ���ľ��ȣ�Ĭ��ֵΪ2
	     */
	    final int MONEY_PRECISION = 2;
        /**
        * �����ַ�����Ԫ��
        */
        final String CN_ZEOR_FULL = "��Ԫ" + CN_FULL;
    	
    	BigDecimal numberOfMoney = new BigDecimal(money);
        StringBuffer sb = new StringBuffer();
        // -1, 0, or 1 as the value of this BigDecimal is negative, zero, or
        // positive.
        int signum = numberOfMoney.signum();
        // ��Ԫ�������
        if (signum == 0) {
            return CN_ZEOR_FULL;
        }
        //�������н�����������
        long number = numberOfMoney.movePointRight(MONEY_PRECISION)
                .setScale(0, 4).abs().longValue();
        // �õ�С�������λֵ
        long scale = number % 100;
        int numUnit = 0;
        int numIndex = 0;
        boolean getZero = false;
        // �ж������λ����һ�������������00 = 0, 01 = 1, 10, 11
        if (!(scale > 0)) {
            numIndex = 2;
            number = number / 100;
            getZero = true;
        }
        if ((scale > 0) && (!(scale % 10 > 0))) {
            numIndex = 1;
            number = number / 10;
            getZero = true;
        }
        int zeroSize = 0;
        while (true) {
            if (number <= 0) {
                break;
            }
            // ÿ�λ�ȡ�����һ����
            numUnit = (int) (number % 10);
            if (numUnit > 0) {
                if ((numIndex == 9) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
                }
                if ((numIndex == 13) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
                }
                sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                getZero = false;
                zeroSize = 0;
            } else {
                ++zeroSize;
                if (!(getZero)) {
                    sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                }
                if (numIndex == 2) {
                    if (number > 0) {
                        sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                    }
                } else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                }
                getZero = true;
            }
            // ��numberÿ�ζ�ȥ�����һ����
            number = number / 10;
            ++numIndex;
        }
        // ���signum == -1����˵�����������Ϊ������������ǰ��׷�������ַ�����
        if (signum == -1) {
            sb.insert(0, CN_NEGATIVE);
        }
        // ���������С�������λΪ"00"���������Ҫ�����׷�������ַ�����
        if (!(scale > 0)) {
            sb.append(CN_FULL);
        }
        return sb.toString();
    }
	
	
	/**
	 * �ݹ����и���field
	 * @param obj ��ǰ�ݹ����
	 * @param fieldList ����field�б�
	 */
	public static void getField(Class obj,List<Field> fieldList){
		Field[] fields = obj.getDeclaredFields();
		if(!obj.getName().equals("java.lang.Object")){
			for (Field field : fields) {
				field.setAccessible(true);
				fieldList.add(field);
			}
			Utils.getField(obj.getSuperclass(), fieldList);
		}
	}
	/**
	 * ��ȡ��������field
	 * @param obj
	 * @return
	 */
	public static List<Field> getFieldList(Class obj){
		List<Field> fieldList = new LinkedList<Field>();
		Utils.getField(obj, fieldList);
		return fieldList;
	}
	
	/**
	 * @param date ��������
	 * @param interval ���������ʾ����5��5���Ժ�;-6:6����ǰ
	 * @return ���������
	 */
	public static Date getDateAfterDay(Date date,int interval){
		String afterYear = "";
		Calendar calender = Calendar.getInstance();
		calender.add(Calendar.DAY_OF_MONTH, interval);
		return calender.getTime();
	}
	/**
	 * @param date ��������
	 * @param interval ����·ݣ�ʾ����5��5�����Ժ�;-6:6������ǰ
	 * @return ���������
	 */
	public static Date getDateAfterMonth(Date date,int interval){
		String afterYear = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, interval);
		return calendar.getTime();
	}
	/**
	 * @param date ��������
	 * @param interval �����ݣ�ʾ����5��5���Ժ�;-6:6����ǰ
	 * @return ���������
	 */
	public static Date getDateAfterYear(Date date,int interval){
		String afterYear = "";
		Calendar calender = Calendar.getInstance();
		calender.add(Calendar.YEAR, interval);
		return calender.getTime();
	}
	
	/**
	 * �������Կ���
	 * @param source ����Դ����
	 * @param target Ŀ�����
	 * @author pujjr 2016-10-09
	 */
	public static void copyProperties(Object source, Object target){
		if(source != null)
			BeanUtils.copyProperties(source, target);
		else
			target = null;
	}
	
	public static String convertStr2Utf8(String value) throws UnsupportedEncodingException
	{
		if(value!=null)
		{
			value=new String(value.getBytes("ISO8859-1"),"UTF-8");
		}
		return value;
	}
	
	/**
	 * ����null����
	 * @param obj
	 * @return
	 */
	public static String nullFilter(Object obj){
		return obj == null?"":obj.toString();
	}
	
	/**
	 * ��������ת��Ϊ��Ӧ���ݱ����� 
	 * @param propName  �����ʽ��"myUserName"
	 * @return ���ظ�ʽ����MY_USER_NAME
	 */
	public static String field2Col(String propName){
//		System.out.println("��������ת��ǰ��"+propName);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < propName.length(); i++) {
			char c = propName.charAt(i);
			if(Character.isUpperCase(c)){
				sb.append("_"+Character.toLowerCase(c));
			}else{
				sb.append(c);
			}
		}
		return sb.toString().toUpperCase();
	}
	
	/**
	 * ��ȡ��������
	 * **/
	public static int getSpaceDay(Date beginDate,Date endDate)
	{
		return (int) ((endDate.getTime()-beginDate.getTime())/(24*60*60*1000)+1);
	}
	/**�Ƚ�ʱ���С**/
	public static int compareDateTime(Date beginDate,Date endDate)
	{
		return (int)(endDate.getTime()-beginDate.getTime());
	}
	/**
	 * ����ָ����ʽ��ȡ��ǰ����
	 * **/
	public static String getCurrentTime(String format)
	{
		if(format==null||format==""||format.length()==0)
		{
			format="yyyyMMddHHmmss";
		}
		SimpleDateFormat df = new SimpleDateFormat(format);//�������ڸ�ʽ
		return df.format(new Date());
	}
	/**
	 * �������ڻ�ȡ���
	 * **/
	public static String getYear(Date date)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy");//�������ڸ�ʽ
		return df.format(date);
	}
	/**
	 * �������ڻ�ȡ�·�
	 * **/
	public static String getMonth(Date date)
	{
		SimpleDateFormat df = new SimpleDateFormat("MM");//�������ڸ�ʽ
		return df.format(date);
	}
	/**
	 * ����ת�ַ���
	 * **/
	public static String getFormatDate(Date date,String format)
	{
		SimpleDateFormat df = new SimpleDateFormat(format);//�������ڸ�ʽ
		return df.format(date);
	}
	/**
	 * ������ݻ�ȡ��������
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
	 * 8�����ַ���ת���ڸ�ʽ
	 * @throws ParseException 
	 * **/
	public static Date str82date(String date) throws ParseException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//�������ڸ�ʽ
		return df.parse(date);
	}
	public static Timestamp str2time(String time) throws ParseException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");//�������ڸ�ʽ
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
	 * Convert byte[] to hex string.�������ǿ��Խ�byteת����int��Ȼ������Integer.toHexString(int)��ת����16�����ַ����� 
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
	 * ��¡List
	 * @param src  ԴList
	 * **/
	public static List<Object> cloneList(List<Object> src)
	{
		List<Object> dest =new ArrayList<Object>(Arrays.asList(new Object[src.size()]));
		Collections.copy(dest, src);
		return dest;
	}
	
	
}
