package com.example.horza_one.ui_Admin.Home;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.horza_one.ApiService;
import com.example.horza_one.R;
import com.example.horza_one.databinding.FragmentHomeAdminBinding;
import com.example.horza_one.models.EstadisticasDiariaResponse;
import com.example.horza_one.models.EstadisticasMensualResponse;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home_Fragment extends Fragment {

    private FragmentHomeAdminBinding binding;
    private static final String TAG = "HomeAdminStats";
    private BarChart barChartDiario;
    private BarChart barChartMensual;
    private ApiService apiService;
    private Spinner spinnerTipoEstadistica;
    private TextView textTituloGraficaMensual;
    private String tipoEstadisticaSeleccionada = "puntuales";
    private Handler handler = new Handler();
    private Runnable actualizadorAutomatico;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Obtener nombre del usuario
        SharedPreferences prefs = requireActivity().getSharedPreferences("HorzaPrefs", Context.MODE_PRIVATE);
        String nombreCompleto = prefs.getString("nombreCompleto", "Usuario");
        String nombreRol = prefs.getString("nombreRol", "");

        final TextView textView = binding.bienvus;
        textView.setText("Bienvenido, " + nombreCompleto);

        // Inicializar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        // Configurar gráfica diaria
        FrameLayout chartContainer = root.findViewById(R.id.chartContainer);
        if (chartContainer != null) {
            chartContainer.removeAllViews();

            barChartDiario = new BarChart(requireContext());
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
            );
            barChartDiario.setLayoutParams(lp);
            barChartDiario.setBackgroundColor(Color.TRANSPARENT);
            chartContainer.addView(barChartDiario);

            setupBarChart(barChartDiario);
        }

        // Configurar gráfica mensual
        FrameLayout chartContainerMensual = root.findViewById(R.id.chartContainerMensual);
        if (chartContainerMensual != null) {
            chartContainerMensual.removeAllViews();

            barChartMensual = new BarChart(requireContext());
            FrameLayout.LayoutParams lp2 = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
            );
            barChartMensual.setLayoutParams(lp2);
            barChartMensual.setBackgroundColor(Color.TRANSPARENT);
            chartContainerMensual.addView(barChartMensual);

            setupBarChart(barChartMensual);
        }

        // Configurar Spinner
        textTituloGraficaMensual = root.findViewById(R.id.textTituloGraficaMensual);
        spinnerTipoEstadistica = root.findViewById(R.id.spinnerTipoEstadistica);
        if (spinnerTipoEstadistica != null) {
            String[] opciones = {"Entradas Puntuales", "Retardos", "Inasistencias", "Permisos"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                    android.R.layout.simple_spinner_item, opciones);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTipoEstadistica.setAdapter(adapter);

            spinnerTipoEstadistica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            tipoEstadisticaSeleccionada = "puntuales";
                            break;
                        case 1:
                            tipoEstadisticaSeleccionada = "retardos";
                            break;
                        case 2:
                            tipoEstadisticaSeleccionada = "inasistencias";
                            break;
                        case 3:
                            tipoEstadisticaSeleccionada = "permisos";
                            break;
                    }
                    cargarEstadisticasMensuales();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

        // Cargar estadísticas iniciales
        cargarEstadisticasDiarias();
        cargarEstadisticasMensuales();

        // Configurar actualización automática cada 30 segundos
        iniciarActualizacionAutomatica();
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
        x.setTextSize(11f);

        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setDrawAxisLine(false);
        chart.getAxisLeft().setTextColor(Color.parseColor("#8A8A8A"));
        chart.getAxisLeft().setTextSize(12f);
        chart.getAxisLeft().setAxisMinimum(0f);
        chart.getAxisLeft().setLabelCount(5, true);
        chart.getAxisLeft().setDrawGridLines(true);
        chart.getAxisLeft().setGridColor(Color.parseColor("#EDEDED"));
        chart.getAxisLeft().setGridLineWidth(1f);
    }

    private void cargarEstadisticasDiarias() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String fechaActual = sdf.format(Calendar.getInstance().getTime());

        Call<EstadisticasDiariaResponse> call = apiService.obtenerEstadisticasDiarias(null);
        call.enqueue(new Callback<EstadisticasDiariaResponse>() {
            @Override
            public void onResponse(Call<EstadisticasDiariaResponse> call, Response<EstadisticasDiariaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    EstadisticasDiariaResponse stats = response.body();
                    Log.d(TAG, "Estadísticas recibidas - Puntuales: " + stats.getEntradasPuntuales() +
                            ", Retardos: " + stats.getRetardos() +
                            ", Inasistencias: " + stats.getInasistencias() +
                            ", Permisos: " + stats.getPermisos());

                    mostrarEstadisticasDiarias(stats);
                } else {
                    Log.e(TAG, "Error en respuesta: " + response.code());
                    Toast.makeText(requireContext(), "Error al cargar estadísticas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EstadisticasDiariaResponse> call, Throwable t) {
                Log.e(TAG, "Error de conexión: " + t.getMessage());
                Toast.makeText(requireContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarEstadisticasDiarias(EstadisticasDiariaResponse stats) {
        final String[] labels = {
                "Puntuales",
                "Retardos",
                "Inasistencias",
                "Permisos",
                "Entradas",
                "Salidas"
        };

        final float[] values = {
                stats.getEntradasPuntuales() != null ? stats.getEntradasPuntuales().floatValue() : 0f,
                stats.getRetardos() != null ? stats.getRetardos().floatValue() : 0f,
                stats.getInasistencias() != null ? stats.getInasistencias().floatValue() : 0f,
                stats.getPermisos() != null ? stats.getPermisos().floatValue() : 0f,
                stats.getTotalEntradas() != null ? stats.getTotalEntradas().floatValue() : 0f,
                stats.getTotalSalidas() != null ? stats.getTotalSalidas().floatValue() : 0f
        };

        if (barChartDiario != null) {
            barChartDiario.post(() -> {
                setBarChartData(barChartDiario, labels, values, true);
                Log.d(TAG, "Gráfica diaria actualizada");
            });
        }
    }

    private void cargarEstadisticasMensuales() {
        Calendar calendar = Calendar.getInstance();
        int mesActual = calendar.get(Calendar.MONTH) + 1;
        int anioActual = calendar.get(Calendar.YEAR);

        Call<EstadisticasMensualResponse> call = apiService.obtenerEstadisticasMensuales(
                mesActual, anioActual, tipoEstadisticaSeleccionada);

        call.enqueue(new Callback<EstadisticasMensualResponse>() {
            @Override
            public void onResponse(Call<EstadisticasMensualResponse> call, Response<EstadisticasMensualResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    EstadisticasMensualResponse stats = response.body();
                    Log.d(TAG, "Estadísticas mensuales recibidas: " + tipoEstadisticaSeleccionada);
                    mostrarEstadisticasMensuales(stats);
                } else {
                    Log.e(TAG, "Error en respuesta mensual: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<EstadisticasMensualResponse> call, Throwable t) {
                Log.e(TAG, "Error de conexión mensual: " + t.getMessage());
            }
        });
    }

    private void mostrarEstadisticasMensuales(EstadisticasMensualResponse stats) {
        if (stats.getEstadisticasPorDia() == null || stats.getEstadisticasPorDia().isEmpty()) {
            Log.e(TAG, "No hay datos mensuales");
            return;
        }

        // Actualizar título
        if (textTituloGraficaMensual != null) {
            String titulo = "Estadísticas Mensuales - ";
            switch (tipoEstadisticaSeleccionada) {
                case "puntuales": titulo += "Entradas Puntuales"; break;
                case "retardos": titulo += "Retardos"; break;
                case "inasistencias": titulo += "Inasistencias"; break;
                case "permisos": titulo += "Permisos"; break;
            }
            textTituloGraficaMensual.setText(titulo);
        }

        // Preparar datos
        List<EstadisticasMensualResponse.EstadisticaDia> datos = stats.getEstadisticasPorDia();
        List<String> labels = new ArrayList<>();
        float[] values = new float[datos.size()];

        for (int i = 0; i < datos.size(); i++) {
            EstadisticasMensualResponse.EstadisticaDia dia = datos.get(i);
            labels.add(String.valueOf(dia.getDia()));
            values[i] = dia.getCantidad() != null ? dia.getCantidad().floatValue() : 0f;
        }

        if (barChartMensual != null) {
            barChartMensual.post(() -> {
                setBarChartData(barChartMensual, labels.toArray(new String[0]), values, false);
                Log.d(TAG, "Gráfica mensual actualizada");
            });
        }
    }

    private void setBarChartData(BarChart chart, String[] etiquetas, float[] valores, boolean esDiaria) {
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < valores.length; i++) {
            entries.add(new BarEntry(i, valores[i]));
        }

        BarDataSet ds = new BarDataSet(entries, esDiaria ? "Estadísticas del Día" : "Datos del Mes");

        if (esDiaria) {
            // Colores diferentes para cada categoría (diaria)
            int[] colores = {
                    Color.parseColor("#4CAF50"), // Verde - Puntuales
                    Color.parseColor("#FFC107"), // Amarillo - Retardos
                    Color.parseColor("#F44336"), // Rojo - Inasistencias
                    Color.parseColor("#FF9800"), // Naranja - Permisos
                    Color.parseColor("#2196F3"), // Azul - Entradas
                    Color.parseColor("#9C27B0")  // Morado - Salidas
            };
            ds.setColors(colores);
        } else {
            // Color único según el tipo (mensual)
            int color;
            switch (tipoEstadisticaSeleccionada) {
                case "puntuales": color = Color.parseColor("#4CAF50"); break;
                case "retardos": color = Color.parseColor("#FFC107"); break;
                case "inasistencias": color = Color.parseColor("#F44336"); break;
                case "permisos": color = Color.parseColor("#FF9800"); break;
                default: color = Color.parseColor("#37B9FF");
            }
            ds.setColor(color);
        }

        ds.setHighLightColor(Color.TRANSPARENT);
        ds.setValueTextColor(Color.parseColor("#2C3E50"));
        ds.setValueTextSize(esDiaria ? 12f : 9f);

        BarData data = new BarData(ds);
        data.setBarWidth(esDiaria ? 0.6f : 0.8f);

        chart.setData(data);
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(etiquetas));

        if (!esDiaria) {
            chart.getXAxis().setLabelCount(Math.min(etiquetas.length, 10));
            chart.getXAxis().setLabelRotationAngle(-45f);
        }

        // Ajustar eje Y
        float maxValue = 0;
        for (float val : valores) {
            if (val > maxValue) maxValue = val;
        }
        chart.getAxisLeft().setAxisMaximum(maxValue * 1.2f);

        chart.notifyDataSetChanged();
        chart.invalidate();
    }

    private void iniciarActualizacionAutomatica() {
        actualizadorAutomatico = new Runnable() {
            @Override
            public void run() {
                if (isAdded() && getActivity() != null) {
                    cargarEstadisticasDiarias();
                    cargarEstadisticasMensuales();
                    handler.postDelayed(this, 30000); // Cada 30 segundos
                }
            }
        };
        handler.postDelayed(actualizadorAutomatico, 30000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (handler != null && actualizadorAutomatico != null) {
            handler.removeCallbacks(actualizadorAutomatico);
        }
        binding = null;
    }
}