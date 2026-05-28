package com.rpg.battle.repository

import com.rpg.battle.entity.Message
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

// Repositório das mensagens — o Spring gera os métodos automaticamente
@Repository
interface MessageRepository : JpaRepository<Message, Long>