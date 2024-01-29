package com.github.iamnatri.msavaliadorcredito.application;

import com.github.iamnatri.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import com.github.iamnatri.msavaliadorcredito.application.ex.ErroComunicacaoMicroserviceException;
import com.github.iamnatri.msavaliadorcredito.domain.model.CartaoCliente;
import com.github.iamnatri.msavaliadorcredito.domain.model.DadosCliente;
import com.github.iamnatri.msavaliadorcredito.domain.model.SituacaoCliente;
import com.github.iamnatri.msavaliadorcredito.infra.clients.CartoesResourceClient;
import com.github.iamnatri.msavaliadorcredito.infra.clients.ClienteResourceClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;
    private final CartoesResourceClient cartoesClient;


    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroserviceException {
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
}
