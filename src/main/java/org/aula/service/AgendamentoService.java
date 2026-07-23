package org.aula.service;

import org.aula.dao.AgendamentoDao;
import org.aula.model.Agendamento;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

    private final AgendamentoDao agendamentoDao;

    public AgendamentoService(AgendamentoDao agendamentoDao) {
        this.agendamentoDao = agendamentoDao;
    }

    public Agendamento agendar(Agendamento agendamento) {

        boolean horarioOcupado =
                agendamentoDao.existeAgendamento(
                        agendamento.getBarbeiro().getId(),
                        agendamento.getData(),
                        agendamento.getHora()
                );

        if (horarioOcupado) {
            throw new RuntimeException(
                    "Este barbeiro já possui um agendamento neste horário."
            );
        }

        return agendamentoDao.create(agendamento);
    }
}