package com.monkey.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadWriteTxtFile {
	
	/**
	 * 读取txt文件
	 * @param path
	 * @return
	 */
	public static String[] ReadTxtLine(String path, String split){
		String lineTxt = "";
		String[] data = null;
        try {
            File file = new File(path);
            if(file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(read);
                lineTxt = bufferedReader.readLine();
                read.close();
                System.out.println("lineTxt = " + lineTxt);
                if (lineTxt.toString() == null || lineTxt.toString().isEmpty() ) {
                	return null;
                } else {
                	data = lineTxt.split(split);
                }
	        } else {
	            System.out.println("找不到指定的文件");
	        }           
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        
        return data;
    }
	
	/**
	 * 写入txt
	 * @param username
	 * @param fileName
	 */
	public static void writeTxt(String str, String fileName) {  
		try {  
			FileOutputStream output = new FileOutputStream(fileName, true); 
			str += ",";
			output.write(str.getBytes("UTF-8"));  
			output.close();   
		} catch (Exception e) {  
			e.printStackTrace();  
		}
	}
	
    /** 
     * 判断文件夹是否存在，如果不存在则新建 
     * @param chartPath 
     */  
	public static void isFileExist(String filePath) {  
        File file = new File(filePath);  
        if (!file.exists()) {  
        	try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }  
    }
	
    /** 
     * 判断文件夹是否存在，如果不存在则新建 
     * @param chartPath 
     */  
    public static void isFolderExist(String chartPath) {  
        File file = new File(chartPath);  
        if (!file.exists()) {  
            file.mkdirs();  
        // log.info("CHART_PATH="+CHART_PATH+"create.");  
        }  
    }
    
    /**
     * 删除文件
     * @param sPath
     */
    public static void deleteFile(String Path) {   
        File file = new File(Path);  
        if (file.isFile() && file.exists()) {  
            file.delete();  
        }  
    }
}
