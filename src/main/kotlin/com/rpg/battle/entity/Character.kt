package com.rpg.battle.entity

import jakarta.persistence.*

// 🏰 Diz pro Spring que essa classe representa uma tabela no banco
@Entity
// 📋 Define o nome da tabela no banco de dados
@Table(name = "characters")

data class Character(
    // 🔑 Chave primária — identificador único de cada personagem
    @Id // ⚙️ O banco gera o ID automaticamente (1, 2, 3...)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    // ⚔️ Atributos do personagem — cada um vira uma coluna na tabela
    @Column(nullable = false)
    val nome: String = "",

    @Column(nullable = false)
    val classe: String = "",

    @Column(nullable = false)
    val hp: Int = 0,

    @Column(nullable = false)
    val ataque: Int = 0,

    @Column(nullable = false)
    val defesa: Int = 0,

    @Column(nullable = false)
    val level: Int = 1
)