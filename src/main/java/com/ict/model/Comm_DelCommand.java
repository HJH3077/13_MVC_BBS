package com.ict.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Comm_DelCommand implements Command{
@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
	return "MyController?cmd=onelist?b_idx=";
	}
}
