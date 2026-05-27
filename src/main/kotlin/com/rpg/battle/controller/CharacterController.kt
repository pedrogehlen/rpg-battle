package com.rpg.battle.controller

import com.rpg.battle.entity.Character
import com.rpg.battle.service.CharacterService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// 🚪 Recepção da API — recebe todas as requisições de personagens
@RestController
// 📍 Define o endereço base: /characters
@RequestMapping("/characters")
class CharacterController(
    // 💉 O Spring injeta o service automaticamente
    private val characterService: CharacterService
) {

    // ➕ POST /characters — cria um novo personagem
    @PostMapping
    fun criar(@RequestBody character: Character): ResponseEntity<Character> {
        return ResponseEntity.ok(characterService.criar(character))
    }

    // 📋 GET /characters — lista todos os personagens
    @GetMapping
    fun listarTodos(): ResponseEntity<List<Character>> {
        return ResponseEntity.ok(characterService.listarTodos())
    }

    // 🔍 GET /characters/{id} — busca personagem por ID
    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): ResponseEntity<Character> {
        return ResponseEntity.ok(characterService.buscarPorId(id))
    }

    // ✏️ PUT /characters/{id} — atualiza personagem
    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: Long, @RequestBody character: Character): ResponseEntity<Character> {
        return ResponseEntity.ok(characterService.atualizar(id, character))
    }

    // 🗑️ DELETE /characters/{id} — deleta personagem
    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Long): ResponseEntity<Void> {
        characterService.deletar(id)
        return ResponseEntity.noContent().build()
    }
}