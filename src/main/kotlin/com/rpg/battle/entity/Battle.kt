package com.rpg.battle.entity

import jakarta.persistence.*

// Diz pro Spring que essa classe representa uma tabela no banco
@Entity
// Nome da tabela que vai ser criada no PostgreSQL
@Table(name = "battles")
// open porque o Hibernate precisa conseguir "herdar" essa classe internamente
open class Battle(

    // Chave primária — o banco gera o número sozinho (1, 2, 3...)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    // ID do primeiro personagem que entrou na batalha
    // Não guardamos o personagem inteiro, só o ID — menos peso no banco
    @Column(nullable = false)
    val personagem1Id: Long = 0,

    // ID do segundo personagem
    @Column(nullable = false)
    val personagem2Id: Long = 0,

    // HP atual do personagem 1 DURANTE a batalha
    // Começa igual ao HP original, mas vai caindo a cada ataque
    // var porque muda durante a batalha (diferente de val que nunca muda)
    @Column(nullable = false)
    var hpAtualP1: Int = 0,

    // HP atual do personagem 2 DURANTE a batalha
    @Column(nullable = false)
    var hpAtualP2: Int = 0,

    // Controla de quem é a vez agora
    // 1 = vez do personagem 1, 2 = vez do personagem 2
    @Column(nullable = false)
    var turnoAtual: Int = 1,

    // Nome do vencedor — fica null enquanto a batalha ainda está rolando
    // String? = o "?" significa que esse campo pode ser nulo
    @Column
    var vencedor: String? = null,

    // Estado da batalha — só dois valores possíveis: "ATIVA" ou "FINALIZADA"
    @Column(nullable = false)
    var status: String = "ATIVA"
)