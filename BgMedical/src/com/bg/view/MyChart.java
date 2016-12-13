/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bg.view;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.util.MathHelper;

import com.bg.utils.Constant;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

/**
 * Average temperature demo chart.
 */
public class MyChart extends AbstractDemoChart {
  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "Average temperature";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "The average temperature in 4 Greek islands (line chart)";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public Intent execute(Context context) {
    return null;
  }

//  将achartengine view加入到ScrollView中时注意：
//  1.在achartengine的renderer加入一行代码：
//  renderer.setInScroll(true);
//  2.ScrollView的布局加入以下设置：
//  android:fillViewport="true"
//  3.ScrollView的height要设成fill_parent
//  空值 MathHelper.NULL_VALUE,
  
  public GraphicalView executeView_9city(Context context,String[] titles,String[] names,List<double[]> values,
		  double[] x_y_max) {  
	     // 设置属性,具体代码
//	  String[] titles = new String[] { "Crete", "Corfu", "Thassos", "Skiathos" };  // 标题
	    List<double[]> x = new ArrayList<double[]>();
	    for (int i = 0; i < titles.length; i++) {
	    	double[] x_value = new double[22];
	    	for (int j = 0; j < 22; j++) {
	    		x_value[j] = j;
			}
	      x.add(x_value); // 每个序列中点的X坐标  
	    }
//	    List<double[]> values = new ArrayList<double[]>();
//	    values.add(Constant.boy_height_SD2neg_9);
//	    values.add(Constant.boy_height_SD1neg_9);
//	    values.add(Constant.boy_height_SD1_9);
//	    values.add(Constant.boy_height_SD2_9);
	    int[] colors = new int[] { Color.RED, Color.GREEN, Color.GREEN, Color.RED}; //每个序列的颜色设置  
	    PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.CIRCLE,  
	        PointStyle.CIRCLE, PointStyle.CIRCLE }; //每个序列中点的形状设置
	    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles); //调用AbstractDemoChart中的方法设置renderer.  
	    
	    renderer.setApplyBackgroundColor(true);//设置背景色
//	    renderer.setBackgroundColor(Color.GRAY);
//	    renderer.setMarginsColor(Color.GRAY); // 外部背景色
	    
	    renderer.setBackgroundColor(Color.parseColor("#f3f3f3"));  
        // 图表与屏幕四边的间距颜色  
        renderer.setMarginsColor(Color.argb(0xF3, 0xF3, 0xF3, 0xF3));  
        renderer.setChartTitleTextSize(40);  
        renderer.setAxisTitleTextSize(25); 
        // renderer.setLegendHeight(50);  
        // 图例文字的大小  
        renderer.setLegendTextSize(20);  
        renderer.setMargins(new int[] { 50, 50, 50, 30 });  
        
        renderer.setInScroll(true);
        // x、y轴上刻度颜色  
        renderer.setXLabelsColor(Color.BLACK);  
        renderer.setYLabelsColor(0, Color.BLACK);  
        
	    int length = renderer.getSeriesRendererCount();
	    for (int i = 0; i < length; i++) {
	      ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true); //设置图上的点为实心  
	    }
	    // 最后两个参数代表轴的颜色和轴标签的颜色  
//	    setChartSettings(renderer, "Average temperature", "Month", "Temperature", 0, 10, 0, 140,
//	        Color.LTGRAY, Color.LTGRAY); //调用AbstractDemoChart中的方法设置图表的renderer属性.
	    setChartSettings(renderer, names[0], names[1],names[2], 0, 22, 0, x_y_max[1],
		        Color.BLACK, Color.BLACK);
//	    renderer.setXLabels(12); //设置x轴显示12个点,根据setChartSettings的最大值和最小值自动计算点的间隔  
	    renderer.setYLabels(10); //设置y轴显示10个点,根据setChartSettings的最大值和最小值自动计算点的间隔  
	    
//	    0—3天,1月,2月,3月,4月 ,5月,6月,8月,10月,12月,15月,18月,21月,2岁,2.5岁,3岁,3.5岁,4岁,4.5岁,5岁,5.5岁,6-7岁
	    renderer.setXLabels(0); 
//	    renderer.setXLabelsAngle(-60f);
	
	    renderer.addXTextLabel(0, "0—3天");
	    renderer.addXTextLabel(1, "1月");
	    renderer.addXTextLabel(2, "2月");
	    renderer.addXTextLabel(3, "3月");
	    renderer.addXTextLabel(4, "4月");
	    renderer.addXTextLabel(5, "5月");
	    renderer.addXTextLabel(6, "6月");
	    renderer.addXTextLabel(7, "8月");
	    renderer.addXTextLabel(8, "10月");
	    renderer.addXTextLabel(9, "12月");
	    renderer.addXTextLabel(10, "15月");
	    renderer.addXTextLabel(11, "18月");
	    renderer.addXTextLabel(12, "21月");
	    renderer.addXTextLabel(13, "2岁");
	    renderer.addXTextLabel(14, "2.5岁");
	    renderer.addXTextLabel(15, "3岁");
	    renderer.addXTextLabel(16, "3.5岁");
	    renderer.addXTextLabel(17, "4岁");
	    renderer.addXTextLabel(18, "4.5岁");
	    renderer.addXTextLabel(19, "5岁");
	    renderer.addXTextLabel(20, "5.5岁");
	    renderer.addXTextLabel(21, "6-7岁");
	   
	    renderer.setShowGrid(true); //是否显示网格  
	    renderer.setXLabelsAlign(Align.CENTER);//刻度线与刻度标注之间的相对位置关系  
	    renderer.setYLabelsAlign(Align.RIGHT);//刻度线与刻度标注之间的相对位置关系 
