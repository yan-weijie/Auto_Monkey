package com.monkey.GenChart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.monkey.model.MonkeyParams;
import com.monkey.util.ReadWriteTxtFile;
import com.sun.demo.GetDevicesTop;

public class CreateChart {
	private static String memInfo;  
	private static String cpuInfo;  
	private static String chartReports;  
	private static List<String> dataList = new LinkedList<String>();
	private static List<String> timeList = new LinkedList<String>();
	private static String path = System.getProperty("user.dir");
	
//	@Test
//	public void f() {
//		makeChart(1, "");
//	}
	
	
	public static void makeChart(int num, MonkeyParams params) {
		path += params.getMonitorData();
		CreateChart.chartReports = params.getChartReports();
		CreateChart.memInfo = params.getMemInfo();
		CreateChart.cpuInfo = params.getCpuInfo();
		makeMemoryChart(num);
		makeCpuChart(num);
	}
	
    /** 
     * 生成memory图表
     */  	
//	@Test 
    public static void makeMemoryChart(int num) {  
    	dataList.clear();
    	timeList.clear();
        operStr(path + memInfo);
        
        String[] columnKeys = new String[timeList.size()];							// 横轴数据
        double[] data = new double[dataList.size()];									// 折线数据
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        String series = "Memory";
               
        for (int i = 0; i < dataList.size(); i++) {
//        	System.out.println("dataList.get(i) = " + dataList.get(i));
        	data[i] = Double.valueOf(dataList.get(i));
        	columnKeys[i] = timeList.get(i).split(" ")[1];
        }

        for (int i = 0; i < data.length; i++) {
        	dataSet.addValue(data[i], series, columnKeys[i]);
        }
        
        createTimeXYChar("Memory", "时间", "KB", dataSet, "MemoryChart" + num +".png", 5000);  
    } 
   
	/**
	 * 生成cpu图表
	 */
//	@Test
    public static void makeCpuChart(int num) {  
    	dataList.clear();
    	timeList.clear();
        operStr(path + cpuInfo);
        
        String[] columnKeys = new String[timeList.size()];							// 横轴数据
        double[] data = new double[dataList.size()];									// 折线数据
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        String series = "CPU";
               
        for (int i = 0; i < dataList.size(); i++) {
//        	System.out.println("list.get(i) = " + memList.get(i));
        	data[i] = Double.valueOf(dataList.get(i).substring(0, dataList.get(i).length() - 1));
            columnKeys[i] = timeList.get(i).split(" ")[1];
        	
        }

        for (int i = 0; i < data.length; i++) {
        	dataSet.addValue(data[i], series, columnKeys[i]);
        }
        
        createTimeXYChar("CPU", "时间", "%", dataSet, "CPUChart" +num +".png", 10);  
    } 
	
	public static void operStr(String path) {
		System.out.println("path = " + path);
		String[] data = ReadWriteTxtFile.ReadTxtLine(path, ",");
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		for (String s:data) {
			if (!s.isEmpty()) {
				String[] temp = s.split("=");
				map.put(temp[0], temp[1]);
			}
		}
		
        Iterator<?> it = map.keySet().iterator(); 
        while(it.hasNext()){
        	String key = it.next().toString();   
        	timeList.add(key);
        	String value= map.get(key);  
        	dataList.add(value);
        }		
	}
   
    /** 
     * 折线图 
     *  
     * @param chartTitle 
     * @param x 
     * @param y 
     * @param xyDataset 
     * @param charName 
     * @return 
     */  
    public static String createTimeXYChar(String chartTitle, String x, String y,  
            CategoryDataset dataSet, String charName, int numberTick) {  
  	
    	JFreeChart chart = ChartFactory.createLineChart(chartTitle, x, y, dataSet, PlotOrientation.VERTICAL, true, true, false);
    	
    	
    	chart.getTitle().setFont(new Font("隶书", Font.BOLD, 20));                   // 设置标题字体
        chart.getLegend().setItemFont(new Font("宋书", Font.PLAIN, 15));             // 设置图例类别字体
        chart.setBackgroundPaint(Color.white);                                      // 设置背景色 
        
        //获取绘图区对象
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.black);                                       // 设置绘图区背景色
        plot.setRangeGridlinePaint(Color.WHITE);                                    // 设置水平方向背景线颜色
        plot.setRangeGridlinesVisible(true);                                        // 设置是否显示水平方向背景线,默认值为true
        plot.setDomainGridlinePaint(Color.WHITE);                                   // 设置垂直方向背景线颜色
        plot.setDomainGridlinesVisible(true);                                       // 设置是否显示垂直方向背景线,默认值为false

