package br.com.rafaelalmeida.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("primeiraRota")
public class MinhaPrimeiraController {

    /**
     * GET - BUSCAR INFORMAÇÕES
     * POST - ADICIONAR UM DADO/INFORMAÇÃO
     * PUT - ALTERAR UM DADO/INFORMAÇÃO
     * DELETE - REMOVER UM DADO
     * PATCH - ALTERAR SOMENTE UMA PARTE DA INFO/DADO
     */

    
    //metodo (funcionalidade) de uma classe
    @GetMapping("/")
    public String primeiraMensagem() {
        return "Minha primeira mensagem";
    }
}
