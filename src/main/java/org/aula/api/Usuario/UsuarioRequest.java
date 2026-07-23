package org.aula.api.Usuario;

import org.aula.model.Usuario;

public record UsuarioRequest(

        Long id,
        String nome,
        String cpf,
        String telefone,
        String email,
        String senha,
        Boolean admin

) {

    public Usuario toEntity(){

        Usuario usuario = new Usuario();

        if(id != null){
            usuario.setId(id);
        }

        usuario.setNome(nome);
        usuario.setCpf(cpf);
        usuario.setTelefone(telefone);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setAdmin(admin);

        return usuario;
    }
}