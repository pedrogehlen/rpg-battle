package com.rpg.battle.controller

import com.rpg.battle.entity.Battle
import com.rpg.battle.service.BattleService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// Recepção da API para batalhas — recebe todas as requisições de batalha
@RestController
// Todas as rotas daqui começam com /battles
@RequestMapping("/battles")
class BattleController(
    // O Spring injeta o service automaticamente
    private val battleService: BattleService
) {

    // POST /battles?p1Id=1&p2Id=2
    // Inicia uma batalha entre o personagem de ID 1 e o de ID 2
    // @RequestParam = pega os valores que vêm depois do "?" na URL
    @PostMapping
    fun iniciar(
        @RequestParam p1Id: Long,
        @RequestParam p2Id: Long
    ): ResponseEntity<Battle> {
        return ResponseEntity.ok(battleService.iniciar(p1Id, p2Id))
    }

    // GET /battles/1
    // Retorna o estado atual da batalha de ID 1 (HP, turno, vencedor...)
    // @PathVariable = pega o valor que vem dentro da URL (o número após a barra)
    @GetMapping("/{id}")
    fun buscar(@PathVariable id: Long): ResponseEntity<Battle> {
        return ResponseEntity.ok(battleService.buscar(id))
    }

    // POST /battles/1/attack?atacanteId=1
    // Personagem de ID 1 ataca na batalha de ID 1
    // Usa dois parâmetros: ID da batalha vem na URL, ID do atacante vem no "?"
    @PostMapping("/{id}/attack")
    fun atacar(
        @PathVariable id: Long,
        @RequestParam atacanteId: Long
    ): ResponseEntity<Battle> {
        return ResponseEntity.ok(battleService.atacar(id, atacanteId))
    }
}