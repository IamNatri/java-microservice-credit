package com.github.iamnatri.mscartoes.application.representation;

import com.github.iamnatri.mscartoes.domain.ClienteCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartoesPorClienteReponse {
    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;

    public static CartoesPorClienteReponse fromDomain(ClienteCartao domain){
        return new CartoesPorClienteReponse(
            domain.getCartao().getNome(),
            domain.getCartao().getBandeira().name(),
            domain.getLimite()
        );
    }
}