//	    renderer.setZoomButtonsVisible(true); //是否显示放大缩小按钮
	    renderer.setZoomEnabled(false, false);
//	    renderer.setPanLimits(new double[] { 0, 22, 0, 140 }); //设置拖动时X轴Y轴允许的最大值最小值.  
//	    renderer.setZoomLimits(new double[] { 0, 22, 0, 140 }); //设置放大缩小时X轴Y轴允许的最大最小值.  
	    renderer.setPanLimits(new double[] { 0, 22, 0, x_y_max[1] }); //设置拖动时X轴Y轴允许的最大值最小值.  
	    renderer.setZoomLimits(new double[] { 0, 22, 0, x_y_max[1] }); //设置放大缩小时X轴Y轴允许的最大最小值.  

	    XYMultipleSeriesDataset dataset = buildDataset(titles, x, values);
	    XYSeries series = dataset.getSeriesAt(0);
//	    series.addAnnotation("Vacation", 6, 30);
	    
	     GraphicalView view = ChartFactory.getLineChartView(context, dataset, renderer);  
	     return view;  
	     }  
  
  
  public GraphicalView executeView(Context context) {  
	     // 设置属性,具体代码
	  String[] titles = new String[] { "Crete", "Corfu", "Thassos", "Skiathos" };  // 标题
	    List<double[]> x = new ArrayList<double[]>();
	    for (int i = 0; i < titles.length; i++) {
//	      x.add(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 }); // 每个序列中点的X坐标 
	    x.add(Constant.month); // 每个序列中点的X坐标  
	    }
	    List<double[]> values = new ArrayList<double[]>();
	    values.add(Constant.boy_height_SD2neg);
	    values.add(Constant.boy_height_SD1neg);
	    values.add(Constant.boy_height_SD1);
	    values.add(Constant.boy_height_SD2);
	    int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.CYAN, Color.RED }; //每个序列的颜色设置  
//	    PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND,  
//	        PointStyle.TRIANGLE, PointStyle.SQUARE }; //每个序列中点的形状设置
	    PointStyle[] styles = new PointStyle[] { PointStyle.POINT, PointStyle.POINT,  
		        PointStyle.POINT, PointStyle.POINT }; //每个序列中点的形状设置
	    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles); //调用AbstractDemoChart中的方法设置renderer.  
	    
	    renderer.setApplyBackgroundColor(true);//设置背景色
//	    renderer.setBackgroundColor(Color.GRAY);
//	    renderer.setMarginsColor(Color.GRAY); // 外部背景色
	    
	    renderer.setBackgroundColor(Color.parseColor("#f3f3f3"));  
     // 图表与屏幕四边的间距颜色  
     renderer.setMarginsColor(Color.argb(0xF3, 0xF3, 0xF3, 0xF3));  
     renderer.setChartTitleTextSize(30);  
     renderer.setAxisTitleTextSize(25);  
     // renderer.setLegendHeight(50);  
     // 图例文字的大小  
     renderer.setLegendTextSize(20);  
     renderer.setMargins(new int[] { 50, 50, 50, 30 });  
     
     renderer.setInScroll(true);
     // x、y轴上刻度颜色  
     renderer.setXLabelsColor(Color.BLACK);  
     renderer.setYLabelsColor(0, Color.BLACK);  
     
	    int length = renderer.getSeriesRendererCount();
	    for (int i = 0; i < length; i++) {
	      ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true); //设置图上的点为实心  
	    }
	    // 最后两个参数代表轴的颜色和轴标签的颜色  
	    setChartSettings(renderer, "Average temperature", "Month", "Temperature", 0, 72.0, 0, 150.0,
	        Color.LTGRAY, Color.LTGRAY); //调用AbstractDemoChart中的方法设置图表的renderer属性.
	    renderer.setXLabels(8); //设置x轴显示12个点,根据setChartSettings的最大值和最小值自动计算点的间隔  
	    renderer.setYLabels(15); //设置y轴显示10个点,根据setChartSettings的最大值和最小值自动计算点的间隔  
	    renderer.setShowGrid(true); //是否显示网格  
	    renderer.setXLabelsAlign(Align.CENTER);//刻度线与刻度标注之间的相对位置关系  
	    renderer.setYLabelsAlign(Align.RIGHT);//刻度线与刻度标注之间的相对位置关系 
