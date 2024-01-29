package com.github.IamNatri.msclientes.domain;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data // lombok map the getters and setters
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String cpf;
    @Column
    private String nome;
    @Column
    private String email;
    @Column
    private Integer idade;

    public Cliente(String cpf, String nome, String email, Integer idade) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.idade = idade;
    }


}
