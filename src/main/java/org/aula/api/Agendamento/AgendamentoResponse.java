package org.aula.api.Agendamento;

import io.swagger.v3.oas.annotations.media.Schema;
import org.aula.model.Agendamento;

import java.time.LocalDate;
import java.time.LocalTime;

@Schema(description = "Dados retornados de um agendamento")
public record AgendamentoResponse(

        @Schema(description = "ID do agendamento", example = "1")
        Long id,

        @Schema(description = "Data do agendamento", example = "2026-07-25")
        LocalDate data,

        @Schema(description = "Hora do agendamento", example = "14:30")
        LocalTime hora,

        @Schema(description = "Status", example = "AGENDADO")
        String status,

        @Schema(description = "ID do usuário", example = "1")
        Long usuarioId,

        @Schema(description = "ID do barbeiro", example = "1")
        Long barbeiroId,

        @Schema(description = "ID do serviço", example = "1")
        Long servicoId

) {

    public static AgendamentoResponse fromEntity(Agendamento agendamento){

        return new AgendamentoResponse(

                agendamento.getId(),
                agendamento.getData(),
                agendamento.getHora(),
                agendamento.getStatus(),
                agendamento.getUsuario().getId(),
                agendamento.getBarbeiro().getId(),
                agendamento.getServico().getId()
        );
    }
}