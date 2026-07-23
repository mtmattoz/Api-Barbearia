package org.aula.api.Usuario;

import io.swagger.v3.oas.annotations.Operation;
import org.aula.dao.UsuarioDao;
import org.aula.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioDao usuarioDao;

    public UsuarioController(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @GetMapping
    @Operation(summary = "Listar usuários")
    public List<UsuarioResponse> listar() {

        return usuarioDao.findAll()
                .stream()
                .map(UsuarioResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    public UsuarioResponse buscarPorId(@PathVariable Long id) {

        Usuario usuario = usuarioDao.findById(id);

        if (usuario == null) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuário não encontrado"
            );
        }

        return UsuarioResponse.fromEntity(usuario);
    }

    @GetMapping("/next-id")
    public long proximoId() {

        return usuarioDao.nextId();
    }

    @PostMapping("/create")
    public ResponseEntity<UsuarioResponse> criar(
            @RequestBody UsuarioRequest request
    ) {

        Usuario usuario = request.toEntity();

        if (request.id() == null) {
            usuario.setId(usuarioDao.nextId());
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        UsuarioResponse.fromEntity(
                                usuarioDao.create(usuario)
                        )
                );
    }

    @PostMapping("/update")
    public UsuarioResponse atualizar(
            @RequestBody UsuarioRequest request
    ) {

        if (request.id() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "ID obrigatório"
            );
        }

        return UsuarioResponse.fromEntity(
                usuarioDao.update(request.toEntity())
        );
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id
    ) {

        if (!usuarioDao.deleteById(id)) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuário não encontrado"
            );
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/delete-all")
    @Operation(
            summary = "Remover todos",
            description = "Remove todos os usuários"
    )
    public ResponseEntity<Void> removerTodos() {

        usuarioDao.deleteAll();

        return ResponseEntity.noContent().build();
    }
}