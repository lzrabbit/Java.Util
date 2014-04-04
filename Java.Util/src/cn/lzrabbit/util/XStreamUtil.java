package cn.lzrabbit.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class XStreamUtil {
	public static String toXML(Object obj) {
		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);
		xstream.alias(obj.getClass().getSimpleName(), obj.getClass());
		return xstream.toXML(obj);
	}

	@SuppressWarnings("unchecked")
	public static <T> T fromXML(String xml) {
		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);
		return (T) xstream.fromXML(xml);

	}

}
