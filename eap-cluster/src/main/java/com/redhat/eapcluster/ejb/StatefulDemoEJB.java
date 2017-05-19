package com.redhat.eapcluster.ejb;

import javax.ejb.Stateful;

@Stateful
public class StatefulDemoEJB {
  int data;
  
    public void add() {
	   data++;	
	}

	public int getData() {
		return data;
	}

 
}
