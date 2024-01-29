package com.github.IamNatri.msclientes.application;

import com.github.IamNatri.msclientes.domain.Cliente;
import com.github.IamNatri.msclientes.infra.repository.ClienteRepository;


import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    @Transactional
    public Cliente save(Cliente cliente){
        return repository.save(cliente);
    }

    public Optional<Cliente> getByCPF(String cpf){
        return repository.findByCpf(cpf);
    }

}
