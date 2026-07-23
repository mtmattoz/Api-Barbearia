package org.aula.api.Barbeiro;

import io.swagger.v3.oas.annotations.Operation;
import org.aula.dao.BarbeiroDao;
import org.aula.model.Barbeiro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/barbeiro")
public class BarbeiroController {

    private final BarbeiroDao barbeiroDao;

    public BarbeiroController(BarbeiroDao barbeiroDao) {
        this.barbeiroDao = barbeiroDao;
    }

    @GetMapping
    @Operation(summary = "Listar barbeiros")
    public List<BarbeiroResponse> listar(){

        return barbeiroDao.findAll()
                .stream()
                .map(BarbeiroResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar barbeiro por ID")
    public BarbeiroResponse buscarPorId(@PathVariable Long id){

        Barbeiro barbeiro = barbeiroDao.findById(id);

        if(barbeiro == null){

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Barbeiro nao encontrado"
            );
        }

        return BarbeiroResponse.fromEntity(barbeiro);
    }

    @GetMapping("/next-id")
    public long proximoId(){

        return barbeiroDao.nextId();
    }

    @PostMapping("/create")
    public ResponseEntity<BarbeiroResponse> criar(
            @RequestBody BarbeiroRequest request
    ){

        Barbeiro barbeiro = request.toEntity();

        if(request.id() == null){
            barbeiro.setId(barbeiroDao.nextId());
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        BarbeiroResponse.fromEntity(
                                barbeiroDao.create(barbeiro)
                        )
                );
    }

    @PostMapping("/update")
    public BarbeiroResponse atualizar(
            @RequestBody BarbeiroRequest request
    ){

        if(request.id() == null){

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "ID obrigatorio"
            );
        }

        return BarbeiroResponse.fromEntity(
                barbeiroDao.update(request.toEntity())
        );
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id
    ){

        if(!barbeiroDao.deleteById(id)){

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Barbeiro nao encontrado"
            );
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/delete-all")
    @Operation(
            summary = "Remover todos",
            description = "Remove todos os barbeiros"
    )
    public ResponseEntity<Void> removerTodos(){

        barbeiroDao.deleteAll();

        return ResponseEntity.noContent().build();
    }
}