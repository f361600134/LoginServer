package com.qlbs.Bridge.util;

import java.security.MessageDigest;

public final class MD5
{
  private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

  public static String digest(String txt)
  {
    try
    {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(txt.getBytes("UTF-8"));

      return toHex(md.digest());
    } catch (Exception e) {
    }
    return "";
  }

  public static String digest(String txt, boolean toLowerCase)
  {
    if (toLowerCase) {
      return digest(txt).toLowerCase();
    }
    return digest(txt);
  }

  private static String toHex(byte[] byteArray)
  {
    char[] resultCharArray = new char[byteArray.length * 2];
    int index = 0;
    for (byte b : byteArray) {
      resultCharArray[(index++)] = hexDigits[(b >>> 4 & 0xF)];
      resultCharArray[(index++)] = hexDigits[(b & 0xF)];
    }
    return new String(resultCharArray);
  }

}