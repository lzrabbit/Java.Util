package cn.lzrabbit.util;

import java.net.URI;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;

//http://stackoverflow.com/questions/277502/jaxb-how-to-ignore-namespace-during-unmarshalling-xml-document
public class NamespaceFilter extends XMLFilterImpl {
	private boolean ignoreNamespace;
	private String defaultNamespace;
	private boolean isRootElement = true;

	public NamespaceFilter() {
		super();
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		if (this.ignoreNamespace) uri = "";
		if (this.isRootElement) this.isRootElement = false;
		else if (!uri.equals("") && !localName.contains("xmlns")) localName = localName + " xmlns=\"" + uri + "\"";

		super.startElement(uri, localName, localName, atts);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (this.ignoreNamespace) uri = "";
		super.endElement(uri, localName, localName);
	}

	@Override
	public void startPrefixMapping(String prefix, String url) throws SAXException {
		if (this.defaultNamespace != null) url = this.defaultNamespace;
		if (!this.ignoreNamespace) super.startPrefixMapping("", url);

	}

	/**
	 * @return the ignoreNamespace
	 */
	public boolean isIgnoreNamespace() {
		return ignoreNamespace;
	}

	/**
	 * @param ignoreNamespace
	 *            the ignoreNamespace to set
	 */
	public void setIgnoreNamespace(boolean ignoreNamespace) {
		this.ignoreNamespace = ignoreNamespace;
	}

	public String getDefaultNamespace() {
		return defaultNamespace;
	}

	public void setDefaultNamespace(String defaultNamespace) {
		this.defaultNamespace = defaultNamespace;
	}
}
