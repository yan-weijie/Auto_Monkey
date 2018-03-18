package com.sun.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.testng.annotations.Test;

import com.monkey.GenChart.CreateChart;
import com.monkey.model.MonkeyParams;
import com.monkey.util.ReadWriteTxtFile;

public class MonkeyRun {
	
	public ExecuteCommand execute;
	
	public MonkeyParams params;
	
	public String path = System.getProperty("user.dir");
	public MonkeyRun() {
		params = new MonkeyParams();
		execute = new ExecuteCommand(); 
	}
	
	@Test
	public void startMonkey() {
		String checkMonkey = "cmd /c adb shell \"ps|grep monkey\"";		
		try {	
			for(int i = 0; i < params.getSeed_list().length; i++) {				
				ReadWriteTxtFile.deleteFile(GetDevicesTop.path + params.getMonitorData() + params.getMemInfo());
				ReadWriteTxtFile.deleteFile(GetDevicesTop.path + params.getMonitorData() + params.getCpuInfo());
				execute.executeCommand("cmd /c del " + path + params.getWorkDataDir() + params.getMonkeyLog());
				
				execute.setUp(params.getPhoneDataDir(), params.getWorkDataDir(), params.getMonkeyLog(), params.getWhiteFile(), params.getBlackFile());
				command(i);		
				System.out.println("第" + (i+1)  + "次运行。。。");
				while(true) {				
					Thread.sleep(60000);
					Process process = Runtime.getRuntime().exec(checkMonkey);
					process.waitFor();
					BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	//				System.out.println(reader.readLine());
					if (reader.readLine() == null) {
						System.out.println("monkey已退出！");
						
						CreateChart.makeChart(i, params);
//						execute.executeCommand("cmd /c adb pull " + params.getAppPackage() + params.getMonkeyLog() + " " + GetDevicesTop.path + params.getChartReports());
						execute.executeCommand("cmd /c adb shell pm clear " + params.getAppPackage());
						break;
					} else {
	//					System.out.println("monkey运行中。。。");
						GetDevicesTop.GetMemory(params);
						GetDevicesTop.GetCPU(params);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}        
	}
	
	public void command(int i) {
/*		executeCommand("adb shell monkey --pkg-whitelist-file /storage/sdcard0/temp/whitelist.txt"
				+ " --throttle 500 -s 100 --ignore-crashes --ignore-timeouts --ignore-security-exceptions"
				+ " --ignore-native-crashes --monitor-native-crashes -v -v -v 15000"
				+ " 1> /storage/sdcard0/temp/monkey_test.txt 2>&1 &");
				
*/		
		String monkeyCommand = "cmd /c adb shell monkey --pkg-whitelist-file " + params.getPhoneDataDir() + params.getWhiteFile()
//						+ " --pkg-blacklist-file " + FILE_PATH + "blacklist.txt "
						+ " --throttle 500 -s " + params.getSeed_list()[i]
						+ " --ignore-crashes --hprof --ignore-timeouts --ignore-security-exceptions"
						+ " --ignore-native-crashes --monitor-native-crashes -v -v -v " + params.getEvent_num()
						+ " 1> " + path + params.getWorkDataDir() + params.getMonkeyLog() + " 2>&1 &";
	
		execute.executeCommand(monkeyCommand);		
	}
}
