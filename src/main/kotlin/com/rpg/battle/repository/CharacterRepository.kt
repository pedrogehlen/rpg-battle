package com.rpg.battle.repository

import com.rpg.battle.entity.Character
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

// 📦 Repositório do personagem — herda todos os métodos do JpaRepository
// Character = qual entidade gerenciar, Long = tipo do ID
@Repository
interface CharacterRepository : JpaRepository<Character, Long>