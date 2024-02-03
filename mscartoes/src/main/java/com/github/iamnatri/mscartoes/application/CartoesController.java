package com.github.iamnatri.mscartoes.application;

import com.github.iamnatri.mscartoes.application.representation.CartoesPorClienteReponse;
import com.github.iamnatri.mscartoes.domain.Cartao;
import com.github.iamnatri.mscartoes.domain.ClienteCartao;
import com.github.iamnatri.mscartoes.application.representation.CartaoSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartoesController {

    private final CartaoService CartaoService;
    private final ClienteCartaoService clienteCartaoService;

    @GetMapping
    public String status(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity cadastrarCartao(@RequestBody CartaoSaveRequest request){
        Cartao cartao = request.toDomain();
        CartaoService.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda){
        List<Cartao> cartoes = CartaoService.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(cartoes);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClienteReponse>> getCartoesByCliente(
            @ RequestParam("cpf") String cpf){
        List<ClienteCartao> lista = clienteCartaoService.listCartoesByCpf(cpf);
        List<CartoesPorClienteReponse> cartoes = lista.stream()
                .map(CartoesPorClienteReponse::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cartoes);
    }


}
