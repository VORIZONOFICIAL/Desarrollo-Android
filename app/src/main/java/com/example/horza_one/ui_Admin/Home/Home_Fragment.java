package com.example.horza_one.ui_Admin.Home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.horza_one.R;
import com.example.horza_one.databinding.FragmentHomeAdminBinding;
import com.github.mikephil.charting.charts.BarChart;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import java.util.ArrayList;


public class Home_Fragment extends Fragment {

    private FragmentHomeAdminBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Home_ViewModel Ajustes_ViewModel =
                new ViewModelProvider(this).get(Home_ViewModel.class);

        binding = FragmentHomeAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        Ajustes_ViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        BarChart barChart = binding.barChart;

// Datos de ejemplo
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1f, 4f));
        entries.add(new BarEntry(2f, 6f));
        entries.add(new BarEntry(3f, 8f));
        entries.add(new BarEntry(4f, 3f));
        entries.add(new BarEntry(5f, 7f));

        BarDataSet dataSet = new BarDataSet(entries, "Datos de ejemplo");
        dataSet.setColor(Color.parseColor("#008577")); // hexadecimal #RRGGBB

        dataSet.setValueTextSize(14f);

        BarData data = new BarData(dataSet);
        barChart.setData(data);
        barChart.invalidate(); // refresca la gráfica


// Personalización
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);

        barChart.getAxisRight().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(1000);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
