package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ConsultaRegistrosRequest;
import com.example.demo.dto.ConsultaRegistrosResponse;
import com.example.demo.dto.RegistroDTO;
import com.example.demo.model.Area;
import com.example.demo.model.Bitacora;
import com.example.demo.model.Dispositivo;
import com.example.demo.model.Registro;
import com.example.demo.model.Usuario;
import com.example.demo.respository.AreaRepository;
import com.example.demo.respository.BitacoraRepository;
import com.example.demo.respository.DispositivoRepository;
import com.example.demo.respository.RegistroRepository;
import com.example.demo.respository.UsuarioRepository;
import com.example.demo.service.RegistroService;

@Service
public class RegistroServiceImpl implements RegistroService {

    @Autowired
    private RegistroRepository registroRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private BitacoraRepository bitacoraRepository;
    
    @Autowired
    private DispositivoRepository dispositivoRepository;
    
    @Autowired
    private AreaRepository areaRepository;

    @Override
    public List<RegistroDTO> obtenerTodos() {
        return registroRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public RegistroDTO obtenerPorId(Integer id) {
        Registro registro = registroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado"));
        return convertirADTO(registro);
    }

    @Override
    public RegistroDTO crear(RegistroDTO registroDTO) {
        Registro registro = convertirAEntidad(registroDTO);
        Registro registroGuardado = registroRepository.save(registro);
        return convertirADTO(registroGuardado);
    }

    @Override
    public RegistroDTO actualizar(Integer id, RegistroDTO registroDTO) {
        Registro registro = registroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado"));
        
        Usuario usuario = usuarioRepository.findById(registroDTO.getMatricula())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Bitacora bitacora = bitacoraRepository.findById(registroDTO.getIdBitacora())
                .orElseThrow(() -> new RuntimeException("Bitácora no encontrada"));
        Dispositivo dispositivo = dispositivoRepository.findById(registroDTO.getIdDispositivo())
                .orElseThrow(() -> new RuntimeException("Dispositivo no encontrado"));
        Area area = areaRepository.findById(registroDTO.getIdArea())
                .orElseThrow(() -> new RuntimeException("Área no encontrada"));
        
        registro.setUsuario(usuario);
        registro.setBitacora(bitacora);
        registro.setDispositivo(dispositivo);
        registro.setArea(area);
        registro.setTipoRegistro(registroDTO.getTipoRegistro());
        registro.setFecha(registroDTO.getFecha());
        registro.setHora(registroDTO.getHora());
        registro.setObservacion(registroDTO.getObservacion());
        registro.setEstadoRegistro(registroDTO.getEstadoRegistro());
        
        Registro registroActualizado = registroRepository.save(registro);
        return convertirADTO(registroActualizado);
    }

    @Override
    public void eliminar(Integer id) {
        registroRepository.deleteById(id);
    }

    @Override
    public ConsultaRegistrosResponse consultarRegistrosPorFiltros(ConsultaRegistrosRequest request) {
        // Obtener usuario para el nombre completo
        Usuario usuario = usuarioRepository.findById(request.getMatricula())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        String nombreCompleto = usuario.getNombreUsuario() + " " +
                usuario.getApellidoPaternoUsuario() + " " +
                usuario.getApellidoMaternoUsuario();
        
        // Obtener registros filtrados
        List<Registro> registros;
        if (request.getTipoRegistro() != null && !request.getTipoRegistro().isEmpty()) {
            registros = registroRepository.findByUsuario_MatriculaAndFechaBetweenAndTipoRegistro(
                    request.getMatricula(),
                    request.getFechaInicio(),
                    request.getFechaFin(),
                    request.getTipoRegistro()
            );
        } else {
            registros = registroRepository.findByUsuario_MatriculaAndFechaBetween(
                    request.getMatricula(),
                    request.getFechaInicio(),
                    request.getFechaFin()
            );
        }
        
        // Convertir a DTOs
        List<RegistroDTO> registrosDTO = registros.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        
        // Calcular estadísticas
        int totalRegistros = registros.size();
        int totalEntradas = (int) registros.stream().filter(r -> "Entrada".equals(r.getTipoRegistro())).count();
        int totalSalidas = (int) registros.stream().filter(r -> "Salida".equals(r.getTipoRegistro())).count();
        int totalPuntuales = (int) registros.stream().filter(r -> "Puntual".equals(r.getEstadoRegistro())).count();
        int totalRetardos = (int) registros.stream().filter(r -> "Retardo".equals(r.getEstadoRegistro())).count();
        int totalAnticipados = (int) registros.stream().filter(r -> "Anticipado".equals(r.getEstadoRegistro())).count();
        
        return new ConsultaRegistrosResponse(
                request.getMatricula(),
                nombreCompleto,
                totalRegistros,
                totalEntradas,
                totalSalidas,
                totalPuntuales,
                totalRetardos,
                totalAnticipados,
                registrosDTO
        );
    }

    @Override
    public List<RegistroDTO> obtenerUltimos3PorDispositivo(Integer idDispositivo) {
        List<Registro> registros = registroRepository.findTop3ByDispositivo_IdDispositivoOrderByFechaDescHoraDesc(idDispositivo);
        return registros.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<RegistroDTO> obtenerTodosPorDispositivo(Integer idDispositivo) {
        List<Registro> registros = registroRepository.findByDispositivo_IdDispositivoOrderByFechaDescHoraDesc(idDispositivo);
        return registros.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private RegistroDTO convertirADTO(Registro registro) {
        return new RegistroDTO(
                registro.getIdRegistro(),
                registro.getUsuario().getMatricula(),
                registro.getBitacora().getIdBitacora(),
                registro.getDispositivo().getIdDispositivo(),
                registro.getArea().getIdArea(),
                registro.getArea().getNombreArea(), // Nombre del área
                registro.getTipoRegistro(),
                registro.getFecha(),
                registro.getHora(),
                registro.getObservacion(),
                registro.getEstadoRegistro()
        );
    }

    private Registro convertirAEntidad(RegistroDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getMatricula())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Bitacora bitacora = bitacoraRepository.findById(dto.getIdBitacora())
                .orElseThrow(() -> new RuntimeException("Bitácora no encontrada"));
        Dispositivo dispositivo = dispositivoRepository.findById(dto.getIdDispositivo())
                .orElseThrow(() -> new RuntimeException("Dispositivo no encontrado"));
        Area area = areaRepository.findById(dto.getIdArea())
                .orElseThrow(() -> new RuntimeException("Área no encontrada"));
        
        return new Registro(
                dto.getIdRegistro(),
                usuario,
                bitacora,
                dispositivo,
                area,
                dto.getTipoRegistro(),
                dto.getFecha(),
                dto.getHora(),
                dto.getObservacion(),
                dto.getEstadoRegistro()
        );
    }
}
