package com.github.iamnatri.msavaliadorcredito.infra.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iamnatri.msavaliadorcredito.domain.model.DadosSolicitacaoEmissaoCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SolicitacaoEmissaoCartaoPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queueEmissaoCartoes;

    public void solicitarCartao(DadosSolicitacaoEmissaoCartao payload) throws JsonProcessingException {
        var json = convertIntoJson(payload);
        rabbitTemplate.convertAndSend(queueEmissaoCartoes.getName(), json);

    }
    private String convertIntoJson(DadosSolicitacaoEmissaoCartao payload) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(payload);
    }
}
