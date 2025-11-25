package com.example.horza_one.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.horza_one.R;
import com.example.horza_one.models.Calendario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarioAdapter extends RecyclerView.Adapter<CalendarioAdapter.CalendarioViewHolder> {

    private List<Calendario> calendarios;
    private OnCalendarioClickListener listener;
    private int selectedPosition = -1;

    public interface OnCalendarioClickListener {
        void onCalendarioClick(Calendario calendario);
    }

    public CalendarioAdapter(List<Calendario> calendarios, OnCalendarioClickListener listener) {
        this.calendarios = calendarios;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CalendarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_calendario_dialog, parent, false);
        return new CalendarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarioViewHolder holder, int position) {
        Calendario calendario = calendarios.get(position);
        holder.bind(calendario, position == selectedPosition);
    }

    @Override
    public int getItemCount() {
        return calendarios.size();
    }

    public void setSelectedPosition(int position) {
        int previousPosition = selectedPosition;
        selectedPosition = position;
        
        if (previousPosition != -1) {
            notifyItemChanged(previousPosition);
        }
        notifyItemChanged(selectedPosition);
    }

    class CalendarioViewHolder extends RecyclerView.ViewHolder {
        private ImageView iconCalendario;
        private TextView textNombreCalendario;
        private TextView textFechas;
        private TextView textEstado;
        private ImageView iconEstado;
        private ImageView iconCheck;

        public CalendarioViewHolder(@NonNull View itemView) {
            super(itemView);
            iconCalendario = itemView.findViewById(R.id.iconCalendario);
            textNombreCalendario = itemView.findViewById(R.id.textNombreCalendario);
            textFechas = itemView.findViewById(R.id.textFechas);
            textEstado = itemView.findViewById(R.id.textEstado);
            iconCheck = itemView.findViewById(R.id.iconCheck);
        }

        public void bind(Calendario calendario, boolean isSelected) {
            // Nombre del calendario
            textNombreCalendario.setText(calendario.getNombreCalendario());

            // Formatear fechas (convertir de yyyy-MM-dd a dd/MM/yyyy)
            String fechaInicioStr = formatearFecha(calendario.getFechaInicio());
            String fechaFinStr = formatearFecha(calendario.getFechaFin());
            textFechas.setText(fechaInicioStr + " - " + fechaFinStr);

            // Estado del calendario
            String estado = calendario.getActivoCalendario();
            if ("Activo".equalsIgnoreCase(estado)) {
                textEstado.setText("ACTIVO");
                textEstado.setTextColor(itemView.getContext().getColor(R.color.success_green));
            } else {
                textEstado.setText("INACTIVO");
                textEstado.setTextColor(itemView.getContext().getColor(R.color.text_secondary));
            }

            // Mostrar/ocultar check de selección
            iconCheck.setVisibility(isSelected ? View.VISIBLE : View.GONE);

            // Click listener
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        setSelectedPosition(position);
                        listener.onCalendarioClick(calendario);
                    }
                }
            });
        }

        private String formatearFecha(String fecha) {
            if (fecha == null || fecha.isEmpty()) {
                return "N/A";
            }
            
            try {
                // Formato de entrada: yyyy-MM-dd (del backend)
                SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                // Formato de salida: dd/MM/yyyy
                SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                
                Date date = formatoEntrada.parse(fecha);
                return formatoSalida.format(date);
            } catch (ParseException e) {
                // Si el formato ya es dd/MM/yyyy o hay error, devolver como está
                return fecha;
            }
        }
    }
}
