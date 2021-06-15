package com.ict.model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ict.db.BVO;
import com.ict.db.CVO;
import com.ict.db.DAO;

public class OneListCommand implements Command{
	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		String b_idx = request.getParameter("b_idx");
		// 내용 가져오기
		BVO bvo = DAO.getOneList(b_idx);

		// 조회수 업데이트 (단, 조회수가 조작이 가능함 => 이걸 해결하는게 숙제)
		int result = DAO.getHitup(b_idx);
		
		// b_idx를 가지고 관련 댓글 가져오기 (즉, 해당 onelist의 b_idx인 댓글만 댓글 DB에서 가져온다는 의미)
		List<CVO> c_list = DAO.getcList(b_idx);
		request.setAttribute("c_list", c_list);
		
		// 수정과 삭제를 위해서 session에 저장
		request.getSession().setAttribute("bvo", bvo);
		return "view/onelist.jsp";
	}
}
