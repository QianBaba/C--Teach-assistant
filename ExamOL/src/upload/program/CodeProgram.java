package upload.program;

import java.io.*;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
public class CodeProgram extends DBMessage{
	
	//banji,num,filename,code
	public String compileResult (String... codemsg)
	{
		
		String submit_path="E:/ExamOL/Exam";
		//System.setProperty("java.security.policy", "java.policy");
		//System.setSecurityManager(new SecurityManager());
		String message = null;
		String path = null;
		String newcode = null;//����ض����Ĵ���
		String insertcode ="";//��Ҫ������ض�������
		String c_name = null;//c�ļ�����·��
		StringBuilder oldcode = null;
		String teacher = "";
		String courseNum = "";
		String examName = "";
		String courseName = "";
		String datetime="";
		String[] q_nums=null;
		try {
			Connection conn=this.ConnectDB();
			Statement st=conn.createStatement();
			ResultSet rs=null;

			String sql1="SELECT * FROM test where id="+codemsg[5];
			rs=st.executeQuery(sql1);
			while(rs.next()){
				courseNum=rs.getString("course");
				examName=rs.getString("name");
				q_nums=rs.getString("q_num").split(";");
			}
			String sql3="SELECT * FROM course where num="+courseNum;
			rs=st.executeQuery(sql3);
			while(rs.next()){
				teacher=rs.getString("teacher");
				courseName=rs.getString("name");
			}
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		    datetime=df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
		    
			String sql2="INSERT INTO submit(user_id,class,test,score,time)"
					+" VALUES ('"+codemsg[1]+"','"+courseNum+"','"+codemsg[5]+"','"+"0"+"','"+datetime+"')";
			st.executeUpdate(sql2);
			rs.close();
			st.close();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "Exception";
		}
		
		try {
			Scanner sc = new Scanner(new File("E:/ExamOL/blackname.txt"));
			while(sc.hasNextLine()){
				insertcode+=(sc.nextLine()+"\n");
			}
			sc.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "Exception";
		}
		if(codemsg[3]!=null){
			//�û��ϴ��õ��Ĵ���
			oldcode = new StringBuilder(codemsg[3]);
			//������
			//insertcode = "#define system errorNo1\n";
//			insertcode = "//ֱ����java���ض���";
			//����ƥ���������ã�
			//1.���û��Ĵ�����й淶��飬�����ж��Ƿ�ͨ��
			//2.���� ����ض�����䵽��ȷλ��
			
			Pattern p = Pattern.compile("int[\\s]*main[\\s]*\\(\\)\\s*\\{");
			Matcher matcher =p.matcher(codemsg[3]);
			int i =0;
			while(matcher.find()){
				//����һ��main����
				++i;
				oldcode.insert(matcher.start(), insertcode);
				newcode = oldcode.toString();
			}
			//����һ��main����
			if(i!=1) return "���벻ͨ�����������main������ʽ�Ƿ���ȷ";	
		}
		//���벻��Ϊ��
		else return "���벻ͨ���������ƴ���";
		//System.out.println(newcode);
		try {
			//���ô���(.c�ļ�)�Ĵ��·��
			
			path =submit_path+"/"+teacher+"/"+courseName+"/"+examName+"/"+codemsg[0]+"/"+codemsg[4]+"/"+codemsg[6];
			File userfiledir = new File(path);
			File usrf = null;
			PrintStream ps = null;
			if(!userfiledir.exists())
			{
				Boolean b1 =userfiledir.mkdirs();
				if(b1){
					c_name = path+"/"+codemsg[2]+".c";//·��+�ļ���
					//System.out.println(c_name);
				}
				else return "Exception";
			}
			else {
				c_name = path+"/"+codemsg[2]+".c";
			}
			usrf = new File(c_name);
			//����д���ļ�
			if(!usrf.exists()){
				Boolean b2 =usrf.createNewFile();
				if(b2){
					ps = new PrintStream(new FileOutputStream(usrf));
					ps.println(newcode);
				}
				else return "Exception";
			}
			else{
				ps = new PrintStream(new FileOutputStream(usrf));
				ps.println(newcode);
			}
			ps.close();
			String s = q_nums[new Integer(codemsg[6])-1];
			//�������ɿ�ִ���ļ���ָ���ļ���
			message =new CodeCompile().codeCompile(codemsg[2], path,s);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return message;
	}

}
