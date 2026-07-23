package org.aula.api.Servico;

import io.swagger.v3.oas.annotations.media.Schema;
import org.aula.model.Servico;

@Schema(description = "Dados retornados de um serviço")
public record ServicoResponse(

        @Schema(description = "ID do serviço", example = "1")
        Long id,

        @Schema(description = "Nome", example = "Corte Masculino")
        String nome,

        @Schema(description = "Valor", example = "40.0")
        Double valor,

        @Schema(description = "Duração em minutos", example = "30")
        Integer duracao

) {

    public static ServicoResponse fromEntity(Servico servico){

        return new ServicoResponse(

                servico.getId(),
                servico.getNome(),
                servico.getValor(),
                servico.getDuracao()
        );
    }
}