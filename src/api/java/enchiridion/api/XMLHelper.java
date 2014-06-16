package enchiridion.api;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLHelper {
	public static String getAttribute(Element e, String name) {
		if(e.getAttribute(name) == null) return "";
		return e.getAttribute(name);
	}
	
	public static Boolean getAttribAsBoolean(Element e, String name) {
		return getAttribAsBoolean(e, name, false);
	}
	
	public static Boolean getAttribAsBoolean(Element e, String name, Boolean dft) {
		if(getAttribute(e, name) == null) return dft;
		else return Boolean.parseBoolean(getAttribute(e, name));
	}
	
	public static Integer getAttribAsInteger(Element e, String name, Integer dft) {
		String num = getAttribute(e, name);
		return num.equals("")? dft: Integer.parseInt(num);
	}
	
	public static Float getAttribAsFloat(Element e, String name, Float dft) {
		String num = getAttribute(e, name);
		return num.equals("")? dft: Float.parseFloat(num);
	}
	
	public static Integer getAttribAsHex(Element e, String name, Integer dft) {
		String num = getAttribute(e, name);
		return num.equals("")? dft: Integer.parseInt(num, 16);
	}
	
	public static String getElement(Element e, String name) {
		if(e.getElementsByTagName(name) == null) return "";
		if(e.getElementsByTagName(name).item(0) == null) return "";
		return e.getElementsByTagName(name).item(0).getTextContent();
	}
	
	public static Boolean getElementAsBoolean(Element e, String name) {
		return getElementAsBoolean(e, name, false);
	}
	
	public static Boolean getElementAsBoolean(Element e, String name, Boolean dft) {
		if(getElement(e, name) == null) return dft;
		else return Boolean.parseBoolean(getElement(e, name));
	}
	
	public static Integer getElementAsInteger(Element e, String name, Integer dft) {
		String num = getElement(e, name);
		return num.equals("")? dft: Integer.parseInt(num);
	}
	
	public static Float getElementAsFloat(Element e, String name, Float dft) {
		String num = getElement(e, name);
		return num.equals("")? dft: Float.parseFloat(num);
	}
	
	public static Integer getElementAsHex(Element e, String name, Integer dft) {
		String num = getElement(e, name);
		return num.equals("")? dft: Integer.parseInt(num, 16);
	}
	
	public static Element getNode(Element e, String name) {
		Node n = e.getElementsByTagName(name).item(0);
		if(n == null) return e;
		else if(n.getNodeType() == Node.ELEMENT_NODE) {
			return (Element) n;
		} else return e;
	}
	
	public static String getSelf(Element e) {
		return e.getTextContent();
	}
}
