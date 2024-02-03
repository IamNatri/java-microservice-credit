package com.github.iamnatri.msavaliadorcredito.application;

import com.github.iamnatri.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import com.github.iamnatri.msavaliadorcredito.application.ex.ErroComunicacaoMicroserviceException;
import com.github.iamnatri.msavaliadorcredito.application.ex.ErroSolicitacaoCartaoException;
import com.github.iamnatri.msavaliadorcredito.domain.model.*;
import com.github.iamnatri.msavaliadorcredito.infra.clients.CartoesResourceClient;
import com.github.iamnatri.msavaliadorcredito.infra.clients.ClienteResourceClient;
import com.github.iamnatri.msavaliadorcredito.infra.mq.SolicitacaoEmissaoCartaoPublisher;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;
    private final CartoesResourceClient cartoesClient;
    private final SolicitacaoEmissaoCartaoPublisher emissaoCartaoPublisher;



    public SituacaoCliente obterSituacaoCliente(String cpf)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroserviceException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf);

            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();
        }catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException();

            }
            throw new ErroComunicacaoMicroserviceException(e.getMessage(), status);


        }
    }

    public RetornoAvaliacaoCliente avaliarCredito(String cpf, Long renda)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroserviceException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);
               ResponseEntity<List<Cartao>> cartoesResponse = cartoesClient.getCartoesRendaAte(renda);


               List<Cartao> cartoes = cartoesResponse.getBody();

            try {
                var listaCartoesAprovados = cartoes.stream().map(cartao -> {

                    DadosCliente dadosCliente = dadosClienteResponse.getBody();

                    BigDecimal limiteBasico = cartao.getLimiteBasico();
                    BigDecimal idadebd = BigDecimal.valueOf(dadosCliente.getIdade());
                    var fatorIdade = idadebd.divide(BigDecimal.valueOf(100));
                    BigDecimal rendaCliente = BigDecimal.valueOf(renda);
                    BigDecimal fatorRendaCliente = rendaCliente.divide(BigDecimal.valueOf(10000));


                    BigDecimal limiteAprovado = fatorRendaCliente.multiply(limiteBasico);

                    CartaoAprovado cartaoAprovado = new CartaoAprovado();
                    cartaoAprovado.setCartao(cartao.getNome());
                    cartaoAprovado.setBandeira(cartao.getBandeira());
                    cartaoAprovado.setLimiteAprovado(limiteAprovado);

                    return cartaoAprovado;
                }).collect(Collectors.toList());

                return new RetornoAvaliacaoCliente(listaCartoesAprovados);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        }catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException();

            }
            throw new ErroComunicacaoMicroserviceException(e.getMessage(), status);


        }

    }
    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao payload) {
        try {
            emissaoCartaoPublisher.solicitarCartao(payload);
            var protocolo = UUID.randomUUID().toString();
            return new ProtocoloSolicitacaoCartao(protocolo);
        } catch (Exception e) {
            throw new ErroSolicitacaoCartaoException(e.getMessage());
        }
    }
}
