package com.github.iamnatri.mscartoes.infra.mqueue;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iamnatri.mscartoes.domain.Cartao;
import com.github.iamnatri.mscartoes.domain.ClienteCartao;
import com.github.iamnatri.mscartoes.domain.DadosSolicitacaoEmissaoCartao;
import com.github.iamnatri.mscartoes.infra.repository.CartaoRepository;
import com.github.iamnatri.mscartoes.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class EmissaoCartaoSubscriber {

    private final CartaoRepository cartaoRepository;
    private final ClienteCartaoRepository clienteCartaoRepository;

    @RabbitListener(queues = "${mq.queue.emissao-cartoes}")
    public void receberMensagemEmissaoCartao(@Payload String payload) {
        var mapper = new ObjectMapper();
        try {
            DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
            System.out.println("Recebendo solicitação de cartão: " + dados);
            Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();

            ClienteCartao clienteCartao = new ClienteCartao();
            clienteCartao.setCartao(cartao);
            clienteCartao.setCpf(dados.getCpf());
            clienteCartao.setLimite(dados.getLimiteLiberado());

            clienteCartaoRepository.save(clienteCartao);

        } catch (Exception e) {
            log.error("Erro ao processar solicitação de cartão: {}", e.getMessage());
        }



    }
}
