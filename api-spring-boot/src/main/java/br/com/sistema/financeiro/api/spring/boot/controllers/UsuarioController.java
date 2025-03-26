package br.com.sistema.financeiro.api.spring.boot.controllers;


import br.com.sistema.financeiro.api.spring.boot.entities.Usuario;
import br.com.sistema.financeiro.api.spring.boot.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
public class UsuarioController {

    @Autowired
    private  UsuarioService usuarioService;


    @PostMapping("/criarUsuario")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioCriado = usuarioService.criarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }



    @GetMapping("/buscarUsuarioPorEmail")
    public ResponseEntity<Optional<Usuario>> buscarUsuarioPorEmail(@RequestParam String email) {
        Optional<Usuario> usuario = usuarioService.buscarUsuarioPorEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }



}
