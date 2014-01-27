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
		if(n.getNodeType() == Node.ELEMENT_NODE) {
			return new XMLHelper((Element) n);
		}
		
		return this;
	}
	
	public String getAttribute(String name) {
		return e.getAttribute(name);
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
	
	/** Deprecate below methods **/

	@Deprecated
	public String getAttribString(String str) {
		return e.getAttribute(str);
	}
	
	//Heading Size
	@Deprecated
	public Float getHSize(String str) {
		return getSize(str, 1F);
	}
	
	//Text Size
	@Deprecated
	public Float getTSize(String str) {
		return getSize(str, 0.85F);
	}
	
	//Crafting Size
	@Deprecated
	public Float getCSize(String str) {
		return getSize(str, 1.5F);
	}
	
	//Any String returns the default float if not valid number
	@Deprecated
	public Float getSize(String str, Float dft) {
		String flt = e.getAttribute(str);
		if(flt.equals(""))
			return dft;
		return Float.parseFloat(flt);
	}
	
	//Returns the offet, defaults to 0
	@Deprecated
	public Integer getOffset(String str) {
		return getInteger(str, 0);
	}
	
	//Returns the wrap amount, defaults to 210
	@Deprecated
	public Integer getWrap(String str) {
		return getInteger(str, 210);
	}
	
	@Deprecated
	public Integer getHOffset(String str) {
		return getInteger(str, -13);
	}
	
	@Deprecated
	public Integer getInteger(String str, int dft) {
		String integer = e.getAttribute(str);
		if(integer.equals(""))
			return dft;
		return Integer.parseInt(integer);
	}
	
	@Deprecated
	public Integer getAttribInt(String str) {
		return Integer.parseInt(e.getAttribute(str));
	}

	@Deprecated
	public String getElementString(String str) {
		if(e.getElementsByTagName(str) == null)
			return "";
		if(e.getElementsByTagName(str).item(0) == null)
			return "";
		return e.getElementsByTagName(str).item(0).getTextContent();
	}

	@Deprecated
	public Integer getElementInt(String str) {
		return Integer.parseInt(e.getElementsByTagName(str).item(0).getTextContent());
	}
	
	@Deprecated
	public Integer getHexCode(String str) {
		return Integer.parseInt(e.getElementsByTagName(str).item(0).getTextContent(), 16);
	}

	@Deprecated
	public String getMod() {
		if(getAttribString("mod").equals(""))
			return "mariculture";
		return getAttribString("mod");
	}
}
