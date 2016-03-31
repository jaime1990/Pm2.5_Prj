package com.goertek.pmdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.goertek.pmdemo.R;
import com.goertek.pmdemo.view.ChartView;

/**
 * Description:
 *
 * @author : jaime
 * @email : appeal1990@hotmail.com
 * @since : 16-3-30
 */
public class Pm25Activity extends Activity {

    private RelativeLayout chart_line;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trend_chart);
        chart_line = (RelativeLayout) findViewById(R.id.static_chart_line_layout);
        ChartView cView = new ChartView(this, chart_line);
        cView.initChart();
    }


}