        // 设置X轴
        CategoryAxis domainAxis = plot.getDomainAxis();   
        domainAxis.setLabelFont(new Font("宋书", Font.PLAIN, 15));                    // 设置横轴字体
        domainAxis.setTickLabelFont(new Font("宋书", Font.PLAIN, 15));                // 设置坐标轴标尺值字体
        domainAxis.setLowerMargin(0.01);                                             // 左边距 边框距离
        domainAxis.setUpperMargin(0.06);                                             // 右边距 边框距离,防止最后边的一个数据靠近了坐标轴。
        domainAxis.setMaximumCategoryLabelLines(10);
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);        // 横轴 lable 的位置 横轴上的 Lable 45度倾斜 DOWN_45
        
        // 设置Y轴
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLabelFont(new Font("宋书", Font.PLAIN, 15)); 
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());         // Y轴显示整数
        rangeAxis.setAutoRangeMinimumSize(1);                                        // 最小跨度
        rangeAxis.setUpperMargin(0.18);                                              // 上边距,防止最大的一个数据靠近了坐标轴。   
        rangeAxis.setLowerBound(0);                                                  // 最小值显示0
//        rangeAxis.setUpperBound(maxNum);											 // 最大值显示
        rangeAxis.setAutoRange(true);                                                // 自动分配Y轴数据
        rangeAxis.setTickMarkStroke(new BasicStroke(1.6f));                          // 设置坐标标记大小
        rangeAxis.setTickMarkPaint(Color.BLACK);                                     // 设置坐标标记颜色
        rangeAxis.setTickUnit(new NumberTickUnit(numberTick));                       // 每10个刻度显示一个刻度值



        // 获取折线对象
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();    // 设置实线
        BasicStroke realLine = new BasicStroke(1.8f);                                 // 设置实线

        float dashes[] = { 5.0f }; 
        BasicStroke brokenLine = new BasicStroke(5.2f,                                // 线条粗细
                BasicStroke.CAP_ROUND,                                                // 端点风格
                BasicStroke.JOIN_ROUND,                                               // 折点风格
                8f, dashes, 0.6f); 
        for (int i = 0; i < dataSet.getRowCount(); i++) {
            if (i % 2 == 0) {
                renderer.setSeriesStroke(i, realLine);                                // 利用实线绘制
            	renderer.setSeriesPaint(i, new Color(255, 255, 0));
            }
            else
                renderer.setSeriesStroke(i, brokenLine);                              // 利用虚线绘制
        }

        plot.setNoDataMessage("无对应的数据，请重新查询。");
        plot.setNoDataMessageFont(new Font("宋书", Font.PLAIN, 15));                   // 字体的大小
        plot.setNoDataMessagePaint(Color.RED);                                        // 字体颜色
    	
        FileOutputStream out = null;  
        try {  
        	ReadWriteTxtFile.isFolderExist(path + chartReports);  
            String chartName = path + chartReports + charName;  
            out = new FileOutputStream(chartName);  
  
            
//            ChartUtilities.writeChartAsJPEG(out, chart, 500, 510);  			  // 将报表保存为png文件  
            ChartUtilities.writeChartAsPNG(out, chart, 2000, 1200);
            return chartName;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        } finally {  
            try {  
            	out.close();  
                System.out.println("create time-createTimeXYChar.");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        
        
    }
}
