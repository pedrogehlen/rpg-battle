package com.rpg.battle.controller

import com.rpg.battle.entity.Message
import com.rpg.battle.service.ChatService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// Recepção da API para o chat
@RestController
class ChatController(
    private val chatService: ChatService
) {

    // GET /msg?remetente=Pedro&mensagem=Vou te atacar!
    // Você chama esse endpoint para ENVIAR uma mensagem pro rival
    @GetMapping("/msg")
    fun enviar(
        @RequestParam remetente: String,
        @RequestParam mensagem: String
    ): ResponseEntity<String> {
        return ResponseEntity.ok(chatService.enviar(remetente, mensagem))
    }

    // POST /ouvir
    // O Spring Boot do RIVAL chama esse endpoint na sua máquina
    // Você nunca chama esse direto — ele é chamado automaticamente quando o rival envia mensagem
    @PostMapping("/ouvir")
    fun receber(@RequestBody body: Map<String, String>): ResponseEntity<Message> {
        val remetente = body["remetente"] ?: "Desconhecido"
        val conteudo = body["conteudo"] ?: ""
        return ResponseEntity.ok(chatService.receber(remetente, conteudo))
    }

    // GET /messages — lista todas as mensagens salvas no banco
    @GetMapping("/messages")
    fun listarTodas(): ResponseEntity<List<Message>> {
        return ResponseEntity.ok(chatService.listarTodas())
    }
}