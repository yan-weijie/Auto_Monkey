package com.sun.demo;

public class ExecuteCommand {
	
	public String phoneDataDir;
	
	public String whiteFile;
	
//	@BeforeClass
	public void setUp(String phoneDataDir, String workDataDir, String monkeyLog, String whiteFile, String blackFile) {
		this.phoneDataDir = phoneDataDir;
		this.whiteFile = whiteFile;
		
		String path = System.getProperty("user.dir");
		
		// push白名单文件到测试机
		executeCommand("adb push " + path + workDataDir + whiteFile + " " + phoneDataDir);
		
		// push黑名单文件到测试机
//		executeCommand("adb push " + path + "\\data\\backList.txt " + "/storage/sdcard0/temp/");
		
		// 设置输入法为appium android input
		executeCommand("adb shell ime set io.appium.android.ime/.UnicodeIME");
		
		// 使用uiautomator登录app
		
		executeCommand("adb push " + path + workDataDir + "AllinLogin.jar " + phoneDataDir);
		
		executeCommand("adb shell uiautomator runtest " + phoneDataDir + "AllinLogin.jar -c com.sun.demo.UiAutomatorLogin");
		executeCommand("adb shell ime set com.sohu.inputmethod.sogou/.SogouIME");
		try {			
			Thread.sleep(10000);			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
    public void executeCommand(String command) {
        try{
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec(command);
            System.out.println("command = " + command);
            try {
                if (proc.waitFor() != 0) {
                    System.err.println("exit value = " + proc.exitValue());
                }
//                BufferedReader reader = new BufferedReader(new InputStreamReader(
//                        proc.getInputStream()));
//                String line = null;
//                while ((line = reader.readLine()) != null) {                    
//                    System.out.println(line.toString());
//                }          
            } catch (InterruptedException e) {
                System.err.println(e);
            } finally {
                try {
                    proc.destroy();
                } catch (Exception e2) {}
            }
             
        } catch (Exception StringIndexOutOfBoundsException) {
            System.out.println("请检查设备是否连接");    
        }
    }   
}
