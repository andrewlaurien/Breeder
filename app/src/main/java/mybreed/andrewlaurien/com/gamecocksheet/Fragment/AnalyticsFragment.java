package mybreed.andrewlaurien.com.gamecocksheet.Fragment;


import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

import mybreed.andrewlaurien.com.gamecocksheet.Custom.DayAxisValueFormatter;
import mybreed.andrewlaurien.com.gamecocksheet.Custom.MyAxisValueFormatter;
import mybreed.andrewlaurien.com.gamecocksheet.Custom.MyValueFormatter;
import mybreed.andrewlaurien.com.gamecocksheet.Custom.XYMarkerView;
import mybreed.andrewlaurien.com.gamecocksheet.MainActivity;
import mybreed.andrewlaurien.com.gamecocksheet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnalyticsFragment extends Fragment implements OnChartValueSelectedListener {
    public AnalyticsFragment() {
        // Required empty public constructor
    }

    View v;

    private static String TAG = "Analytics";

    BarChart mChart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_analytics, container, false);

        mChart = v.findViewById(R.id.piechart1);

        mChart.setOnChartValueSelectedListener(this);

        mChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);

        mChart.setDrawValueAboveBar(false);
        mChart.setHighlightFullBarEnabled(false);

        // change the position of the y-labels
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setValueFormatter(new MyAxisValueFormatter());
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setTextColor(Color.WHITE);
        mChart.getAxisRight().setEnabled(false);


        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart);


        XAxis xLabels = mChart.getXAxis();
        xLabels.setPosition(XAxis.XAxisPosition.TOP);
        xLabels.setTextColor(Color.WHITE);

        // mChart.setDrawXLabels(false);
        // mChart.setDrawYLabels(false);

        // setting data


        XYMarkerView mv = new XYMarkerView(getActivity(), xAxisFormatter);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);
        setData();


        return v;

    }

    protected RectF mOnValueSelectedRectF = new RectF();


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;


        RectF bounds = mOnValueSelectedRectF;
        mChart.getBarBounds((BarEntry) e, bounds);
        MPPointF position = mChart.getPosition(e, YAxis.AxisDependency.LEFT);

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        Log.i("x-index",
                "low: " + mChart.getLowestVisibleX() + ", high: "
                        + mChart.getHighestVisibleX());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {

    }

    private void setData() {


        try {

            ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

            for (int i = 0; i < MainActivity.myFights.size(); i++) {

                yVals1.add(new BarEntry(i,
                        new float[]{MainActivity.myFights.get(i).getFloatAmountWin(),
                                MainActivity.myFights.get(i).getPrizeFloat()},
                        MainActivity.myFights.get(i)));
            }

            final ArrayList<String> xAxisLabel = new ArrayList<>();
            for (int i = 0; i < MainActivity.myFights.size(); i++) {
                xAxisLabel.add(MainActivity.myFights.get(i).getFightDate());
            }

            XAxis xAxis = mChart.getXAxis();
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return xAxisLabel.get((int) value);
                }
            });

            BarDataSet set1;

            if (mChart.getData() != null &&
                    mChart.getData().getDataSetCount() > 0) {
                set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
                set1.setValues(yVals1);
                mChart.getData().notifyDataChanged();
                mChart.notifyDataSetChanged();
            } else {
                set1 = new BarDataSet(yVals1, "Fight Record");
                set1.setDrawIcons(false);
                set1.setColors(getColors());
                set1.setStackLabels(new String[]{"Won", "Prize"});
                set1.setValueTextColor(Color.WHITE);

                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                dataSets.add(set1);

                BarData data = new BarData(dataSets);
                data.setValueFormatter(new MyValueFormatter());
                data.setValueTextColor(Color.WHITE);

                mChart.setData(data);
            }

            mChart.setFitBars(true);
            mChart.invalidate();

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Please check you data " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private int[] getColors() {

        int stacksize = 2;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        for (int i = 0; i < colors.length; i++) {
            colors[i] = ColorTemplate.MATERIAL_COLORS[i];
        }

        return colors;
    }

}
