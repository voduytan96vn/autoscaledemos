package com.redhat.ejb;

 
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
 
 
@Stateless
@Remote(Service.class)

public class ServiceEJB implements Service {
 
 
       
	@Override
	public String exec(String arg) {
	 
		 return "Hello "+arg;
			 
		 
	    
	   
   }
	
 
	
}
