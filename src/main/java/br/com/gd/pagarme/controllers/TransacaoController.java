package br.com.gd.pagarme.controllers;

import br.com.gd.pagarme.dtos.requests.TransacaoRequestDTO;
import br.com.gd.pagarme.dtos.responses.SaldoResponseDTO;
import br.com.gd.pagarme.dtos.responses.TransacaoResponseDTO;
import br.com.gd.pagarme.facades.TransacaoFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping ("api/v1/transacoes")
public class TransacaoController {
    @Autowired
    private TransacaoFacade transacaoFacade;
    @PostMapping
    public ResponseEntity<TransacaoResponseDTO> salvar (@RequestBody @Valid TransacaoRequestDTO transacaoRequestDTO){
        return new ResponseEntity<>(transacaoFacade.salvar(transacaoRequestDTO), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<TransacaoResponseDTO>> listar (){
        return new ResponseEntity<>(transacaoFacade.listar(), HttpStatus.OK);
    }
    @GetMapping("/saldo")
    public ResponseEntity<SaldoResponseDTO> consultarSaldo (){
        return new ResponseEntity<>(transacaoFacade.consultarSaldo(), HttpStatus.ACCEPTED);
    }
    @DeleteMapping
    public ResponseEntity<Void> deletar (){
        transacaoFacade.deletar();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
