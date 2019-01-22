package com.qlbs.Bridge.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qlbs.Bridge.common.result.ErrorCodeEnum;
import com.qlbs.Bridge.common.result.IResult;
import com.qlbs.Bridge.common.result.support.SimpleResult;
import com.qlbs.Bridge.controller.Stu;

public class CommonUtil {

	public static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	/**
	 * 判断参数对象
	 * 
	 * @param params
	 * @return
	 * @return IResult
	 * @date 2018年9月25日上午10:22:32
	 */
	public static IResult checkParams(Object object) {
		Class<?> clazz = object.getClass();
		Set<Field> fieldSet = new HashSet<>();
		while (clazz != null) {
			fieldSet.addAll(Arrays.asList(clazz.getDeclaredFields()));
			clazz = clazz.getSuperclass(); // 得到父类,然后赋给自己
		}
		boolean bool = true;
		try {
			StringBuilder sb = new StringBuilder();
			for (Field field : fieldSet) {
				field.setAccessible(true);
				String name = field.getName();
				String obj = (String) field.get(object);
				sb.append(name).append("=").append(obj).append(", ");
				if (StringUtils.isBlank(obj)) {
					// 参数空,提示错误
					logger.info("参数为空, name:{}", name);
					bool = false;
				}
			}
			logger.info("参数信息, param:{}", sb.toString());
		} catch (Exception e) {
			bool = false;
			logger.error("校验参数异常, param:{}", object);
		}
		return bool ? SimpleResult.build(ErrorCodeEnum.IllEGAL_PARAMS) : null;
	}

	/**
	 * 实体类转Map共通方法
	 */
	public static Map<String, Object> convertBean(Object bean) throws Exception {
		Class<?> type = bean.getClass();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor descriptor : propertyDescriptors) {
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

	/**
	 * Map转实体类共通方法
	 * 
	 * @param type
	 * @param map
	 * @return
	 * @throws Exception
	 * @return Object
	 * @date 2019年1月9日上午10:23:03
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertMap(Class<T> type, Map<String, String[]> map) throws Exception {
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		Object obj = type.newInstance();
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor descriptor : propertyDescriptors) {
			String propertyName = descriptor.getName();
			Class<?> clazzType = descriptor.getPropertyType();
			if (map.containsKey(propertyName)) {
				String[] value = map.get(propertyName);
				// 基础类型和String类型
				if (isPrimitive(clazzType)) {
					descriptor.getWriteMethod().invoke(obj, ConvertUtils.convert(value[0], clazzType));
				} else {
					logger.info("propertyName:{} 找不到类型, clazzType:{}", propertyName, clazzType);
				}
			}
		}
		return (T) obj;
	}

	/**
	 * 判断是否为基础类型以及基础类型的封装类型
	 *
	 * @see java.lang.Boolean#TYPE
	 * @see java.lang.Character#TYPE
	 * @see java.lang.Byte#TYPE
	 * @see java.lang.Short#TYPE
	 * @see java.lang.Integer#TYPE
	 * @see java.lang.Long#TYPE
	 * @see java.lang.Float#TYPE
	 * @see java.lang.Double#TYPE
	 */
	public static boolean isPrimitive(Class<?> clz) {
		if ((clz == Boolean.TYPE || clz == Boolean.class)//
				|| (clz == Character.TYPE || clz == Character.class)//
				|| (clz == Integer.TYPE || clz == Integer.class)//
				|| (clz == Long.TYPE || clz == Long.class)//
				|| (clz == Byte.TYPE || clz == Byte.class)//
				|| (clz == Double.TYPE || clz == Double.class)//
				|| (clz == Short.TYPE || clz == Short.class) //
				|| (clz == Float.TYPE || clz == Float.class)//
				|| clz == String.class) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		// String aaa = "111";
		// Integer bbb = 222;
		// int ccc = 333;
		// // // System.out.println(ConvertUtils.convert(aaa, Integer.class));
		// System.out.println(isWrapClass(aaa.getClass()));
		// System.out.println(isWrapClass(bbb.getClass()));
		long start = System.currentTimeMillis();
		Map<String, String[]> map = new HashMap<>();
		map.put("id", new String[] { "1" });
		map.put("name", new String[] { "a" });
		map.put("age", new String[] { "1" });
		Stu stu = null;
		try {
			stu = CommonUtil.convertMap(Stu.class, map);
			System.out.println("1111:" + (System.currentTimeMillis() - start));
			System.out.println(stu);
			convertBean(stu);
			System.out.println("2222:" + (System.currentTimeMillis() - start));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("3333:" + (System.currentTimeMillis() - start));
		//
		// YouxifanIOSLoginParam param = new YouxifanIOSLoginParam();
		// param.setGameKey("agemkey1");
		// param.setUserId("userId1");
		// param.setQdCode1("1");
		// param.setQdCode2("2");
		// param.setToken("token1");
		// checkParams(param);
	}

	/**
	 * String to map
	 * 
	 * @param gameKeyMapStr
	 *            a:b,a1:b1
	 * @return
	 * @return Map<String,String>
	 * @date 2018年9月25日下午3:14:04
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map strToMap(String str, Class<? extends Map> mapClass) {
		try {
			Map map = mapClass.newInstance();
			if (!StringUtils.isBlank(str)) {
				String gameKeyMapArr[] = str.split(",");
				for (String gameKeyStr : gameKeyMapArr) {
					String gameKeyArr[] = gameKeyStr.split(":");
					map.put(gameKeyArr[0], gameKeyArr[1]);
				}
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把一个对象解析成json
	 * 
	 * @param obj
	 * @param extract
	 *            除去需要解析的字段
	 * @return
	 * @return Map<String,String>
	 * @date 2018年9月25日下午4:39:10
	 */
	public static Map<String, String> objToTreeMap(Object obj, String... extract) {
		Map<String, String> sortMap = new TreeMap<String, String>(new Comparator<String>() {
			@Override
			public int compare(String str1, String str2) {
				return str1.compareTo(str2);
			}
		});
		JSONObject jsonObject = (JSONObject) JSON.toJSON(obj);
		Set<Entry<String, Object>> entrySet = jsonObject.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String key = entry.getKey();
			String value = String.valueOf(entry.getValue());
			if (StringUtils.containsAny(key, extract)) {
				continue;
			}
			sortMap.put(key, value);
		}
		return sortMap;
	}

	/**
	 * map中的数据组装后生成md5 签名
	 * 
	 * @style key1=value1&key2=value2
	 * @param map
	 * @return
	 */
	public static String getMySignByMap(Map<String, String> map, String... extras) {
		StringBuilder url = new StringBuilder();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			url.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		url.deleteCharAt(url.length() - 1);
		for (String extra : extras) {
			url.append(extra);
		}
		// md5加密
		String mySign = MD5.digest(url.toString(), true);
		return mySign;
	}

	/**
	 * 使用 Map按key进行排序
	 * 
	 * @param map
	 * @return
	 */
	public static Map<String, String> sortMapByKey(Map<String, String> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Map<String, String> sortMap = new TreeMap<String, String>(new Comparator<String>() {
			@Override
			public int compare(String str1, String str2) {
				return str1.compareTo(str2);
			}
		});
		sortMap.putAll(map);
		return sortMap;
	}

}
