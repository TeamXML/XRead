package de.fu.xml.xread;


import java.io.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.lang.Object;
import java.lang.reflect.Array;

import org.json.*;


import com.google.gson.*;

public class ToJson {
	
	
//	private static String myUrl;

    public static void mainJson(String myUrl, String fileUrl) throws Exception{
    	myUrl = "http://twitter.com/";
        fileUrl = "/Users/Gayane/Desktop/firstexample.json";
   	   Object jsonobjekt = new Gson().fromJson(new FileReader(fileUrl), Object.class);
         
       List keys = getKeysFromJson(fileUrl,myUrl);
           	
       String tojson = "{ '@context': 'jsonFile.jsonld', "+
    		   jsonobjekt.toString()+ "}";
       
      
       try {
           File file = new File("jsonFile.jsonld");
           BufferedWriter output = new BufferedWriter(new FileWriter(file));
           output.write("{"+keys+"}");
           output.close();
           
           file = new File("JsonLDOutput.txt");
           output = new BufferedWriter(new FileWriter(file));
           output.write(tojson);
           output.close();
         } catch ( IOException e ) {
            e.printStackTrace();
         }
       
    }
    
    
    
    static List getKeysFromJson(String fileName,String URL) throws Exception
    {
      Object jsonobjekt = new Gson().fromJson(new FileReader(fileName), Object.class);
      List keys = new ArrayList();
      List aktiv_key = new ArrayList();
      List objekts = null;
      List with_parents = null;
      Object prev_object = null;
      boolean first = false;
      collectAllTheKeys(keys, jsonobjekt, aktiv_key,with_parents,first,URL);
      return aktiv_key;
    }

    static void collectAllTheKeys(List keys,  Object o, List aktiv_key,List with_parents, boolean first,String URL)
    {
    	
      Collection values = null;
      if (o instanceof Map)
      {
        Map map = (Map) o;
        Map old = null;
      
        keys.addAll(map.keySet()); 
        values = map.values();
        for(Object key : map.keySet()){
        	if(!first){
        		aktiv_key.add("'"+key+"'"+":"+"'"+URL+key+"'");
        		
        	}
        }
        first = false;
  	  
        for(Object key : map.keySet()){
        	if (map.get(key) instanceof Map)
                {
        			Object k = map.get(key);
        			Map m = (Map) k;
        		
        			if(m.keySet() != null){
        				for(Object ks : m.keySet()){
        					
        					aktiv_key.add("'"+ks+"'"+":"+"{'@id':'"+key+":"+ks+"'}");
           			 }
        			} else  System.out.println("ELSE   "+key);
        			 
        		
                }
        		
        		 else if (map.get(key) instanceof Collection){
         	        values = (Collection) map.get(key);
         	       for(Object val : values){
         	        	nested(key, aktiv_key, val);
         	        }
         	      }
        		
        		
        		
        }
       
  
      }
      else if (o instanceof Collection){
    	  
        values = (Collection) o;

      }
      else {
    	return;
      }
     
      for (Object value : values){ 
    	  
    	  collectAllTheKeys(keys, value, aktiv_key,with_parents,first, URL);
    	 
    	  
      }
        
    }
    static void nested(Object key, List aktiv_key,  Object o)
    {	
    	
     Collection values = null;
    
   	 if (o instanceof Map)
        {
   		 
          Map map = (Map) o;
          values = map.values();
   	
         if(map.keySet() != null){
 				for(Object ks : map.keySet()){
 					aktiv_key.add("'"+ks+"'"+":"+"{'@id':'"+key+":"+ks+"'}");
    			 }
 				
 			}
          
      
      
      }
       	
   	 else if (o instanceof Collection){
   	        values = (Collection) o;
	
   	      }
    else return;
	   	    
	     for(Object val : values){
	     	nested(key, aktiv_key, val);
	     }
    	
    }
    
  
    
}

