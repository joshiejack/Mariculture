package mariculture.core.guide;

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

import mariculture.Mariculture;
import mariculture.plugins.compatibility.CompatBait;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLHelper {
	public Element e;
	public XMLHelper(Element element) {
		this.e = element;
	}
	
	public Element getElement() {
		return e;
	}
	
	public XMLHelper getHelper(String name) {
		Node n = e.getElementsByTagName(name).item(0);
		if(n == null)
			return this;
		if(n.getNodeType() == Node.ELEMENT_NODE) {
			return new XMLHelper((Element) n);
		}
		
		return this;
	}
	
	public String getAttribute(String name) {
		return e.getAttribute(name);
	}
	
	public String getOptionalAttribute(String name) {
		if(e.getAttribute(name) == null)
			return "";
		return e.getAttribute(name);
	}
	
	public boolean getAttribAsBoolean(String name) {
		if(e.getAttribute(name) == null)
			return false;
		return Boolean.parseBoolean(e.getAttribute(name));
	}
	
	public Integer getAttribAsInteger(String name, int dft) {
		String string = getAttribute(name);
		return string.equals("")? dft: Integer.parseInt(string);
	}
	
	public Integer getAttribAsHex(String name, int dft) {
		String string = getAttribute(name);
		return string.equals("")? dft: Integer.parseInt(string, 16);
	}
	
	public Float getAttribAsFloat(String name, float dft) {
		String string = getAttribute(name);
		return string.equals("")? dft: Float.parseFloat(string);
	}
	
	public String getElement(String name) {
		return e.getElementsByTagName(name).item(0).getTextContent();
	}
	
	public String getOptionalElement(String str) {
		if(e.getElementsByTagName(str) == null)
			return "";
		if(e.getElementsByTagName(str).item(0) == null)
			return "";
		return e.getElementsByTagName(str).item(0).getTextContent();
	}
	
	public Integer getElementAsInteger(String name, int dft) {
		String string = getElement(name);
		return string.equals("")? dft: Integer.parseInt(string);
	}
	
	public Integer getElementAsHex(String name, int dft) {
		String string = getElement(name);
		return string.equals("")? dft: Integer.parseInt(string, 16);
	}
	
	public Float getElementAsFloat(String name, float dft) {
		String string = getElement(name);
		return string.equals("")? dft: Float.parseFloat(string);
	}

	public String getSelf() {
		return e.getTextContent();
	}
}
