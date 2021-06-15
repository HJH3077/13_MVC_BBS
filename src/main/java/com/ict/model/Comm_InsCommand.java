package com.ict.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ict.db.BVO;
import com.ict.db.CVO;
import com.ict.db.DAO;

public class Comm_InsCommand implements Command{
	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		// b_idx를 얻기 위햐서 session의 bvo 호출
		BVO bvo = (BVO)request.getSession().getAttribute("bvo");
		CVO cvo = new CVO();
		cvo.setB_idx(bvo.getB_idx());
		cvo.setContent(request.getParameter("content")); // cvo의 content임
		
		int result = DAO.getComm_Insert(cvo);
		if(result > 0) {
			return "MyController?cmd=onelist&b_idx="+ bvo.getB_idx();
		}
		
		return null;
	}
}
