package com.rpg.battle.service

import com.rpg.battle.entity.Character
import com.rpg.battle.repository.CharacterRepository
import org.springframework.stereotype.Service

// 🎮 Gerente do jogo — contém todas as regras de negócio dos personagens
@Service
class CharacterService(
    // 💉 O Spring injeta o repositório automaticamente (injeção de dependência)
    private val characterRepository: CharacterRepository
) {

    // ➕ Cria e salva um novo personagem no banco
    fun criar(character: Character): Character {
        return characterRepository.save(character)
    }

    // 📋 Retorna a lista de todos os personagens salvos
    fun listarTodos(): List<Character> {
        return characterRepository.findAll()
    }

    // 🔍 Busca um personagem pelo ID — lança erro se não encontrar
    fun buscarPorId(id: Long): Character {
        return characterRepository.findById(id)
            .orElseThrow { RuntimeException("Personagem não encontrado!") }
    }

    // ✏️ Atualiza os dados de um personagem existente
    fun atualizar(id: Long, character: Character): Character {
        buscarPorId(id) // verifica se existe antes de atualizar
        return characterRepository.save(character.copy(id = id))
    }

    // 🗑️ Deleta um personagem pelo ID
    fun deletar(id: Long) {
        buscarPorId(id) // verifica se existe antes de deletar
        characterRepository.deleteById(id)
    }
}