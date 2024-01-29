package com.github.iamnatri.mscartoes.application.representation;

import com.github.iamnatri.mscartoes.domain.BandeiraCartao;
import com.github.iamnatri.mscartoes.domain.Cartao;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoSaveRequest {
    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limite;

    public Cartao toDomain(){
        return new Cartao(nome, bandeira, renda, limite);
    }
}
