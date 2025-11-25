package com.example.demo.service.impl;

import com.example.demo.dto.CambioContrasenaRequest;
import com.example.demo.dto.UsuarioDTO;
import com.example.demo.model.Rol;
import com.example.demo.model.Usuario;
import com.example.demo.respository.RolRepository;
import com.example.demo.respository.UsuarioRepository;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<UsuarioDTO> obtenerTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO obtenerPorId(Integer matricula) {
        Usuario usuario = usuarioRepository.findById(matricula)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertirADTO(usuario);
    }

    @Override
    public UsuarioDTO crear(UsuarioDTO usuarioDTO) {
        Usuario usuario = convertirAEntidad(usuarioDTO);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return convertirADTO(usuarioGuardado);
    }

    @Override
    public UsuarioDTO actualizar(Integer matricula, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(matricula)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Rol rol = rolRepository.findById(usuarioDTO.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        
        usuario.setRol(rol);
        usuario.setRfc(usuarioDTO.getRfc());
        usuario.setCurp(usuarioDTO.getCurp());
        usuario.setFechaAlta(usuarioDTO.getFechaAlta());
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setApellidoPaternoUsuario(usuarioDTO.getApellidoPaternoUsuario());
        usuario.setApellidoMaternoUsuario(usuarioDTO.getApellidoMaternoUsuario());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setTipoContrato(usuarioDTO.getTipoContrato());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setActivo(usuarioDTO.getActivo());
        usuario.setCpUsuario(usuarioDTO.getCpUsuario());
        usuario.setCalleUsuario(usuarioDTO.getCalleUsuario());
        
        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return convertirADTO(usuarioActualizado);
    }

    @Override
    public void eliminar(Integer matricula) {
        usuarioRepository.deleteById(matricula);
    }

    @Override
    public boolean cambiarContrasena(CambioContrasenaRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getMatricula())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if (!usuario.getContrasena().equals(request.getContrasenaActual())) {
            return false;
        }
        
        usuario.setContrasena(request.getContrasenaNueva());
        usuarioRepository.save(usuario);
        return true;
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getMatricula(),
                usuario.getRol().getIdRol(),
                usuario.getRfc(),
                usuario.getCurp(),
                usuario.getFechaAlta(),
                usuario.getNombreUsuario(),
                usuario.getApellidoPaternoUsuario(),
                usuario.getApellidoMaternoUsuario(),
                usuario.getTelefono(),
                usuario.getTipoContrato(),
                usuario.getCorreo(),
                usuario.getActivo(),
                usuario.getCpUsuario(),
                usuario.getCalleUsuario(),
                null // No devolver la contraseña por seguridad
        );
    }

    private Usuario convertirAEntidad(UsuarioDTO dto) {
        Rol rol = rolRepository.findById(dto.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        
        return new Usuario(
                dto.getMatricula(),
                rol,
                dto.getRfc(),
                dto.getCurp(),
                dto.getFechaAlta(),
                dto.getNombreUsuario(),
                dto.getApellidoPaternoUsuario(),
                dto.getApellidoMaternoUsuario(),
                dto.getTelefono(),
                dto.getTipoContrato(),
                dto.getCorreo(),
                dto.getActivo(),
                dto.getCpUsuario(),
                dto.getCalleUsuario(),
                dto.getContrasena()
        );
    }
}
