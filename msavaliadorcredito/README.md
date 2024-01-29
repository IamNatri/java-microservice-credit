biblioteca utilizada para se comunicar com os outros micro serviços cartoes e clientes

biblioteca - Spring-cloud-starter-openfeign

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

```java
@FeignClient(name = "ms-cartao", url = "http://localhost:8888/api/cartoes")
public interface CartaoClient {

    @GetMapping("/{id}")
    CartaoResponse buscaCartao(@PathVariable("id") String id);
}
```

```java
@FeignClient(name = "ms-cliente", url = "http://localhost:8080/api/clientes")
public interface ClienteClient {

    @GetMapping("/{id}")
    ClienteResponse buscaCliente(@PathVariable("id") String id);
}
```


