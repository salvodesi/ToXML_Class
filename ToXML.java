package com.services.convert.toXML;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;


public class ToXML {
	
	public ToXML(ResultSet result) throws Exception {
		// TODO Auto-generated constructor stub
	}
	
	public StringBuffer convertXML(ResultSet rs) throws Exception{
		StringBuffer xmlReply = new StringBuffer();
		xmlReply.append("<results>");

	    ResultSetMetaData rsmd = rs.getMetaData();
	    int colCount = rsmd.getColumnCount();
	    
	    while (rs.next()) {
	        xmlReply.append("<row>");
	        for (int i = 1; i <= colCount; i++) {
	          String columnName = rsmd.getColumnName(i);
	          Object value = rs.getObject(i);
	          xmlReply.append("<"+columnName+">"+value.toString()+"</"+columnName+">");
	        }
	        xmlReply.append("</row>");
	      }

        xmlReply.append("</results>");
	    return xmlReply;
	    
	}
	
	public String XML_to_String(ResultSet rs) throws Exception{
		StringBuffer sw = this.convertXML(rs);
		return sw.toString();
	}

}
