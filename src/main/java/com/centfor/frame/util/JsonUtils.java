package com.centfor.frame.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils  {
	   static 	ObjectMapper mapper = null;
	   public static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	   static{
		   mapper = new ObjectMapper();
		   //为 null 的不转换
		   mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL); 
	   }
	 public static <T> T readValue(String content,Class<T> clazz) {
		 T t=null;
		try {
			t=  mapper.readValue(content, clazz);
		} catch (JsonParseException e) {
			logger.error(e.getMessage(),e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(),e);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		return t;
	 }
	 
	 public static String writeValueAsString(Object o){
		 String str=null;
		try {
			str= mapper.writeValueAsString(o);
		} catch (JsonGenerationException e) {
			logger.error(e.getMessage(),e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(),e);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		return str;
	 }
	 
	 public static Object readValues(String content,Class CollectionType,Class clazz){
		 Object o=null;
		 
		try {
			o=  mapper.readValue(content, getCollectionType(CollectionType, clazz));
		} catch (JsonParseException e) {
			logger.error(e.getMessage(),e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(),e);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		
		return o;
	 }
	 
	 public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {   
	         return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
	 }  
	 
}
