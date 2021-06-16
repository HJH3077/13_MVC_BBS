package com.ict.model;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ict.db.BVO;
import com.ict.db.DAO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class Update_OKCommand implements Command {
	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		try {
			String path = request.getServletContext().getRealPath("/upload");

			MultipartRequest mr = new MultipartRequest(request, path, 100 * 1024 * 1024, "utf-8",
					new DefaultFileRenamePolicy());

			BVO bvo2 = (BVO) request.getSession().getAttribute("bvo");

			BVO bvo = new BVO();
			bvo.setB_idx(bvo2.getB_idx());
			bvo.setSubject(mr.getParameter("subject"));
			bvo.setWriter(mr.getParameter("writer"));
			bvo.setContent(mr.getParameter("content"));
			bvo.setPwd(mr.getParameter("pwd"));

			String old_file_name = mr.getParameter("old_file_name");
			// 파일 처리
			if (mr.getFile("file_name") == null) {
				// 새로운 첨부 파일이 없으면 
				bvo.setFile_name(old_file_name); // 이전 파일 이름 그대로
			} else {
				// 새로운 첨부 파일이 있으면
				bvo.setFile_name(mr.getFilesystemName("file_name")); // 새로운 파일 명으로
			}

			// DB 업데이트 처리
			int result = DAO.getUpdate(bvo);
			if (result > 0) {
				// 업데이트 성공 후 이전 파일 삭제 처리
				try {
					if(! bvo.getFile_name().equals(old_file_name)) {
						// 이전 파일이 새로운 파일과 다르면 삭제
						File file = new File(path + "/" + new String(old_file_name.getBytes("utf-8")));
						if (file.exists()) {
							file.delete();
						}						
					}
				} catch (Exception e) {
				}
				String cPage = mr.getParameter("cPage");
				return "MyController?cmd=onelist&b_idx=" + bvo.getB_idx() + "&cPage=" + cPage;
			}

		} catch (Exception e) {
		}
		return null;
	}
}
