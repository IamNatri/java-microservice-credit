package com.github.IamNatri.msclientes.application.representation;

import com.github.IamNatri.msclientes.domain.Cliente;
import lombok.Data;

@Data
public class ClienteSaveRequest {
    private String cpf;
    private String nome;
    private String email;
    private Integer idade;

    public Cliente toModel(){
        return new Cliente(cpf, nome, email, idade);
    }
}
