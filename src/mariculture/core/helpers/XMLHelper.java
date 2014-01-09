package mariculture.core.helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import mariculture.core.Mariculture;
import mariculture.plugins.compatibility.CompatBait;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLHelper {
	public Element e;
	public XMLHelper(Element element) {
		this.e = element;
	}

	public String toIdent(String str) {
		return e.getAttribute(str);
	}

	public String toString(String str) {
		return e.getElementsByTagName(str).item(0).getTextContent();
	}

	public Integer toInt(String str) {
		return Integer.parseInt(e.getElementsByTagName(str).item(0).getTextContent());
	}
}
