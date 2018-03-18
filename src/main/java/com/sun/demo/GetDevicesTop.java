package com.sun.demo;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.monkey.model.MonkeyParams;
import com.monkey.util.ReadWriteTxtFile;
import com.monkey.util.Utils;

public class GetDevicesTop {
	public static String path = System.getProperty("user.dir");
	
	public static void GetMemory(MonkeyParams params) throws IOException {
		String info = path + params.getMonitorData() + params.getMemInfoTemp();
	    try {
	    	ReadWriteTxtFile.isFileExist(info);
	    	ReadWriteTxtFile.isFileExist(path + params.getMonitorData() + params.getMemInfo());
	    	
	    	String command = "cmd /c adb shell dumpsys meminfo " + params.getAppPackage() +" | findstr \"TOTAL\" > " + info;
	        Process process = Runtime.getRuntime().exec(command);
	        process.waitFor();	
	        List<String> list = new LinkedList<String>();
	        for(String s:ReadWriteTxtFile.ReadTxtLine(info, " ")) {
	        	if (!s.isEmpty()) {
	        		list.add(s);
	        	}
	        }
//	        System.out.println("Memory = " + list.get(1));
	        ReadWriteTxtFile.writeTxt(Utils.getTime() + "=" + list.get(1), path + params.getMonitorData() + params.getMemInfo());
	        process.destroy();
	    } catch (Exception StringIndexOutOfBoundsException) {
            //System.out.print("请检查设备是否连接");
        }
	}
	
	public static void GetCPU(MonkeyParams params) throws IOException {
		String info = path + params.getMonitorData() + params.getCpuInfoTemp();
	    try {
	    	ReadWriteTxtFile.isFileExist(info);
	    	ReadWriteTxtFile.isFileExist(path + params.getMonitorData() + params.getCpuInfo());
	    	
	    	String command = "cmd /c adb shell top -n 1 | findstr " + params.getAppPackage() + " > " + info;
	        Process process = Runtime.getRuntime().exec(command);
	        process.waitFor();		
	        
	        List<String> list = new LinkedList<String>();
	        for(String s:ReadWriteTxtFile.ReadTxtLine(info, " ")) {
	        	if (!s.isEmpty()) {
	        		list.add(s);
	        	}
	        }
//	        System.out.println("CPU = " + list.get(2));
	        ReadWriteTxtFile.writeTxt(Utils.getTime() + "=" + list.get(2), path + params.getMonitorData() + params.getCpuInfo());
	        process.destroy();
	    } catch (Exception StringIndexOutOfBoundsException) {
            //System.out.print("请检查设备是否连接");
        }
	}

}
