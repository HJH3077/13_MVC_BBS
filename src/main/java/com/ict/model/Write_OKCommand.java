package com.ict.model;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ict.db.DAO;
import com.ict.db.BVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class Write_OKCommand implements Command{
	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		try {
			String path = request.getServletContext().getRealPath("/upload");

			MultipartRequest mr = new MultipartRequest(request, path, 100 * 1024 * 1024, "utf-8",
					new DefaultFileRenamePolicy());

			BVO bvo = new BVO();
			bvo.setSubject(mr.getParameter("subject"));
			bvo.setWriter(mr.getParameter("writer"));
			bvo.setContent(mr.getParameter("content"));
			bvo.setPwd(mr.getParameter("pwd"));
			
			// 첨부파일이 있을 때와 첨부파일이 없을 때를 구분하자 (이걸 안해주면 나중에 오류남)
			if (mr.getFile("file_name") != null) {
				bvo.setFile_name(mr.getFilesystemName("file_name")); // 파일 있을 때
			} else {
				bvo.setFile_name(""); // 파일 없을 떄, 이거 안하면 DB처리 시 오류남
			}

			int result = DAO.getInsert(bvo);
			if (result > 0) {
				return "MyController?cmd=list";
			}
		} catch (IOException e) {
		}
		return null;
	}
}
