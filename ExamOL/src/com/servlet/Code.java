package com.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import upload.program.CodeProgram;
//�����ϴ��Ĵ�����
@SuppressWarnings("serial")
public class Code extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String message = "";
			request.setCharacterEncoding("utf-8");					//���ñ����ʽΪutf-8	
			String code = request.getParameter("code");
			String filename = request.getParameter("filename");
			String banji = request.getParameter("banji");
			String num = request.getParameter("num");
			
			
			String examId = request.getParameter("examId");
			String tihao = request.getParameter("tihao");
			String studentName = request.getParameter("name");
			//System.out.println(filename+"\n"+code+"\n"+banji+"\n"+num);
			message = new CodeProgram().compileResult(banji,num,filename,code,studentName,examId,tihao);
			//System.out.println(message);
			response.setContentType("text/html;charset=utf-8");	//������Ӧͷ������
			response.getWriter().write(message);					//������Ӧ��Ϣ
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
