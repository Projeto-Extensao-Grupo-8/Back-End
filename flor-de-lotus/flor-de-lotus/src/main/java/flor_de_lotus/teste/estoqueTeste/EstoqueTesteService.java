package flor_de_lotus.teste.estoqueTeste;

import flor_de_lotus.teste.Teste;
import flor_de_lotus.teste.TesteService;
import flor_de_lotus.teste.estoqueTeste.dto.EstoqueTesteRequest;
import flor_de_lotus.teste.estoqueTeste.dto.EstoqueTesteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstoqueTesteService {
    private final EstoqueTesteRespository respository;
    private final TesteService Testeservice;



}
