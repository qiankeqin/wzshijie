package com.lagoqu.worldplay.common.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.lagoqu.worldplay.util.TokenThread;

public class InitServlet extends HttpServlet{

	  public void init() throws ServletException {   
		  new Thread(new TokenThread()).start();
	  }
	
}
