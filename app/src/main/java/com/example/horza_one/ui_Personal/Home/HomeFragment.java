package com.example.horza_one.ui_Personal.Home;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.horza_one.R;
import com.example.horza_one.databinding.FragmentHomePersonalBinding;
import com.example.horza_one.ui_Admin.Home.Home_ViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomeFragment extends Fragment {

    private FragmentHomePersonalBinding binding;
    private PieChart pieChart;
    private GridLayout calendarGrid;
    private TextView textDiasRegistrados;
    private TextView textMesActual;

    // Datos de ejemplo (sustituir con datos reales de la API)
    private int asistenciasPuntuales = 15;
    private int retardos = 3;
    private int permisos = 2;
    private int faltas = 1;

    // Estados de asistencia por día del mes (día -> tipo)
    // Tipos: 0=sin registro, 1=puntual, 2=retardo, 3=permiso, 4=falta
    private Map<Integer, Integer> estadosPorDia = new HashMap<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Home_ViewModel homeViewModel =
                new ViewModelProvider(this).get(Home_ViewModel.class);
        binding = FragmentHomePersonalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // ✅ OBTENER NOMBRE DEL USUARIO DESDE SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("HorzaPrefs", Context.MODE_PRIVATE);
        String nombreCompleto = prefs.getString("nombreCompleto", "Usuario");
        String nombreRol = prefs.getString("nombreRol", "");

        // ✅ MOSTRAR EN EL TEXTVIEW (asume que tienes un TextView en tu layout)
        final TextView textView = binding.bienvus;
        textView.setText("Bienvenido, " + nombreCompleto + " (" + nombreRol + ")");
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar vistas
        pieChart = binding.pieChart;
        calendarGrid = binding.calendarGrid;
        textDiasRegistrados = binding.textDiasRegistrados;
        textMesActual = binding.textMesActual;

        // Generar datos de ejemplo para el calendario
        generarDatosEjemplo();

        // Configurar gráfica de pastel
        configurarGraficaPastel();

        // Configurar calendario
        configurarCalendario();

        // Actualizar contador de días
        int diasRegistrados = asistenciasPuntuales + retardos + permisos + faltas;
        textDiasRegistrados.setText(String.valueOf(diasRegistrados));

        // Actualizar mes actual
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatoMes = new SimpleDateFormat("MMMM yyyy", new Locale("es", "ES"));
        String mesActual = formatoMes.format(cal.getTime());
        // Capitalizar primera letra
        mesActual = mesActual.substring(0, 1).toUpperCase() + mesActual.substring(1);
        textMesActual.setText(mesActual);
    }

    private void generarDatosEjemplo() {
        // Ejemplo de estados para días del mes
        // 1=puntual (verde), 2=retardo (amarillo), 3=permiso (naranja), 4=falta (rojo)
        estadosPorDia.put(1, 1);  // Día 1: Puntual
        estadosPorDia.put(2, 1);  // Día 2: Puntual
        estadosPorDia.put(3, 2);  // Día 3: Retardo
        estadosPorDia.put(4, 1);  // Día 4: Puntual
        estadosPorDia.put(5, 1);  // Día 5: Puntual
        estadosPorDia.put(8, 1);  // Día 8: Puntual
        estadosPorDia.put(9, 2);  // Día 9: Retardo
        estadosPorDia.put(10, 1); // Día 10: Puntual
        estadosPorDia.put(11, 4); // Día 11: Falta
        estadosPorDia.put(12, 1); // Día 12: Puntual
        estadosPorDia.put(15, 1); // Día 15: Puntual
        estadosPorDia.put(16, 3); // Día 16: Permiso
        estadosPorDia.put(17, 1); // Día 17: Puntual
        estadosPorDia.put(18, 2); // Día 18: Retardo
        estadosPorDia.put(19, 1); // Día 19: Puntual
        estadosPorDia.put(22, 1); // Día 22: Puntual
        estadosPorDia.put(23, 1); // Día 23: Puntual
        estadosPorDia.put(24, 1); // Día 24: Puntual
        estadosPorDia.put(25, 1); // Día 25: Puntual
        estadosPorDia.put(26, 3); // Día 26: Permiso
        estadosPorDia.put(29, 1); // Día 29: Puntual
    }

    private void configurarGraficaPastel() {
        // Crear entradas para la gráfica
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(asistenciasPuntuales, "Puntuales"));
        entries.add(new PieEntry(retardos, "Retardos"));
        entries.add(new PieEntry(permisos, "Permisos"));
        entries.add(new PieEntry(faltas, "Faltas"));

        // Crear dataset
        PieDataSet dataSet = new PieDataSet(entries, "");

        // Configurar colores (Verde, Amarillo, Naranja, Rojo)
        int[] colores = {
                Color.parseColor("#4CAF50"), // Verde - Puntuales
                Color.parseColor("#FFC107"), // Amarillo - Retardos
                Color.parseColor("#FF9800"), // Naranja - Permisos
                Color.parseColor("#F44336")  // Rojo - Faltas
        };
        dataSet.setColors(colores);

        // Configurar valores en el gráfico
        dataSet.setValueTextSize(14f);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setSliceSpace(3f);

        // Crear datos
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(pieChart));

        // Configurar gráfica
        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setHoleRadius(45f);
        pieChart.setTransparentCircleRadius(50f);
        pieChart.setDrawEntryLabels(false);
        pieChart.setRotationEnabled(true);

        // Configurar centro del círculo
        pieChart.setCenterText("Asistencias\ndel Mes");
        pieChart.setCenterTextSize(14f);
        pieChart.setCenterTextColor(Color.parseColor("#2C3E50"));

        // Configurar leyenda
        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setTextSize(12f);
        legend.setFormSize(10f);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setXEntrySpace(12f);
        legend.setYEntrySpace(4f);

        // Animar
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    private void configurarCalendario() {
        // Limpiar grid
        calendarGrid.removeAllViews();

        // Obtener información del mes actual
        Calendar calendario = Calendar.getInstance();
        calendario.set(Calendar.DAY_OF_MONTH, 1);

        int primerDiaSemana = calendario.get(Calendar.DAY_OF_WEEK) - 1; // 0=Domingo
        int diasEnMes = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);

        // GridLayout con 7 columnas (días de la semana)
        calendarGrid.setColumnCount(7);

        // Calcular número de filas necesarias
        int totalCeldas = primerDiaSemana + diasEnMes;
        int filas = (int) Math.ceil(totalCeldas / 7.0);
        calendarGrid.setRowCount(filas);

        // Agregar espacios en blanco antes del primer día
        for (int i = 0; i < primerDiaSemana; i++) {
            TextView vacio = new TextView(requireContext());
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            vacio.setLayoutParams(params);
            calendarGrid.addView(vacio);
        }

        // Agregar días del mes
        for (int dia = 1; dia <= diasEnMes; dia++) {
            TextView tvDia = crearVistaDia(dia);
            calendarGrid.addView(tvDia);
        }
    }

    private TextView crearVistaDia(int dia) {
        TextView tvDia = new TextView(requireContext());

        // Configurar layout
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = (int) (48 * getResources().getDisplayMetrics().density);
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        params.setMargins(4, 4, 4, 4);
        tvDia.setLayoutParams(params);

        // Configurar texto
        tvDia.setText(String.valueOf(dia));
        tvDia.setGravity(Gravity.CENTER);
        tvDia.setTextSize(14f);
        tvDia.setTextColor(Color.parseColor("#2C3E50"));

        // Aplicar fondo según el estado
        Integer estado = estadosPorDia.get(dia);
        if (estado != null) {
            switch (estado) {
                case 1: // Puntual - Verde
                    tvDia.setBackgroundResource(R.drawable.calendar_day_green);
                    tvDia.setTextColor(Color.WHITE);
                    break;
                case 2: // Retardo - Amarillo
                    tvDia.setBackgroundResource(R.drawable.calendar_day_yellow);
                    tvDia.setTextColor(Color.WHITE);
                    break;
                case 3: // Permiso - Naranja
                    tvDia.setBackgroundResource(R.drawable.calendar_day_orange);
                    tvDia.setTextColor(Color.WHITE);
                    break;
                case 4: // Falta - Rojo
                    tvDia.setBackgroundResource(R.drawable.calendar_day_red);
                    tvDia.setTextColor(Color.WHITE);
                    break;
                default:
                    tvDia.setBackgroundResource(R.drawable.calendar_day_default);
                    break;
            }
        } else {
            // Sin registro
            tvDia.setBackgroundResource(R.drawable.calendar_day_default);
        }

        return tvDia;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}