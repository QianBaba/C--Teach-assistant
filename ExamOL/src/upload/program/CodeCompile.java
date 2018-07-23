package upload.program;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class CodeCompile {
	public String codeCompile(String filename,String path,String tikunum)
	{
		Process process = null;
		String message = null;
		try {
			process = Runtime.getRuntime().exec("cmd /c gcc -o "+"R"+filename+" "+filename+".c",null,new File(path));
			String line=null; 
            String true_result="";
            String false_result="";
            File compile_file = new File(path+"/"+filename+"_compile.txt");
            if(!compile_file.exists()) compile_file.createNewFile();
            PrintStream ps = new PrintStream(new FileOutputStream(compile_file));
			
			if(process!=null){
				//ȡ���������������    
	             InputStream fis=process.getInputStream();    
	            //��һ�����������ȥ��    
	             InputStreamReader isr=new InputStreamReader(fis);    
	            //�û���������    
	             BufferedReader br=new BufferedReader(isr); 
	             
	             BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
	             
	            //ֱ������Ϊֹ    
	            while((line=br.readLine())!=null)    
	             {    
	                 //System.out.println(line);
	                 true_result=true_result+"<br />"+line;
	             }   
	            while ((line = stdError.readLine()) != null) {
	            	//System.out.println(line);
	            	false_result =false_result+line+"<br />";
	            	ps.append(line+"\r\n");
	            }
	            ps.close();
	            br.close();
	            stdError.close();
			}
			process.waitFor();
			process.destroy();
			if(false_result!="")
			{
				//System.out.println(false_result);
				message = "���뱨��<br />"+false_result;
			}
			else{
				message =new CodeRun().codeRun("R"+filename, path,filename,tikunum);
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return message;
	}

}
