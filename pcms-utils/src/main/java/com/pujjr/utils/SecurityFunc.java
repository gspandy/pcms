package com.pujjr.utils;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SecurityFunc{
	//3DES
	private static final String Algorithm = "DESede"; //定义 加密算法,可用 DES,DESede,Blowfish
	
	
	private static byte[] encryptMode(byte[] keybyte, byte[] src) 
	{
	       try {
	            //生成密钥
	            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

	            //加密
	            Cipher c1 = Cipher.getInstance(Algorithm);
	            c1.init(Cipher.ENCRYPT_MODE, deskey);
	            return c1.doFinal(src);
	        } catch (java.security.NoSuchAlgorithmException e1) {
	            e1.printStackTrace();
	        } catch (javax.crypto.NoSuchPaddingException e2) {
	            e2.printStackTrace();
	        } catch (java.lang.Exception e3) {
	            e3.printStackTrace();
	        }
	        return null;
	 }
	 //keybyte为加密密钥，长度为24字节
    //src为加密后的缓冲区
    private  static byte[] decryptMode(byte[] keybyte, byte[] src) {      
    try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            //解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }
    
    public static String do3DesEncrypt(String src)
    {
    	Security.addProvider(new com.sun.crypto.provider.SunJCE());

        final byte[] keyBytes = {0x11, 0x22, 0x4F, 0x58, (byte)0x88, 0x10, 0x40, 0x38
                               , 0x28, 0x25, 0x79, 0x51, (byte)0xCB, (byte)0xDD, 0x55, 0x66
                               , 0x77, 0x29, 0x74, (byte)0x98, 0x30, 0x40, 0x36, (byte)0xE2};    //24字节的密钥
        
        byte[] encoded = encryptMode(keyBytes, src.getBytes());     
        
        return Utils.bytesToHexString(encoded);
    }
    
    public static String do3DesDecrypt(String src)
    {
    	Security.addProvider(new com.sun.crypto.provider.SunJCE());

        final byte[] keyBytes = {0x11, 0x22, 0x4F, 0x58, (byte)0x88, 0x10, 0x40, 0x38
                               , 0x28, 0x25, 0x79, 0x51, (byte)0xCB, (byte)0xDD, 0x55, 0x66
                               , 0x77, 0x29, 0x74, (byte)0x98, 0x30, 0x40, 0x36, (byte)0xE2};    //24字节的密钥
        
        byte[] srcBytes = decryptMode(keyBytes, Utils.hexStringToBytes(src));     
        
        return new String(srcBytes);
    }
    
    

}
