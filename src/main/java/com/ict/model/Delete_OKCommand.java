package com.ict.model;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ict.db.BVO;
import com.ict.db.DAO;

public class Delete_OKCommand implements Command{
	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		BVO bvo = (BVO)request.getSession().getAttribute("bvo");
		int result = DAO.getDelete(bvo);
		if(result > 0) {
			String path = request.getServletContext().getRealPath("/upload");
			String file_name = bvo.getFile_name();
			try {
				File file = new File(path + "/" + new String(file_name.getBytes("utf-8")));
				if(file.exists()) {
					file.delete();
				}
			} catch (Exception e) {
			}
			return "MyController?cmd=list";
		}
		return null;
	}
}
