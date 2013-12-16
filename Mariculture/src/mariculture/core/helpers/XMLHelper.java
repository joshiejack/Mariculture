package mariculture.core.helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mariculture.core.Mariculture;
import mariculture.plugins.compatibility.CompatBait;

import org.w3c.dom.Element;

public class XMLHelper {
	public Element e;
	public File file;
	
	public XMLHelper(Element element) {
		this.e = element;
	}
	
	public XMLHelper(String str) {
		File file = new File(Mariculture.root + "/mariculture/", str + ".xml");
		if(!file.exists()) {
			try {
				InputStream stream = CompatBait.class.getResourceAsStream("/mariculture/xml/" + str + ".xml");
			    OutputStream resStreamOut = null;
			    int readBytes;
			    byte[] buffer = new byte[4096];
			    try {
			        resStreamOut = new FileOutputStream(file);
			        while ((readBytes = stream.read(buffer)) > 0) {
			            resStreamOut.write(buffer, 0, readBytes);
			        }
			    } catch (IOException e) {
			        e.printStackTrace();
			    } finally {
			        stream.close();
			        resStreamOut.close();
			    }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		this.file = file;
	}
	
	public File get() {
		return this.file;
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
