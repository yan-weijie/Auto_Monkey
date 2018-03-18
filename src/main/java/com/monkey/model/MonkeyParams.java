package com.monkey.model;

import com.monkey.util.Dom4jXml;

public class MonkeyParams {
	
	// 事件数量
	public String event_num = "200000";

	// log级别，一个-v增加一个级别，越多越详细，默认级别0
	public String log_level = "-v -v -v ";
	
	// 指定产生随机事件种子值，相同的种子值产生相同的事件序列。
	public int[] seed_list = {600, 500, 400, 300, 200, 100};
	
	public String appPackage;

	public String phoneDataDir;
	
	public String workDataDir;
	
	public String monkeyLog;
	
	public String blackFile;
	
	public String whiteFile;
	
	public String chartReports;
	
	public String monitorData;
	
	public String memInfo;
	
	public String cpuInfo;
	
	public String memInfoTemp;
	
	public String cpuInfoTemp;

	public String getEvent_num() {
		return event_num;
	}

	public String getLog_level() {
		return log_level;
	}

	public int[] getSeed_list() {
		return seed_list;
	}

	public String getAppPackage() {
		return Dom4jXml.getValue("appPackage");
	}

	public String getPhoneDataDir() {
		return Dom4jXml.getValue("phoneDataDir");
	}

	public String getWorkDataDir() {
		return Dom4jXml.getValue("workDataDir");
	}

	public String getMonkeyLog() {
		return Dom4jXml.getValue("monkeyLog");
	}

	public String getBlackFile() {
		return Dom4jXml.getValue("blackFile");
	}

	public String getWhiteFile() {
		return Dom4jXml.getValue("whiteFile");
	}

	public String getChartReports() {
		return Dom4jXml.getValue("chartReports");
	}

	public String getMonitorData() {
		return Dom4jXml.getValue("monitorData");
	}

	public String getMemInfo() {
		return Dom4jXml.getValue("memInfo");
	}

	public String getCpuInfo() {
		return Dom4jXml.getValue("cpuInfo");
	}

	public String getMemInfoTemp() {
		return Dom4jXml.getValue("memInfoTemp");
	}

	public String getCpuInfoTemp() {
		return Dom4jXml.getValue("cpuInfoTemp");
	}

}
