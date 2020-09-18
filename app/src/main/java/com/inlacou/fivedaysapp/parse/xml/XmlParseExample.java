package com.inlacou.fivedaysapp.parse.xml;

import com.inlacou.fivedaysapp.business.Pokemon;

import net.jodah.xsylum.XmlDocument;
import net.jodah.xsylum.XmlElement;
import net.jodah.xsylum.Xsylum;
import net.jodah.xsylum.XsylumException;

public class XmlParseExample {
	
	private static String EXAMPLE_XML = "<Pokemon>" +
			"   <id>1</id>" +
			"   <name>Bulbasaur</name>" +
			"       <types>" +
			"       <typeSlot>" +
			"           <slot>1</slot>" +
			"           <type>" +
			"               <name>\"grass\"</name>" +
			"               <url>\"https://etc\"</url>" +
			"           </type>" +
			"       </typeSlot>" +
			"       <typeSlot>" +
			"           <slot>2</slot>" +
			"           <type>" +
			"               <name>\"poison\"</name>" +
			"               <url>\"https://etc\"</url>" +
			"           </type>" +
			"       </typeSlot>" +
			"   </types>" +
			"</Pokemon>";
	
	public static Pokemon parse() throws XsylumException {
		XmlDocument document = Xsylum.documentFor(EXAMPLE_XML);
		XmlElement element = document.root();
		return new Pokemon(Integer.parseInt(element.get("id").value()), element.get("name").value(), 0);
	}
	
}
