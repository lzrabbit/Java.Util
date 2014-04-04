package cn.lzrabbit.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import com.sun.xml.bind.v2.WellKnownNamespace;

public class SerializeUtil {
	static final ObjectMapper mapper = new ObjectMapper();

	public static String toJson(Object obj) {
		if (obj == null) return null;
		String json;
		try {
			json = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		// System.out.println(json);
		return json;
	}

	public static <T> T fromJson(String content, Class<T> valueType) throws Exception {
		if (content == null) return null;
		return mapper.readValue(content, valueType);
	}

	public static String toXML(Object obj) {

		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());

			// 下面代码演示将对象转变为xml
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// //编码格式
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);// 是否格式化生成的xml串
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xm头声明信息
//			 StringWriter writer = new StringWriter();
//			 marshaller.marshal(obj, writer);
//			 return writer.toString();

			// marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapper() {
			// @Override
			// public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
			// System.out.println(String.format("namespaceUri:%s suggestion:%s requirePrefix:%s", namespaceUri, suggestion, requirePrefix));
			// // if (namespaceUri.equals(WellKnownNamespace.XML_NAMESPACE_URI)) return "xsi";
			// // if (namespaceUri.equals(WellKnownNamespace.XML_SCHEMA)) return "xs";
			// // if (namespaceUri.equals(WellKnownNamespace.XML_MIME_URI)) return "xmime";
			// // if(namespaceUri.contains("abc")) return "abc";
			// // if(namespaceUri.contains("tuan")) return "tuan";
			// return suggestion;
			// }
			// });

			StringWriter out = new StringWriter();
			OutputFormat format = new OutputFormat();
			format.setIndent(true);
			format.setNewlines(true);
			format.setNewLineAfterDeclaration(false);
			XMLWriter writer = new XMLWriter(out, format);
			NamespaceFilter nsfFilter = new NamespaceFilter();
			nsfFilter.setDefaultNamespace("xxoo.com");
			nsfFilter.setContentHandler(writer);
			marshaller.marshal(obj, nsfFilter);
			return out.toString();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T fromXML(String xml, Class<T> valueType) {
		// JAXBContext context = JAXBContext.newInstance(valueType);
		// Unmarshaller unmarshaller = context.createUnmarshaller();
		// SAXParserFactory sax = SAXParserFactory.newInstance();
		// sax.setNamespaceAware(false);
		// XMLReader xmlReader = sax.newSAXParser().getXMLReader();
		// StringReader reader = new StringReader(xml);
		// SAXSource source = new SAXSource(xmlReader, new InputSource(reader));
		// return (T) unmarshaller.unmarshal(source);
		try {
			JAXBContext context = JAXBContext.newInstance(valueType);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			// return (T) unmarshaller.unmarshal(new StringReader(xml));

			XMLReader reader = XMLReaderFactory.createXMLReader();
			NamespaceFilter nsfFilter = new NamespaceFilter();
			nsfFilter.setIgnoreNamespace(true);
			nsfFilter.setParent(reader);
			InputSource input = new InputSource(new StringReader(xml));
			SAXSource source = new SAXSource(nsfFilter, input);
			return (T) unmarshaller.unmarshal(source);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
