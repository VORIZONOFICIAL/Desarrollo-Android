package com.example.horza_one.ui_Admin.Home;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.horza_one.R;
import com.example.horza_one.databinding.FragmentHomeAdminBinding;
import com.github.mikephil.charting.charts.BarChart;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;


public class Home_Fragment extends Fragment {

    private TextView bienvus;
    private static final String TAG = "EvPersonalChart";
    private BarChart barChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bienvus.findViewById(R.id.bienvus);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ev_per_personal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        FrameLayout chartContainer = root.findViewById(R.id.chartContainer);
        if (chartContainer == null) {
            Log.e(TAG, "chartContainer == null (no existe en el layout inflado)");
            return;
        }

        chartContainer.removeAllViews();

        barChart = new BarChart(requireContext());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );
        barChart.setLayoutParams(lp);
        barChart.setBackgroundColor(Color.TRANSPARENT);
        chartContainer.addView(barChart);

        setupBarChart(barChart);

        final String[] labels = {"Retardos","Asistencias","Faltas","Permisos"};
        final float[]  values = {3f, 15f, 2f, 5f};

        barChart.post(() -> {
            setBarChartData(barChart, labels, values);
            Log.d(TAG, "setData() llamado. hasData="+(barChart.getData()!=null));
        });
    }

    private void setupBarChart(BarChart chart) {
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);
        chart.setFitBars(true);
        chart.setExtraOffsets(8f, 16f, 8f, 8f);
        chart.animateY(700);

        Legend lg = chart.getLegend();
        lg.setEnabled(true);
        lg.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        lg.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        lg.setForm(Legend.LegendForm.CIRCLE);
        lg.setFormSize(10f);
        lg.setTextColor(Color.parseColor("#505050"));
        lg.setTextSize(12f);

        XAxis x = chart.getXAxis();
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setGranularity(1f);
        x.setDrawAxisLine(false);
        x.setDrawGridLines(false);
        x.setTextColor(Color.parseColor("#4A4A4A"));
        x.setTextSize(12f);

        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setDrawAxisLine(false);
        chart.getAxisLeft().setTextColor(Color.parseColor("#8A8A8A"));
        chart.getAxisLeft().setTextSize(12f);
        chart.getAxisLeft().setAxisMinimum(0f);
        chart.getAxisLeft().setAxisMaximum(20f);
        chart.getAxisLeft().setLabelCount(5, true);
        chart.getAxisLeft().setDrawGridLines(true);
        chart.getAxisLeft().setGridColor(Color.parseColor("#EDEDED"));
        chart.getAxisLeft().setGridLineWidth(1f);
    }

    private void setBarChartData(BarChart chart, String[] etiquetas, float[] valores) {
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < valores.length; i++) entries.add(new BarEntry(i, valores[i]));

        BarDataSet ds = new BarDataSet(entries, "Serie 1");
        ds.setColor(Color.parseColor("#37B9FF"));
        ds.setHighLightColor(Color.TRANSPARENT);
        ds.setValueTextColor(Color.TRANSPARENT);

        BarData data = new BarData(ds);
        data.setBarWidth(0.48f);

        chart.setData(data);
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(etiquetas));
        chart.notifyDataSetChanged();
        chart.invalidate();
    }
}
