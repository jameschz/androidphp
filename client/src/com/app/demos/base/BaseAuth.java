package com.app.demos.base;

import com.app.demos.model.Customer;

public class BaseAuth {
	
	static public boolean isLogin () {
		Customer customer = Customer.getInstance();
		if (customer.getLogin() == true) {
			return true;
		}
		return false;
	}
	
	static public void setLogin (Boolean status) {
		Customer customer = Customer.getInstance();
		customer.setLogin(status);
	}
	
	static public void setCustomer (Customer mc) {
		Customer customer = Customer.getInstance();
		customer.setId(mc.getId());
		customer.setSid(mc.getSid());
		customer.setName(mc.getName());
		customer.setSign(mc.getSign());
		customer.setFace(mc.getFace());
	}
	
	static public Customer getCustomer () {
		return Customer.getInstance();
	}
}