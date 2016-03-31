package com.goertek.pmdemo.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.Random;

/**
 * Description:折线图View
 *
 * @author : jaime
 * @email : appeal1990@hotmail.com
 * @since : 16-3-29
 */
public class ChartView {

    // 用于存放每条折线的点数据
    private XYSeries line;
    // 用于存放所有需要绘制的XYSeries
    private XYMultipleSeriesDataset mDataset;
    // 用于存放每条折线的风格
    private XYSeriesRenderer renderer;
    // 用于存放所有需要绘制的折线的风格
    private XYMultipleSeriesRenderer mXYMultipleSeriesRenderer;
    private RelativeLayout sLayout;
    private GraphicalView chart;
    private Context context;

    public ChartView(Context ctx, RelativeLayout rl) {
        this.context = ctx;
        this.sLayout = rl;
        initChart();
    }

    public void initChart() {
        // 初始化时，必须保证XYMultipleSeriesDataset对象中的XYSeries数量和
        // XYMultipleSeriesRenderer对象中的XYSeriesRenderer数量一样多
        line = new XYSeries("");
        renderer = new XYSeriesRenderer();
        mDataset = new XYMultipleSeriesDataset();
        mXYMultipleSeriesRenderer = new XYMultipleSeriesRenderer();

        // 对XYSeries和XYSeriesRenderer的对象的参数赋值
        initLine(line);
        initRenderer(renderer, Color.BLUE, PointStyle.CIRCLE, true);

        // 将XYSeries对象和XYSeriesRenderer对象分别添加到XYMultipleSeriesDataset对象和XYMultipleSeriesRenderer对象中。
        mDataset.addSeries(line);
        mXYMultipleSeriesRenderer.addSeriesRenderer(renderer);

        // 配置chart参数
        setChartSettings(mXYMultipleSeriesRenderer, "X", "Y", 1, 7, 0, 500,
                Color.RED, Color.RED);

        // 通过该函数获取到一个View 对象
        chart = ChartFactory.getLineChartView(context, mDataset,
                mXYMultipleSeriesRenderer);

        // 将该View 对象添加到layout中。
        sLayout.addView(chart, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
    }

    private void initLine(XYSeries series) {
        // 随机生成两组数据
        Random r = new Random();
        for (int i = 1; i < 7; i++) {
            series.add(i, r.nextInt(500)%301+200);
        }
    }

    private XYSeriesRenderer initRenderer(XYSeriesRenderer renderer, int color,
                                          PointStyle style, boolean fill) {
        // 设置图表中曲线本身的样式，包括颜色、点的大小以及线的粗细等
        renderer.setColor(color);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setFillPoints(fill);
        renderer.setLineWidth(1);
        return renderer;
    }

    protected void setChartSettings(
            XYMultipleSeriesRenderer mXYMultipleSeriesRenderer, String xTitle,
            String yTitle, double xMin, double xMax, double yMin, double yMax,
            int axesColor, int labelsColor) {
        // 有关对图表的渲染可参看api文档
        mXYMultipleSeriesRenderer.setXTitle(xTitle);
        mXYMultipleSeriesRenderer.setYTitle(yTitle);
        mXYMultipleSeriesRenderer.setXAxisMin(xMin);
        mXYMultipleSeriesRenderer.setAxisTitleTextSize(20);
        mXYMultipleSeriesRenderer.setChartTitleTextSize(20);
        mXYMultipleSeriesRenderer.setLegendTextSize(20);
        mXYMultipleSeriesRenderer.setLabelsTextSize(15);
        mXYMultipleSeriesRenderer.setXAxisMax(xMax);
        mXYMultipleSeriesRenderer.setYAxisMin(yMin);
        mXYMultipleSeriesRenderer.setYAxisMax(yMax);
        mXYMultipleSeriesRenderer.setDisplayValues(true);
        mXYMultipleSeriesRenderer.setPanEnabled(true, false);
        mXYMultipleSeriesRenderer.setAxesColor(axesColor);
        mXYMultipleSeriesRenderer.setLabelsColor(labelsColor);
        mXYMultipleSeriesRenderer.setXLabelsAlign(Align.RIGHT);
        mXYMultipleSeriesRenderer.setYLabelsAlign(Align.RIGHT);
        mXYMultipleSeriesRenderer.setShowGrid(false);
        mXYMultipleSeriesRenderer.setGridColor(Color.GRAY);
        mXYMultipleSeriesRenderer.setXTitle("日期");
        mXYMultipleSeriesRenderer.setYTitle("PM2.5");
        mXYMultipleSeriesRenderer.setXLabels(10);
        mXYMultipleSeriesRenderer.setYLabels(10);
        mXYMultipleSeriesRenderer.setPointSize((float) 5);
        mXYMultipleSeriesRenderer.setShowLegend(true);
        mXYMultipleSeriesRenderer.setZoomEnabled(true, false);
        mXYMultipleSeriesRenderer.setFitLegend(true);
        mXYMultipleSeriesRenderer.setBackgroundColor(Color.WHITE);// 背景色
        mXYMultipleSeriesRenderer.setMarginsColor(Color.WHITE);// 边距
        mXYMultipleSeriesRenderer.setApplyBackgroundColor(true);
    }
}
