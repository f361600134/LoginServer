package com.qlbs.Bridge.util;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlUtil {

	public final static Logger logger = LoggerFactory.getLogger(UrlUtil.class.getName());

	/**
	 * 获取ip地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress = null;
		try {
			String ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
				ip = request.getRemoteAddr();
			}

			ipAddress = request.getHeader("x-forwarded-for");
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
				if (ipAddress.equals("127.0.0.1")) {
					// 根据网卡取本机配置的IP
					InetAddress inet = null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					ipAddress = inet.getHostAddress();
				}
			}
			// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
			if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
																// = 15
				if (ipAddress.indexOf(",") > 0) {
					ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
				}
			}
		} catch (Exception e) {
			ipAddress = "";
		}
		// ipAddress = this.getRequest().getRemoteAddr();
		return ipAddress;
	}

	public static String decode(String src, String enc) {
		if (src == null)
			return null;
		if ("".equals(src)) {
			return "";
		}
		String result = null;
		try {
			result = URLDecoder.decode(src, enc);
		} catch (Exception e) {
			logger.error("UrlUtil.decode error!", e);
		}
		return result;
	}

	public static String encode(String src, String enc) {
		if (src == null)
			return null;
		if ("".equals(src)) {
			return "";
		}
		String result = null;
		try {
			result = URLEncoder.encode(src, enc);
		} catch (Exception e) {
			logger.error("UrlUtil.encode error!", e);
		}
		return result;
	}

	public static String utf8Decode(String src) {
		return decode(src, "UTF-8");
	}

	public static String utf8Encode(String src) {
		return encode(src, "UTF-8");
	}

	public static String decode(String src) {
		String txt = src;
		txt = txt.replaceAll("_REP_", "\\=");
		txt = txt.replaceAll("_RAP_", "\\+");
		txt = txt.replaceAll("_RBP_", "\\-");
		txt = txt.replaceAll("_RCP_", "\\\\");
		try {
			return new String(Base64.decodeBase64(txt), "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("", e);
		}
		return "";
	}

	public static String encode(String src) {
		String encodeBase64String = null;
		try {
			encodeBase64String = Base64.encodeBase64String(src.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("", e);
		}
		encodeBase64String = encodeBase64String.replaceAll("\\=", "_REP_");
		encodeBase64String = encodeBase64String.replaceAll("\\+", "_RAP_");
		encodeBase64String = encodeBase64String.replaceAll("\\-", "_RBP_");
		encodeBase64String = encodeBase64String.replaceAll("\\\\", "_RCP_");
		return encodeBase64String;
	}

	public static boolean isBase64Encode(String src) {
		return Base64.isBase64(src);
	}
}