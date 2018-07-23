package upload.program;

import java.io.BufferedReader;
import java.io.File;


import java.io.InputStreamReader;

import java.util.Scanner;
import java.io.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
 

public class CodeRun {
	private static ExecutorService executorService = Executors.newSingleThreadExecutor();
	public String codeRun(String filename,String path,String name,String tikunum)
	{
		String inputdir="E:/ExamOL/Problem/"+tikunum+"/input";
		String message = "";
		String input = ""; 
		try {
			if(!new File(path+"/"+filename+".exe").exists()) return "������ִ���ļ�ʧ��<br />";
			else{
				File inputfile = new File(inputdir);
				if(inputfile.exists())
				{
					File[] filearray = inputfile.listFiles();
					if(filearray!=null)
					{
						for(int i=0;i<filearray.length;i++){
							//System.out.println(filearray[i].getAbsolutePath());
							Scanner scanner = new Scanner(filearray[i]);
							while(scanner.hasNextLine()){
								input+=(scanner.nextLine()+"\n");
							}
							scanner.close();
							String s = run(input,filename,path,name);
							if(s.equals("timeout")) return "���г�ʱ,�������ṹ������ע�ⲻҪ������ѭ��";
							if(s.equals("exception")) return "Exception";
							message+=("input:<br />"+input+"<br />output:<br />"+s+"<br /><hr />");
							input="";
						}
						message = "���н��<br />Test result<br />"+message;
					}
					else message = run("",filename,path,name);
				}
				else message = "���н��<br />"+run("",filename,path,name);	
			}	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "�����쳣";
		}
		return message;
	}
	private String run(String input,String filename,String path,String name)
	{
		String message="";
        //��ʼִ�к�ʱ����  
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {

            @Override
            public String call() {
            	String message = "";
            	try {
        			Process process = Runtime.getRuntime().exec("cmd /c "+filename,null,new File(path));
        			if(process!=null){
        				String line=null; 
        	            String true_result="";
        	            String false_result="";
        				OutputStream stdin = process.getOutputStream();
        				stdin.write(input.getBytes());
        				stdin.flush();
        				//ȡ���������������    
        	            //��һ�����������ȥ��    
        	            //�û���������    
        	             BufferedReader br=new BufferedReader(new InputStreamReader(process.getInputStream())); 
        	             
        	             BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        	             
        	            //ֱ������Ϊֹ
        	             File file= new File(path+"/"+name+"_out.txt");
        	             if(!file.exists())	file.createNewFile();
        	             FileWriter fw = new FileWriter(file, true);  
        	             BufferedWriter bw = new BufferedWriter(fw);  
        	             
        	            while((line=br.readLine())!=null)    
        	             {    
        	                 //System.out.println(line);
//        					ps.append(line+"\n");
        	                 bw.append(line+"\r\n"+"************************************"); 
        	                 true_result=true_result+"<br />"+line;
        	             }
        	        
        	            while ((line = stdError.readLine()) != null) {
        	            	//System.out.println(line);
//        					ps.append(line);
        	            	bw.append(line+"\r\n");
        	            	false_result =false_result+line+"<br />";
        	            }
        	            bw.close();
        	            fw.close();
        	            stdin.close();
        	            br.close();
        	            stdError.close();
        	            process.waitFor();
        	            process.destroy();
        				if(false_result!="")
        				{
        					//System.out.println(false_result);
        					message = false_result;
        				}
        				else{
        					message = true_result;
        				}
        			}
        			return message;
        			
        		} catch (Exception e) {
        			// TODO: handle exception
        			e.printStackTrace();
        			message = "exception";
        		}
                return message;
            }
        });
        executorService.execute(futureTask);
        try {
            message = futureTask.get(2*1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            //e.printStackTrace();
            futureTask.cancel(true);
           message = "timeout";
        }
    	
		return message;
	}

}

