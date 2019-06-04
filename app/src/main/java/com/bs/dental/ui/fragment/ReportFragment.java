package com.bs.dental.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bs.dental.R;
import com.bs.dental.model.PatientTurnChartItem;
import com.bs.dental.model.ProductChartObject;
import com.bs.dental.networking.RetroClientTurn;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.utils.XAxisBarValueFormatter;
import com.facebook.CallbackManager;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mmkr on 5/9/2019.
 */
public class ReportFragment extends BaseFragment implements View.OnClickListener {

    private CallbackManager callbackManager;

    @Inject
    PreferenceService preferenceService;
    private View rootView;
    // private LineChart chart_line;
    private HorizontalBarChart chart_bar_horizontal;
    private BarChart chart_bar;
    private PieChart chart_pie;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_report, container, false);
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        getActivity().setTitle(getString(R.string.login));


        //chart_line = (LineChart) view.findViewById(R.id.chart_line);
        //chart_bar_horizontal = (HorizontalBarChart) view.findViewById(R.id.chart_bar_horizontal);
        chart_bar = (BarChart) view.findViewById(R.id.chart_bar);
        chart_bar.setDrawBarShadow(false);
        chart_bar.setDrawValueAboveBar(true);
        chart_bar.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart_bar.setMaxVisibleValueCount(60);
        // scaling can now only be done on x- and y-axis separately
        chart_bar.setPinchZoom(false);
        chart_bar.setDrawGridBackground(false);
        // chart.setDrawYLabels(false);
        YAxis leftAxis = chart_bar.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        chart_pie = (PieChart) view.findViewById(R.id.chart_pie);
        chart_pie.setUsePercentValues(false);
        chart_pie.getDescription().setEnabled(false);
        //chart_pie.setExtraOffsets(5, 10, 5, 5);
        chart_pie.setDragDecelerationFrictionCoef(0.95f);
        //chart_pie.setCenterTextTypeface(tfLight);
        //chart_pie.setCenterText(generateCenterSpannableText());
        chart_pie.setDrawHoleEnabled(true);
        chart_pie.setHoleColor(Color.WHITE);
        chart_pie.setTransparentCircleColor(Color.WHITE);
        chart_pie.setTransparentCircleAlpha(110);
        //chart_pie.setHoleRadius(58f);
        chart_pie.setTransparentCircleRadius(15f);
        chart_pie.setDrawCenterText(true);
        chart_pie.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart_pie.setRotationEnabled(true);
        chart_pie.setHighlightPerTapEnabled(true);
        chart_pie.setHoleRadius(25);

        Legend l = chart_pie.getLegend();
        //l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        chart_pie.getLegend().setWordWrapEnabled(true);
        chart_pie.setDrawEntryLabels(false);

        callTurnPatientChartWebservice();
    }

    // TODO: 5/8/2019 --- mmkr : turn api for densyx features
    public void callTurnPatientChartWebservice() {
        RetroClientTurn.getApi().getPatientTurnsChart(preferenceService.GetPreferenceValue(PreferenceService.COOKIE_TURN)).enqueue(new Callback<List<PatientTurnChartItem>>() {
            @Override
            public void onResponse(Call<List<PatientTurnChartItem>> call, Response<List<PatientTurnChartItem>> response) {
                if (response.isSuccessful()) {
                    List<PatientTurnChartItem> pol = response.body();
                    //Toast.makeText(getActivity(), "Login_Doctor : SUCCESS \n" + lrt.getMessage(), Toast.LENGTH_LONG).show();
                    for (int i=0; i < pol.size(); i++) {
                        Log.d("PATIENT_CHART", i + " - " + pol.get(i).getPersianDateTime()+ "  :  " + pol.get(i).getCount());
                    }

                    //setTurnPatientData_lineChart(pol);
                    setTurnPatientData_barChart(pol);
                    callTurnProductsChartWebservice();

                } else {
                    Log.d("PATIENT_CHART", "PATIENT_CHART : ERROR : " + response.errorBody().toString());
                    Toast.makeText(getActivity(), "PATIENT_CHART : ERROR \n" + response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<PatientTurnChartItem>> call, Throwable t) {
            }
        });
    }


    // TODO: 5/8/2019 --- mmkr : turn api for densyx features
    public void callTurnProductsChartWebservice() {
        RetroClientTurn.getApi().getCountProductsChart(preferenceService.GetPreferenceValue(PreferenceService.COOKIE_TURN)).enqueue(new Callback<List<ProductChartObject>>() {
            @Override
            public void onResponse(Call<List<ProductChartObject>> call, Response<List<ProductChartObject>> response) {
                if (response.isSuccessful()) {
                    List<ProductChartObject> pco = response.body();
                    //Toast.makeText(getActivity(), "Login_Doctor : SUCCESS \n" + lrt.getMessage(), Toast.LENGTH_LONG).show();
                    for (int i=0; i < pco.size(); i++) {
                        Log.d("PRODUCT_CHART", i + " - " + pco.get(i).getName()+ "  :  " + pco.get(i).getCount());
                    }

                    setTurnProductData_pieChart(pco);

                } else {
                    Log.d("PRODUCT_CHART", "PRODUCT_CHART : ERROR : " + response.errorBody().toString());
                    Toast.makeText(getActivity(), "PRODUCT_CHART : ERROR \n" + response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductChartObject>> call, Throwable t) {
            }
        });
    }

    //*** set bar chart for patient data
    private void setTurnPatientData_barChart(List<PatientTurnChartItem> pol) {

        ArrayList<BarEntry> values = new ArrayList<>();

        final String[] XLabels = new String[pol.size()];
        for (int i = 0; i < pol.size(); i++) {
            XLabels[i] = pol.get(i).getPersianDateTime();
            values.add(new BarEntry(Float.valueOf(i), Float.valueOf(pol.get(i).getCount()))); // add one entry per hour
        }

        XAxisBarValueFormatter formatter = new XAxisBarValueFormatter(pol);

        XAxis xAxis = chart_bar.getXAxis();
        xAxis.setGranularity(4f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        xAxis.setDrawGridLines(false);
        // xAxis.setLabelCount(pol.size());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        BarDataSet set1;

            if (chart_bar.getData() != null &&
                    chart_bar.getData().getDataSetCount() > 0) {
                set1 = (BarDataSet) chart_bar.getData().getDataSetByIndex(0);
                set1.setValues(values);
                chart_bar.getData().notifyDataChanged();
                chart_bar.notifyDataSetChanged();

            } else {
                set1 = new BarDataSet(values, "آمار بیماران");

           set1.setColors(ColorTemplate.MATERIAL_COLORS);

                int startColor1 = ContextCompat.getColor(getActivity(), android.R.color.holo_orange_light);
                int startColor2 = ContextCompat.getColor(getActivity(), android.R.color.holo_blue_light);
                int startColor3 = ContextCompat.getColor(getActivity(), android.R.color.holo_orange_light);
                int startColor4 = ContextCompat.getColor(getActivity(), android.R.color.holo_green_light);
                int startColor5 = ContextCompat.getColor(getActivity(), android.R.color.holo_red_light);
                int endColor1 = ContextCompat.getColor(getActivity(), android.R.color.holo_blue_dark);
                int endColor2 = ContextCompat.getColor(getActivity(), android.R.color.holo_purple);
                int endColor3 = ContextCompat.getColor(getActivity(), android.R.color.holo_green_dark);
                int endColor4 = ContextCompat.getColor(getActivity(), android.R.color.holo_red_dark);
                int endColor5 = ContextCompat.getColor(getActivity(), android.R.color.holo_orange_dark);

                List<GradientColor> gradientColors = new ArrayList<>();
                gradientColors.add(new GradientColor(startColor1, endColor1));
                gradientColors.add(new GradientColor(startColor2, endColor2));
                gradientColors.add(new GradientColor(startColor3, endColor3));
                gradientColors.add(new GradientColor(startColor4, endColor4));
                gradientColors.add(new GradientColor(startColor5, endColor5));

                //set1.setGradientColors(gradientColors);

                ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);

                BarData data = new BarData(dataSets);
                data.setValueTextSize(10f);
                //data.setValueTypeface(tfLight);
                data.setBarWidth(0.9f);

                chart_bar.setData(data);
                chart_bar.invalidate();
            }

    }

    //*** set horizontal bar chart for turn product data
    private void setTurnProductData_barHorizontalChart(List<ProductChartObject> pco) {

        ArrayList<BarEntry> values = new ArrayList<>();

        final String[] XLabels = new String[pco.size()];
        for (int i = 0; i < pco.size(); i++) {
            XLabels[i] = pco.get(i).getName();
            values.add(new BarEntry(Float.valueOf(i), Float.valueOf(pco.get(i).getCount()))); // add one entry per hour
        }

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return XLabels[(int) value];
            }

        };

        XAxis xAxis = chart_bar.getXAxis();
        xAxis.setGranularity(2f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter((ValueFormatter) formatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        BarDataSet set1;

            if (chart_bar.getData() != null &&
                    chart_bar.getData().getDataSetCount() > 0) {
                set1 = (BarDataSet) chart_bar.getData().getDataSetByIndex(0);
                set1.setValues(values);
                chart_bar.getData().notifyDataChanged();
                chart_bar.notifyDataSetChanged();

            } else {
                set1 = new BarDataSet(values, "آمار محصولات");

           set1.setColors(ColorTemplate.MATERIAL_COLORS);

                int startColor1 = ContextCompat.getColor(getActivity(), android.R.color.holo_orange_light);
                int startColor2 = ContextCompat.getColor(getActivity(), android.R.color.holo_blue_light);
                int startColor3 = ContextCompat.getColor(getActivity(), android.R.color.holo_orange_light);
                int startColor4 = ContextCompat.getColor(getActivity(), android.R.color.holo_green_light);
                int startColor5 = ContextCompat.getColor(getActivity(), android.R.color.holo_red_light);
                int endColor1 = ContextCompat.getColor(getActivity(), android.R.color.holo_blue_dark);
                int endColor2 = ContextCompat.getColor(getActivity(), android.R.color.holo_purple);
                int endColor3 = ContextCompat.getColor(getActivity(), android.R.color.holo_green_dark);
                int endColor4 = ContextCompat.getColor(getActivity(), android.R.color.holo_red_dark);
                int endColor5 = ContextCompat.getColor(getActivity(), android.R.color.holo_orange_dark);

                List<GradientColor> gradientColors = new ArrayList<>();
                gradientColors.add(new GradientColor(startColor1, endColor1));
                gradientColors.add(new GradientColor(startColor2, endColor2));
                gradientColors.add(new GradientColor(startColor3, endColor3));
                gradientColors.add(new GradientColor(startColor4, endColor4));
                gradientColors.add(new GradientColor(startColor5, endColor5));

                //set1.setGradientColors(gradientColors);

                ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);

                BarData data = new BarData(dataSets);
                data.setValueTextSize(10f);
                //data.setValueTypeface(tfLight);
                data.setBarWidth(0.9f);

                chart_bar_horizontal.setData(data);
                chart_bar_horizontal.invalidate();
            }

    }

    //*** set pie chart for turn product data
    private void setTurnProductData_pieChart(List<ProductChartObject> pco) {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        ArrayList<String> entryLabels = new ArrayList<String>();

        for (int i = 0; i < pco.size() ; i++) {
            entries.add(new PieEntry(Float.valueOf(pco.get(i).getCount()),
                    pco.get(i).getName()));

                entryLabels.add(pco.get(i).getName());
        }

        PieDataSet dataSet = new PieDataSet(entries, "آمار محصولات");

        dataSet.setDrawIcons(false);
        dataSet.setUsingSliceColorAsValueLineColor(true);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 20));
        dataSet.setSelectionShift(10f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(chart_pie));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);
        //data.setValueTypeface(tfLight);
        chart_pie.setData(data);

        // undo all highlights
        chart_pie.highlightValues(null);
        chart_pie.invalidate();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        int resourceId = v.getId();
        switch (resourceId) {
            case R.id.login_button:
                //do somting
                break;
            case R.id.register_button:
                //do somting
                break;
            default:
                //do somting
        }

    }
}