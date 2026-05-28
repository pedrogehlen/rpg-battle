package com.rpg.battle.repository

import com.rpg.battle.entity.Battle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

// Repositório da batalha
// Herdar JpaRepository<Battle, Long> significa:
// — Battle = qual tabela esse repositório gerencia
// — Long = qual é o tipo do ID dessa tabela
// O Spring gera automaticamente: save(), findAll(), findById(), deleteById()...
@Repository
interface BattleRepository : JpaRepository<Battle, Long>