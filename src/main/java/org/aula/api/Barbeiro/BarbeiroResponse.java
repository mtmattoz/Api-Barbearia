package org.aula.api.Barbeiro;

import io.swagger.v3.oas.annotations.media.Schema;
import org.aula.model.Barbeiro;

@Schema(description = "Dados retornados de um barbeiro")
public record BarbeiroResponse(

        @Schema(description = "ID do barbeiro", example = "1")
        Long id,

        @Schema(description = "Nome", example = "João")
        String nome,

        @Schema(description = "Telefone", example = "47999999999")
        String telefone,

        @Schema(description = "Especialidade", example = "Corte Masculino")
        String especialidade,

        @Schema(description = "Barbeiro ativo", example = "true")
        Boolean ativo

) {

    public static BarbeiroResponse fromEntity(Barbeiro barbeiro){

        return new BarbeiroResponse(

                barbeiro.getId(),
                barbeiro.getNome(),
                barbeiro.getTelefone(),
                barbeiro.getEspecialidade(),
                barbeiro.getAtivo()
        );
    }
}