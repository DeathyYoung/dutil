package com.deathyyoung.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * @ClassName: JAXBUtil
 * @Description: xml工具类
 * @date 2014-9-28
 * 
 */
public class JAXBUtil {

	// private static final Log logger = LogFactory.getLog(JAXBUtil.class);
	private static Map<String, JAXBContext> map = new ConcurrentHashMap<String, JAXBContext>();

	/**
	 * 
	 * @param clazz
	 *            xml绑定的对象的类
	 * @param object
	 *            xml绑定的对象
	 * @return 解析后字符编码为UTF-8的xml字符串
	 * @throws Exception
	 */
	public static <T> String toXml(Class<T> clazz, Object object)
			throws Exception {
		// 组装xml
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Marshaller marshaller = null;
		JAXBContext context = null;
		String returnString = null;
		try {
			try {
				context = map.get(clazz.toString());
				if (context == null) {
					context = JAXBContext.newInstance(clazz);
					map.put(clazz.toString(), context);
				}
				marshaller = context.createMarshaller();
				// marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
				// Boolean.TRUE);
				// marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
				marshaller.marshal(object, os);
			} catch (Exception e) {
				// logger.error("组装消息报文错误：" + e.getMessage());
				// logger.error(e);
				throw e;
			}

			// 转换为UTF-8的字符串
			if (os != null) {
				returnString = os.toString("UTF-8");
			}
		} finally {
			marshaller = null;
			if (os != null) {
				os.close();
			}
			os = null;
		}
		return returnString;

	}

	/**
	 * 
	 * @param clazz
	 *            xml绑定的对象的类
	 * @param xmlString
	 *            字符编码为UTF-8的xml字符串
	 * @return 返回xml绑定的对象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toObject(Class<T> clazz, String xmlString) {
		Unmarshaller unmarshaller = null;
		JAXBContext context = null;
		Object returnObject = null;
		ByteArrayInputStream stream = null;
		try {
			stream = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
			context = map.get(clazz.toString());
			if (context == null) {
				context = JAXBContext.newInstance(clazz);
				map.put(clazz.toString(), context);
			}
			unmarshaller = context.createUnmarshaller();
			returnObject = unmarshaller.unmarshal(stream);
		} catch (Exception e) {
			// logger.error("解析消息报文错误：" + e.getMessage());
			// logger.error(e);
		} finally {
			unmarshaller = null;
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			stream = null;
		}
		return (T) returnObject;
	}
}
