package com.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import upload.program.RegisterProgram;

@SuppressWarnings("serial")
public class Register extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String message = "";
			request.setCharacterEncoding("utf-8");
			String name = request.getParameter("name");
			String number = request.getParameter("xuehao");
			String password = request.getParameter("mima");
			String banji = request.getParameter("banji");
			
			int val=new RegisterProgram().user_save(name,number,password,banji);
			if(val==1){			//�ж��û��������Ƿ���ȷ
				message="{'state':'�ɹ�','result':'1'}"; 			//��ǰ̨���سɹ���Ϣ
			}else if(val==-1){
				message="{'state':'ʧ��','result':'-1'}";		//��ǰ̨����ʧ����Ϣ
			}
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(message);
		} catch (Exception e) {
			// TODO: handle exception
			response.getWriter().write("{'state':'ʧ��','result':'0'}");
		}

	}
}


