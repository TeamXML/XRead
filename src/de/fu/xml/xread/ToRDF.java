package de.fu.xml.xread;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class ToRDF {
	
	private String previouse_kategorie;
	private static String myUrl;

    public static void main(String... args) throws Exception {
    	myUrl = "http://twitter.com/";
   	   Object jsonobjekt = new Gson().fromJson(new FileReader("/Users/Gayane/Desktop/firstexample.json"), Object.class);
         
       List keys = getKeysFromJson("/Users/Gayane/Desktop/firstexample.json",myUrl);
       String rdfall ="";
        for(Object key : keys){
        	rdfall = rdfall+key+"  ";
        }
       String tordf = "<rdf:RDF  xmlns:twitter= '"+myUrl+"' >"+
       "<rdf:Description rdf:about='"+myUrl+"'>"+
    		   rdfall+ "</rdf:Description></rdf:RDF>";
       
      System.out.println(tordf);
       try {
           File file = new File("jsonFile.jsonld");
           BufferedWriter output = new BufferedWriter(new FileWriter(file));
           output.write("{"+keys+"}");
           output.close();
           
           file = new File("JSONRDFOutput.txt");
           output = new BufferedWriter(new FileWriter(file));
           output.write(tordf);
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
      String myRDF = null;
      boolean first = false;
      collectAllTheKeys(keys, jsonobjekt,jsonobjekt, aktiv_key,with_parents,first,URL);
      System.out.println(aktiv_key.toString());
      return aktiv_key;
    }

    static String collectAllTheKeys(List keys,  Object o, Object old, List aktiv_key,List with_parents, boolean first,String URL)
    {
    	
    	   Collection values = null;
    	      if (o instanceof Map)
    	      {
    	        Map map = (Map) o;
    	     
    	     
    	        keys.addAll(map.keySet()); 
    	        values = map.values();
    	      
    	        first = false;
    	  	  
    	        for(Object key : map.keySet()){
    	        	Object k = map.get(key);
    	        	
    	        		if(!(k instanceof Map) && !(k instanceof Collection)){
    	        			aktiv_key.add( "<twitter:"+key+">");
    	        			aktiv_key.add( k);
    	        			aktiv_key.add("</twitter:"+key+">");
    	        		}
    	        		else{
    	        			aktiv_key.add( "<twitter:"+key+">");
    	        		
    	        			aktiv_key.add("</twitter:"+key+">");
    	        		}
    	        		
    	        		
    	        		
    	        }
    	       
    	        
    	        for(Object key : map.keySet()){
    	        	Object k = map.get(key);
    	        	if((k instanceof Map) || (k instanceof Collection)){
    	        		nested(key, aktiv_key, k);
    	        	}
    	        		
    	      
    	          	       
    	        }
    	       return "";
    	  
    	      }
    	      else if (o instanceof Collection){
    	    	 
    	        values = (Collection) o;

    	      }
    	      else {
    	    	 // System.out.println("Else    "+o);
    	    	  String st = "";
    	    	  if(o != null){
    	    		  st = o.toString();
    	    		  return st;
    	    	  }
    	    	  
    	    	
    	    	  
    	    	return "";
    	      }
    	     
     
      for (Object value : values){ 
    	  
    	 // System.out.println(value);
    	  
    	  collectAllTheKeys(keys, value,old, aktiv_key,with_parents,first, URL);
    	 
    	  
      }
      return "";
        
    }
    
    
    
    static String nested(Object keys, List aktiv_key,  Object o)
    {
    	   Collection values = null;
 	      if (o instanceof Map)
 	      {
 	        Map map = (Map) o;
 	     
 	     
 	        values = map.values();
 	      
 	      
 	   
 	    for(Object key : map.keySet()){
        	Object k = map.get(key);
        	
        		if(!(k instanceof Map) && !(k instanceof Collection)){
        			
        		
        			aktiv_key.add(aktiv_key.indexOf("<twitter:"+keys+">")+1, "<twitter:"+key+">");
        			aktiv_key.add(aktiv_key.indexOf("<twitter:"+keys+">")+2, k);
        			aktiv_key.add(aktiv_key.indexOf("<twitter:"+keys+">")+3,"</twitter:"+key+">");
        		}
        		else{
        			
                			
	        		aktiv_key.add(aktiv_key.indexOf("<twitter:"+keys+">")+1, "<twitter:"+key+">");
	        		aktiv_key.add(aktiv_key.indexOf("<twitter:"+keys+">")+2, "</twitter:"+key+">");
	        		
	        		if(k instanceof Collection){
        				for(Object kcoll: (Collection)k){
            				if(!(kcoll instanceof Map) && !(kcoll instanceof Collection)){
            				//	t = true;
            					System.out.println("  key "+kcoll);
            					
            					aktiv_key.add(aktiv_key.indexOf("<twitter:"+keys+">")+2, kcoll);
            				}
            			}
        			}
	        		
	        	
        		}
        		
        		
        		
        }
 	   for(Object key : map.keySet()){
       	Object k = map.get(key);
	       	if((k instanceof Map)){
	       		nested(key, aktiv_key, map.get(key));
	       	}
	       	else if(k instanceof Collection){
	       		for(Object c : (Collection)k){
	       			nested(key, aktiv_key, c);
	       		}
	       	}
       		
     
         	       
       }
 	   	   
 	      }
 	      else if (o instanceof Collection){
 	    	 
 	        values = (Collection) o;

 	      }
 	      else {
 	    	
 	    	  String st = "";
 	    	  if(o != null){
 	    		  st = o.toString();
 	    		  return st;
 	    	  }
 	    	  
 	    	
 	    	  
 	    	return "";
 	      }
 	     
  
  
   return "";
   
  
    }
}

