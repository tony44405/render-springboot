package pe.edu.upc.brotessapp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.brotessapp.dtos.RolDTO;
import pe.edu.upc.brotessapp.entities.Rol;
import pe.edu.upc.brotessapp.serviceinterfaces.IRolService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private IRolService rS;

    @GetMapping("/lista")
    public List<RolDTO> listarZonas() {
        return rS.list().stream().map(z-> {//expresion lambda para cada elemento - transformacion
            ModelMapper m = new ModelMapper();
            return m.map(z, RolDTO.class);
        }).collect(Collectors.toList()); //al final hace que toda la coleccion se tome como un tolist
    }

    @PostMapping("/inserta")
    public void insertar(@RequestBody RolDTO dto) {
        ModelMapper m = new ModelMapper();
        Rol z = m.map(dto, Rol.class);
        rS.insert(z);
    }
    @GetMapping("/{id}")
    public RolDTO buscarId(@PathVariable("id") int id) {
        ModelMapper m = new ModelMapper();
        //aqui se quiere mostrar lo que se puso en el DTO
        RolDTO dto = m.map(rS.listId(id), RolDTO.class);
        return dto;
    }
    @PutMapping("/modifica")
    public void modificar(@RequestBody RolDTO dto) {
        ModelMapper m = new ModelMapper();
        Rol z = m.map(dto, Rol.class);
        rS.update(z);
    }
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") int id) {
        rS.delete(id);
    }


}
