package org.aula.api.Barbeiro;

import org.aula.model.Barbeiro;

public record BarbeiroRequest(

        Long id,
        String nome,
        String telefone,
        String especialidade,
        Boolean ativo

) {

    public Barbeiro toEntity(){

        Barbeiro barbeiro = new Barbeiro();

        if(id != null){
            barbeiro.setId(id);
        }

        barbeiro.setNome(nome);
        barbeiro.setTelefone(telefone);
        barbeiro.setEspecialidade(especialidade);
        barbeiro.setAtivo(ativo);

        return barbeiro;
    }
}