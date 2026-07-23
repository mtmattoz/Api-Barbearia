package org.aula.api.Servico;

import io.swagger.v3.oas.annotations.Operation;
import org.aula.dao.ServicoDao;
import org.aula.model.Servico;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/servico")
public class ServicoController {

    private final ServicoDao servicoDao;

    public ServicoController(ServicoDao servicoDao) {
        this.servicoDao = servicoDao;
    }

    @GetMapping
    @Operation(summary = "Listar serviços")
    public List<ServicoResponse> listar(){

        return servicoDao.findAll()
                .stream()
                .map(ServicoResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar serviço por ID")
    public ServicoResponse buscarPorId(@PathVariable Long id){

        Servico servico = servicoDao.findById(id);

        if(servico == null){

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Servico nao encontrado"
            );
        }

        return ServicoResponse.fromEntity(servico);
    }

    @GetMapping("/next-id")
    public long proximoId(){

        return servicoDao.nextId();
    }

    @PostMapping("/create")
    public ResponseEntity<ServicoResponse> criar(
            @RequestBody ServicoRequest request
    ){

        Servico servico = request.toEntity();

        if(request.id() == null){
            servico.setId(servicoDao.nextId());
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ServicoResponse.fromEntity(
                                servicoDao.create(servico)
                        )
                );
    }

    @PostMapping("/update")
    public ServicoResponse atualizar(
            @RequestBody ServicoRequest request
    ){

        if(request.id() == null){

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "ID obrigatorio"
            );
        }

        return ServicoResponse.fromEntity(
                servicoDao.update(request.toEntity())
        );
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id
    ){

        if(!servicoDao.deleteById(id)){

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Servico nao encontrado"
            );
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/delete-all")
    @Operation(
            summary = "Remover todos",
            description = "Remove todos os serviços"
    )
    public ResponseEntity<Void> removerTodos(){

        servicoDao.deleteAll();

        return ResponseEntity.noContent().build();
    }
}