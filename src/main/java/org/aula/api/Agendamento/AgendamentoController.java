package org.aula.api.Agendamento;

import io.swagger.v3.oas.annotations.Operation;
import org.aula.dao.AgendamentoDao;
import org.aula.model.Agendamento;
import org.aula.service.AgendamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {

    private final AgendamentoDao agendamentoDao;
    private final AgendamentoService agendamentoService;

    public AgendamentoController(
            AgendamentoDao agendamentoDao,
            AgendamentoService agendamentoService
    ) {

        this.agendamentoDao = agendamentoDao;
        this.agendamentoService = agendamentoService;
    }

    @GetMapping
    @Operation(summary = "Listar agendamentos")
    public List<AgendamentoResponse> listar(){

        return agendamentoDao.findAll()
                .stream()
                .map(AgendamentoResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar agendamento por ID")
    public AgendamentoResponse buscarPorId(
            @PathVariable Long id
    ){

        Agendamento agendamento =
                agendamentoDao.findById(id);

        if(agendamento == null){

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Agendamento nao encontrado"
            );
        }

        return AgendamentoResponse.fromEntity(
                agendamento
        );
    }

    @GetMapping("/next-id")
    public long proximoId(){

        return agendamentoDao.nextId();
    }

    @PostMapping("/create")
    @Operation(summary = "Criar agendamento")
    public ResponseEntity<AgendamentoResponse> criar(
            @RequestBody AgendamentoRequest request
    ){

        Agendamento agendamento =
                request.toEntity();

        if(request.id() == null){

            agendamento.setId(
                    agendamentoDao.nextId()
            );
        }

        agendamento =
                agendamentoService.agendar(
                        agendamento
                );

        return ResponseEntity.status(
                HttpStatus.CREATED
        ).body(
                AgendamentoResponse.fromEntity(
                        agendamento
                )
        );
    }

    @PostMapping("/update")
    @Operation(summary = "Atualizar agendamento")
    public AgendamentoResponse atualizar(
            @RequestBody AgendamentoRequest request
    ){

        if(request.id() == null){

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "ID obrigatorio"
            );
        }

        return AgendamentoResponse.fromEntity(

                agendamentoDao.update(
                        request.toEntity()
                )
        );
    }

    @PostMapping("/{id}/delete")
    @Operation(summary = "Excluir agendamento")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id
    ){

        if(!agendamentoDao.deleteById(id)){

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Agendamento nao encontrado"
            );
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/delete-all")
    @Operation(summary = "Excluir todos")
    public ResponseEntity<Void> removerTodos(){

        agendamentoDao.deleteAll();

        return ResponseEntity.noContent().build();
    }

}