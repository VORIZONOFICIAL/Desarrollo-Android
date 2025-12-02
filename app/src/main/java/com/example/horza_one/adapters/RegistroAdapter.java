package com.example.horza_one.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.horza_one.R;
import com.example.horza_one.models.Registro;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RegistroAdapter extends RecyclerView.Adapter<RegistroAdapter.RegistroViewHolder> {

    private List<Registro> listaRegistros;

    public RegistroAdapter(List<Registro> listaRegistros) {
        this.listaRegistros = listaRegistros;
    }

    @NonNull
    @Override
    public RegistroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_registro, parent, false);
        return new RegistroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegistroViewHolder holder, int position) {
        Registro registro = listaRegistros.get(position);
        holder.bind(registro);
    }

    @Override
    public int getItemCount() {
        return listaRegistros.size();
    }

    public void actualizarLista(List<Registro> nuevaLista) {
        this.listaRegistros = nuevaLista;
        notifyDataSetChanged();
    }

    static class RegistroViewHolder extends RecyclerView.ViewHolder {
        ImageView iconEstadoRegistro;
        TextView textMatriculaRegistro;
        TextView textHoraRegistro;
        TextView textEstadoRegistro;
        TextView textTipoRegistro;
        TextView textFechaRegistro;
        LinearLayout badgeEstadoRegistro;
        LinearLayout layoutDiferenciaTiempo;

        public RegistroViewHolder(@NonNull View itemView) {
            super(itemView);
            iconEstadoRegistro = itemView.findViewById(R.id.iconEstadoRegistro);
            textMatriculaRegistro = itemView.findViewById(R.id.textMatriculaRegistro);
            textHoraRegistro = itemView.findViewById(R.id.textHoraRegistro);
            textEstadoRegistro = itemView.findViewById(R.id.textEstadoRegistro);
            textTipoRegistro = itemView.findViewById(R.id.textTipoRegistro);
            textFechaRegistro = itemView.findViewById(R.id.textFechaRegistro);
            badgeEstadoRegistro = itemView.findViewById(R.id.badgeEstadoRegistro);
            layoutDiferenciaTiempo = itemView.findViewById(R.id.layoutDiferenciaTiempo);
        }

        public void bind(Registro registro) {
            // Mostrar matrícula
            textMatriculaRegistro.setText("Matrícula: " + registro.getMatricula());
            
            // Mostrar hora
            textHoraRegistro.setText(registro.getHora());
            
            // Mostrar tipo de registro con emoji
            String tipoRegistro = registro.getTipoRegistro();
            if ("Entrada".equalsIgnoreCase(tipoRegistro)) {
                textTipoRegistro.setText("Entrada");
            } else if ("Salida".equalsIgnoreCase(tipoRegistro)) {
                textTipoRegistro.setText("Salida");
            } else {
                textTipoRegistro.setText(tipoRegistro);
            }
            
            // Formatear y mostrar fecha
            textFechaRegistro.setText(formatearFecha(registro.getFecha()));
            
            // Configurar estado del registro
            String estado = registro.getEstadoRegistro();
            textEstadoRegistro.setText(estado);
            
            // Configurar colores e íconos según el tipo de registro y estado
            int color;
            int iconResource;
            int badgeBackground;

            // Seleccionar ícono según tipo de registro (Entrada/Salida)
            if ("Entrada".equalsIgnoreCase(tipoRegistro)) {
                iconResource = R.drawable.ic_entrada;
            } else if ("Salida".equalsIgnoreCase(tipoRegistro)) {
                iconResource = R.drawable.ic_salida;
            } else {
                iconResource = R.drawable.ic_check_circle;
            }

            // Seleccionar colores y badge según el estado
            switch (estado) {
                case "Puntual":
                    color = ContextCompat.getColor(itemView.getContext(), R.color.accent_blue);
                    badgeBackground = R.drawable.badge_disponible;
                    break;
                case "Retardo":
                    color = ContextCompat.getColor(itemView.getContext(), R.color.error_red);
                    badgeBackground = R.drawable.badge_ocupado;
                    break;
                case "Anticipado":
                    color = ContextCompat.getColor(itemView.getContext(), R.color.success_green);
                    badgeBackground = R.drawable.badge_disponible;
                    break;
                default:
                    color = ContextCompat.getColor(itemView.getContext(), R.color.divider);
                    badgeBackground = R.drawable.badge_disponible;
                    break;
            }
            
            // Aplicar el ícono (sin filtro de color para mantener los colores del SVG)
            iconEstadoRegistro.setImageResource(iconResource);
            
            // Aplicar colores del estado al badge
            textEstadoRegistro.setTextColor(color);
            badgeEstadoRegistro.setBackgroundResource(badgeBackground);
            
            // Ocultar diferencia de tiempo por defecto (puede ser extendido en el futuro)
            if (layoutDiferenciaTiempo != null) {
                layoutDiferenciaTiempo.setVisibility(View.GONE);
            }
        }
        
        private String formatearFecha(String fecha) {
            try {
                SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
                Date date = formatoEntrada.parse(fecha);
                return formatoSalida.format(date);
            } catch (ParseException e) {
                return fecha;
            }
        }
    }
}
