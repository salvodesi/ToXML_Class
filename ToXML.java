package com.services.convert.toXML;

import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class ToXML {

	private DocumentBuilderFactory docFactory = null;
	private DocumentBuilder docBuilder = null;
	private Document doc = null;
	
	public ToXML(ResultSet result) throws Exception {
		// TODO Auto-generated constructor stub
	}
	
	public StringWriter convertXML(ResultSet rs) throws Exception{
		this.docFactory =  DocumentBuilderFactory.newInstance();
		try {
			this.docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.doc = docBuilder.newDocument();
		Element results = doc.createElement("Results");
	    doc.appendChild(results);
	    
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int colCount = rsmd.getColumnCount();
	    
	    while (rs.next()) {
	        Element row = doc.createElement("Row");
	        results.appendChild(row);
	        for (int i = 1; i <= colCount; i++) {
	          String columnName = rsmd.getColumnName(i);
	          Object value = rs.getObject(i);
	          Element node = doc.createElement(columnName);
	          node.appendChild(doc.createTextNode(value.toString()));
	          row.appendChild(node);
	        }
	      }
	    
	    DOMSource domSource = new DOMSource(doc);
	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = tf.newTransformer();
	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
	    StringWriter sw = new StringWriter();
	    StreamResult sr = new StreamResult(sw);
	    transformer.transform(domSource, sr);

	    return sw;
	    
	}
	
	public String XML_to_String(ResultSet rs) throws Exception{
		StringWriter sw = this.convertXML(rs);
		return sw.toString();
	}

}