//	    renderer.setZoomButtonsVisible(true); //是否显示放大缩小按钮
	    renderer.setZoomEnabled(false, false);
	    renderer.setPanLimits(new double[] { 0, 80, 0, 150 }); //设置拖动时X轴Y轴允许的最大值最小值.  
	    renderer.setZoomLimits(new double[] { 0, 80, 0, 150 }); //设置放大缩小时X轴Y轴允许的最大最小值.  

	    XYMultipleSeriesDataset dataset = buildDataset(titles, x, values);
	    XYSeries series = dataset.getSeriesAt(0);
//	    series.addAnnotation("Vacation", 6, 30);
	    
	     GraphicalView view = ChartFactory.getLineChartView(context, dataset, renderer);  
	     return view;  
	     }  
  
  public GraphicalView executeView_who(Context context,String[] titles,String[] names,List<double[]> values,
		  double[] x_y_max,Boolean month ) {  
	     // 设置属性,具体代码
//	  String[] titles = new String[] { "Crete", "Corfu", "Thassos", "Skiathos" };  // 标题
	    List<double[]> x = new ArrayList<double[]>();
	    for (int i = 0; i < titles.length; i++) {
//	      x.add(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 }); // 每个序列中点的X坐标 
	    if (month) {
	    	x.add(Constant.month_head); // 每个序列中点的X坐标  
		}else {
			x.add(Constant.month); // 每个序列中点的X坐标  
		}
	    	
	    }
//	    List<double[]> values = new ArrayList<double[]>();
//	    values.add(Constant.boy_height_SD2neg);
//	    values.add(Constant.boy_height_SD1neg);
//	    values.add(Constant.boy_height_SD1);
//	    values.add(Constant.boy_height_SD2);
	    int[] colors = new int[] { Color.RED, Color.GREEN, Color.GREEN, Color.RED }; //每个序列的颜色设置  
//	    PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND,  
//	        PointStyle.TRIANGLE, PointStyle.SQUARE }; //每个序列中点的形状设置
	    PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.CIRCLE,  
		        PointStyle.CIRCLE, PointStyle.CIRCLE }; //每个序列中点的形状设置
	    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles); //调用AbstractDemoChart中的方法设置renderer.  
	    
	    renderer.setApplyBackgroundColor(true);//设置背景色
//	    renderer.setBackgroundColor(Color.GRAY);
//	    renderer.setMarginsColor(Color.GRAY); // 外部背景色
	    
	    renderer.setBackgroundColor(Color.parseColor("#f3f3f3"));  
  // 图表与屏幕四边的间距颜色  
  renderer.setMarginsColor(Color.argb(0xF3, 0xF3, 0xF3, 0xF3));  
  renderer.setChartTitleTextSize(40);  
  renderer.setAxisTitleTextSize(25);  
  // renderer.setLegendHeight(50);  
  // 图例文字的大小  
  renderer.setLegendTextSize(20);  
  renderer.setMargins(new int[] { 50, 50, 50, 30 });  
  
  renderer.setInScroll(true);
  // x、y轴上刻度颜色  
  renderer.setXLabelsColor(Color.BLACK);  
  renderer.setYLabelsColor(0, Color.BLACK);  
  
	    int length = renderer.getSeriesRendererCount();
	    for (int i = 0; i < length; i++) {
	      ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true); //设置图上的点为实心  
	    }
	    // 最后两个参数代表轴的颜色和轴标签的颜色  
//	    setChartSettings(renderer, "Average temperature", "Month", "Temperature", 0, 72.0, 0, 150.0,
//	        Color.LTGRAY, Color.LTGRAY); //调用AbstractDemoChart中的方法设置图表的renderer属性.
	    setChartSettings(renderer, names[0], names[1],names[2], 0, x_y_max[0], 0, x_y_max[1],
		        Color.BLACK, Color.BLACK);
	    renderer.setXLabels(8); //设置x轴显示12个点,根据setChartSettings的最大值和最小值自动计算点的间隔  
	    renderer.setYLabels((int) (x_y_max[1]/10)); //设置y轴显示10个点,根据setChartSettings的最大值和最小值自动计算点的间隔  
	    renderer.setShowGrid(true); //是否显示网格  
	    renderer.setXLabelsAlign(Align.CENTER);//刻度线与刻度标注之间的相对位置关系  
	    renderer.setYLabelsAlign(Align.RIGHT);//刻度线与刻度标注之间的相对位置关系 
//	    renderer.setZoomButtonsVisible(true); //是否显示放大缩小按钮
	    renderer.setZoomEnabled(false, false);
	    renderer.setPanLimits(new double[] { 0, x_y_max[0], 0, x_y_max[1] }); //设置拖动时X轴Y轴允许的最大值最小值.  
	    renderer.setZoomLimits(new double[] { 0, x_y_max[0], 0, x_y_max[1] }); //设置放大缩小时X轴Y轴允许的最大最小值.  

	    XYMultipleSeriesDataset dataset = buildDataset(titles, x, values);
	    XYSeries series = dataset.getSeriesAt(0);
//	    series.addAnnotation("Vacation", 6, 30);
	    
	     GraphicalView view = ChartFactory.getLineChartView(context, dataset, renderer);  
	     return view;  
	     }  
}
