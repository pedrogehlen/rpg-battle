package com.rpg.battle.service

import com.rpg.battle.entity.Message
import com.rpg.battle.repository.MessageRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

// Gerente do chat — envia e recebe mensagens
@Service
class ChatService(
    private val messageRepository: MessageRepository
) {

    // Pega o IP do rival do application.properties
    // O @Value injeta o valor da propriedade rival.url automaticamente
    @Value("\${rival.url}")
    private lateinit var rivalUrl: String

    // Envia mensagem pro rival e salva no banco
    fun enviar(remetente: String, conteudo: String): String {

        // Salva a mensagem no banco local primeiro
        val mensagem = Message(remetente = remetente, conteudo = conteudo)
        messageRepository.save(mensagem)

        // RestTemplate é o "carteiro" — ele faz requisições HTTP pra outros servidores
        val restTemplate = RestTemplate()

        // Monta o objeto que vai ser enviado pro rival
        val corpo = mapOf("remetente" to remetente, "conteudo" to conteudo)

        // Faz POST direto no IP do rival com a mensagem
        restTemplate.postForObject(rivalUrl, corpo, String::class.java)

        return "Mensagem enviada!"
    }

    // Recebe mensagem do rival e salva no banco
    fun receber(remetente: String, conteudo: String): Message {
        val mensagem = Message(remetente = remetente, conteudo = conteudo)
        return messageRepository.save(mensagem)
    }

    // Lista todas as mensagens salvas no banco
    fun listarTodas(): List<Message> {
        return messageRepository.findAll()
    }
}