package org.aula.api.Agendamento;

import org.aula.model.Agendamento;
import org.aula.model.Barbeiro;
import org.aula.model.Servico;
import org.aula.model.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;

public record AgendamentoRequest(

        Long id,
        LocalDate data,
        LocalTime hora,
        String status,
        Long usuarioId,
        Long barbeiroId,
        Long servicoId

) {

    public Agendamento toEntity(){

        Agendamento agendamento = new Agendamento();

        if(id != null){
            agendamento.setId(id);
        }

        agendamento.setData(data);
        agendamento.setHora(hora);
        agendamento.setStatus(status);

        if(usuarioId != null){

            Usuario usuario = new Usuario();
            usuario.setId(usuarioId);

            agendamento.setUsuario(usuario);
        }

        if(barbeiroId != null){

            Barbeiro barbeiro = new Barbeiro();
            barbeiro.setId(barbeiroId);

            agendamento.setBarbeiro(barbeiro);
        }

        if(servicoId != null){

            Servico servico = new Servico();
            servico.setId(servicoId);

            agendamento.setServico(servico);
        }

        return agendamento;
    }
}