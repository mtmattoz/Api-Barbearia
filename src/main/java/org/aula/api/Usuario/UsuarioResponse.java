package org.aula.api.Usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import org.aula.model.Usuario;

@Schema(description = "Dados retornados de um usuário")
public record UsuarioResponse(

        @Schema(description = "ID do usuário", example = "1")
        Long id,

        @Schema(description = "Nome do usuário", example = "Mateus")
        String nome,

        @Schema(description = "CPF", example = "12345678900")
        String cpf,

        @Schema(description = "Telefone", example = "47999999999")
        String telefone,

        @Schema(description = "Email", example = "mateus@gmail.com")
        String email,

        @Schema(description = "Administrador", example = "false")
        Boolean admin

) {

    public static UsuarioResponse fromEntity(Usuario usuario){

        return new UsuarioResponse(

                usuario.getId(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getTelefone(),
                usuario.getEmail(),
                usuario.getAdmin()
        );
    }
}