package com.monkey.util;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.testng.annotations.Test;

public class FtpApacheUploadFile {
	private  FTPClient ftp; 
	private static String path = System.getProperty("user.dir");
	private String datePath = "";
	private static int fileNum = 0;
	
	public FtpApacheUploadFile() {
		datePath = RandomStr.random();
	}
	
    /**  
     *   
     * @param path 上传到ftp服务器哪个路径下     
     * @param addr 地址  
     * @param port 端口号  
     * @param username 用户名  
     * @param password 密码  
     * @return  
     * @throws Exception  
     */    
    private  boolean connect(String path, String addr, int port, String username, String password) throws Exception {      
        boolean result = false;      
        ftp = new FTPClient();      
        int reply;      
        ftp.connect(addr,port);      
        ftp.login(username,password);      
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);      
        reply = ftp.getReplyCode();      
        if (!FTPReply.isPositiveCompletion(reply)) {      
            ftp.disconnect();      
            return result;      
        }   
        
        ftp.changeWorkingDirectory(path);  
        ftp.makeDirectory(datePath);        						// 文件夹若不存在则创建
        ftp.changeWorkingDirectory(path + "\\" + datePath);    
        ftp.makeDirectory("android");        						// 文件夹若不存在则创建
        ftp.changeWorkingDirectory(path + "\\" + datePath + "\\android\\");   
        return result;      
    }      
    /**  
     *   
     * @param file 上传的文件或文件夹  
     * @throws Exception  
     */    
    private void upload(File file) throws Exception{  
    	String fileNames = "";
        if(file.isDirectory()) { 
        	if (!ftp.makeDirectory(file.getName())) {
        		fileNames = file.getName() + "_" + (fileNum ++);
	            while (!ftp.makeDirectory(fileNames)) {
	            	upload(file);
	            } 
        	} else {
        		fileNames = file.getName();
        	}
        	ftp.changeWorkingDirectory(fileNames); 
            String[] files = file.list();             
            for (int i = 0; i < files.length; i++) {                         
                File fileName = new File(file.getPath() + "\\" + files[i]);      
//                System.out.println(file.getPath() + "\\" + files[i]);
                FileInputStream input = new FileInputStream(fileName);      
                ftp.storeFile(fileName.getName(), input);      
                input.close();                                                           
	           
            } 
        } else {   
            File fileName = new File(file.getPath());      
            FileInputStream input = new FileInputStream(fileName);      
            ftp.storeFile(fileName.getName(), input);      
            input.close();        
        }      
    }
    
    @Test
    public static void uploadResults() throws Exception{    
    	FtpApacheUploadFile uploadFile = new FtpApacheUploadFile();    
    	uploadFile.connect("/", "192.168.1.103", 21, "Test", "123456");    
    	File file = new File(path + "\\data\\MonkeyChartReports\\");    
    	uploadFile.upload(file);    
    }    
}