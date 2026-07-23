package org.aula.api.Servico;

import org.aula.model.Servico;

public record ServicoRequest(

        Long id,
        String nome,
        Double valor,
        Integer duracao

) {

    public Servico toEntity(){

        Servico servico = new Servico();

        if(id != null){
            servico.setId(id);
        }

        servico.setNome(nome);
        servico.setValor(valor);
        servico.setDuracao(duracao);

        return servico;
    }
}