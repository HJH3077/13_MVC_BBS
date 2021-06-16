package com.ict.model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ict.db.BVO;
import com.ict.db.DAO;

public class ListCommand implements Command{
	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		Paging pvo = new Paging();
		
		// 1. 전체 게시물의 수를 구한다.
		int count = DAO.getCount();
		pvo.setTotalRecord(count);
		
		// 2. 전체 게시물의 수를 활용해서 전체 페이지의 수를 구하자
		pvo.setTotalPage(pvo.getTotalRecord()/pvo.getNumPerPage());
			// 나눠서 0이 아닌경우 즉, 나머지가 존재하면 전체페이지에서 1증가
			if(pvo.getTotalRecord()%pvo.getNumPerPage() != 0) {
				pvo.setTotalPage(pvo.getTotalPage()+1);
			}

		// ** 3. 현재 페이지 구하기
		// 무조건 ListCommand는 cPage라는 파라미터값을 받아야 한다. (cmd가 list이면 무조건 cPage 파라미터를 넣어야 한다.)
		String cPage = request.getParameter("cPage");
	    pvo.setNowPage(Integer.parseInt(cPage));
			
		// ** 4. 시작번호와 끝번호를 구해서 DB 정보 가져오기
		pvo.setBegin((pvo.getNowPage()-1)*pvo.getNumPerPage()+1); // 시작번호
		pvo.setEnd((pvo.getBegin()-1)+pvo.getNumPerPage());    // 끝번호
		List<BVO> list = DAO.getList(pvo.getBegin(), pvo.getEnd());
		
		// ** 5. 시작블록과 끝 블록 구하기
		pvo.setBeginBlock((int)(pvo.getNowPage()-1)/pvo.getPagePerBlock()*pvo.getPagePerBlock()+1); 
		pvo.setEndBlock(pvo.getBeginBlock()+pvo.getPagePerBlock()-1);
		// getPagePerBlock이 2라서
		// 1, 2페이지는 시작이 1, 끝이 2가됨; 3, 4페이지는 시작이 3, 끝이 4가됨
		// 즉, 1,2 페이지일 때는 이전으로 버튼을 못누르게 하고 다음으로 버튼을 누르면 3,4 페이지를 보기 위해 3,4 버튼이 보이게 하기위해
		// 주의사항 : endBlock이 totalPage보다 클 수가 있다. ex)5페이지가 끝인데 6은? 6도 생김
		if(pvo.getEndBlock() > pvo.getTotalPage()) {
			pvo.setEndBlock(pvo.getTotalPage());
		}
		
		
		request.setAttribute("list", list);
		request.setAttribute("pvo", pvo);
		return "view/list.jsp";
	}
}
