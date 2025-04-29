package pe.edu.upc.brotessapp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.brotessapp.dtos.Q_T1DTO;
import pe.edu.upc.brotessapp.dtos.UsuarioDTO;
import pe.edu.upc.brotessapp.entities.Usuario;
import pe.edu.upc.brotessapp.serviceinterfaces.IUsuarioService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService uS;

    @GetMapping("/lista")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UsuarioDTO> listar() {
        return uS.list().stream().map(u->{
            ModelMapper m = new ModelMapper();
            return m.map(u,UsuarioDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping("/inserta")
    public void insertar(@RequestBody UsuarioDTO dto) {
        ModelMapper m = new ModelMapper();
        Usuario u = m.map(dto,Usuario.class);
        uS.insert(u);
    }

    @GetMapping("/{id}")
    public UsuarioDTO buscarId(@PathVariable("id") int id) {
        ModelMapper m = new ModelMapper();
        //aqui se quiere mostrar lo que se puso en el DTO
        UsuarioDTO dto = m.map(uS.listId(id), UsuarioDTO.class);
        return dto;
    }
    @PutMapping("/modifica")
    public void modificar(@RequestBody UsuarioDTO dto) {
        ModelMapper m = new ModelMapper();
        Usuario u = m.map(dto, Usuario.class);
        uS.update(u);
    }
    @GetMapping("/cantidad-usuarios-zona")
    public List<Q_T1DTO> cantidadUsuariosPorZona() {
        List<String[]> data = uS.cantidadUsuariosPorZona();

        return data.stream().map(fila -> {
            Q_T1DTO dto = new Q_T1DTO();
            dto.setDistrito(fila[0]);
            dto.setCantidadUsuarios(Integer.parseInt(fila[1]));
            return dto;
        }).collect(Collectors.toList());
    }
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") int id) {
        uS.delete(id);
    }
}
