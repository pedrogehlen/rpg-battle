package com.rpg.battle.entity

import jakarta.persistence.*
import java.time.LocalDateTime

// Representa uma mensagem do chat — cada mensagem vira uma linha no banco
@Entity
@Table(name = "messages")
open class Message(

    // ID gerado automaticamente pelo banco
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    // Nome de quem enviou a mensagem
    @Column(nullable = false)
    val remetente: String = "",

    // Texto da mensagem
    @Column(nullable = false)
    val conteudo: String = "",

    // Data e hora exata do envio — gerada automaticamente
    @Column(nullable = false)
    val timestamp: LocalDateTime = LocalDateTime.now()
)