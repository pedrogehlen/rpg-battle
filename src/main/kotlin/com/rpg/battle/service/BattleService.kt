package com.rpg.battle.service

import com.rpg.battle.entity.Battle
import com.rpg.battle.entity.Character
import com.rpg.battle.repository.BattleRepository
import com.rpg.battle.repository.CharacterRepository
import org.springframework.stereotype.Service
import kotlin.random.Random

// Gerente das batalhas — aqui ficam todas as regras do jogo
@Service
class BattleService(
    // O Spring injeta os repositórios automaticamente
    // Injeção de dependência = o Spring entrega as ferramentas que você precisa
    private val battleRepository: BattleRepository,
    private val characterRepository: CharacterRepository
) {

    // Inicia uma batalha entre dois personagens
    fun iniciar(p1Id: Long, p2Id: Long): Battle {

        // Vai no banco e busca o personagem 1 pelo ID
        // Se não achar, lança erro na hora
        val p1 = characterRepository.findById(p1Id)
            .orElseThrow { RuntimeException("Personagem 1 não encontrado!") }

        // Mesma coisa pro personagem 2
        val p2 = characterRepository.findById(p2Id)
            .orElseThrow { RuntimeException("Personagem 2 não encontrado!") }

        // Monta o objeto batalha com os dados dos dois personagens
        // O HP começa igual ao HP original do personagem lá do banco
        val battle = Battle(
            personagem1Id = p1.id,
            personagem2Id = p2.id,
            hpAtualP1 = p1.hp,
            hpAtualP2 = p2.hp
        )

        // Salva a batalha no banco e retorna ela
        return battleRepository.save(battle)
    }

    // Busca o estado atual de uma batalha pelo ID
    fun buscar(id: Long): Battle {
        return battleRepository.findById(id)
            .orElseThrow { RuntimeException("Batalha não encontrada!") }
    }

    // Recebe o ID da batalha e o ID de quem quer atacar
    fun atacar(battleId: Long, atacanteId: Long): Battle {

        // Busca a batalha no banco pelo ID
        val battle = buscar(battleId)

        // Se a batalha já terminou, não deixa atacar de novo
        if (battle.status == "FINALIZADA") {
            throw RuntimeException("Batalha já finalizada!")
        }

        // Busca os dois personagens no banco
        // .get() pega o valor direto — já sabemos que existem pois a batalha foi criada com eles
        val p1 = characterRepository.findById(battle.personagem1Id).get()
        val p2 = characterRepository.findById(battle.personagem2Id).get()

        // Variáveis que vamos preencher logo abaixo
        val atacante: Character
        val defensor: Character
        val ehP1Atacando: Boolean

        // Se o personagem 1 está tentando atacar E é o turno dele (turno 1)
        if (atacanteId == battle.personagem1Id && battle.turnoAtual == 1) {
            atacante = p1
            defensor = p2
            ehP1Atacando = true
            // Se o personagem 2 está tentando atacar E é o turno dele (turno 2)
        } else if (atacanteId == battle.personagem2Id && battle.turnoAtual == 2) {
            atacante = p2
            defensor = p1
            ehP1Atacando = false
            // Tentou atacar fora do seu turno — barra a jogada
        } else {
            throw RuntimeException("Não é o turno deste personagem!")
        }

        // Dano base: ataque do atacante menos a defesa do defensor
        val danoBase = atacante.ataque - defensor.defesa

        // Calcula 20% do dano base para usar como margem de variação
        // Exemplo: danoBase 35 → variacao = 7 → dano entre 28 e 42
        val variacao = (danoBase * 0.2).toInt()

        // Sorteia um número aleatório dentro da margem de variação
        // nextInt(-variacao, variacao + 1) sorteia entre -7 e +7 por exemplo
        val danoAleatorio = danoBase + Random.nextInt(-variacao, variacao + 1)

        // Garante que o dano mínimo é sempre 0
        val danoSemCritico = maxOf(0, danoAleatorio)

        // Sorteia um número entre 0.0 e 1.0
        // Se cair abaixo de 0.3 (30% de chance) → é crítico!
        val critico = Random.nextFloat() < 0.3f

        // Se foi crítico, multiplica por 1.4 (40% a mais)
        // Se não foi, usa o dano normal
        val danoFinal = if (critico) (danoSemCritico * 1.4).toInt() else danoSemCritico

        // Aplica o dano no HP de quem está defendendo
        if (ehP1Atacando) {
            battle.hpAtualP2 -= danoFinal  // P1 atacou, então P2 perde HP
        } else {
            battle.hpAtualP1 -= danoFinal  // P2 atacou, então P1 perde HP
        }

        // Verifica se alguém morreu depois do ataque
        if (battle.hpAtualP1 <= 0) {
            battle.vencedor = p2.nome      // P1 chegou a 0, P2 ganhou
            battle.status = "FINALIZADA"
        } else if (battle.hpAtualP2 <= 0) {
            battle.vencedor = p1.nome      // P2 chegou a 0, P1 ganhou
            battle.status = "FINALIZADA"
        } else {
            // Ninguém morreu — passa o turno pro outro jogador
            // Se era turno 1, vira turno 2. Se era turno 2, vira turno 1.
            battle.turnoAtual = if (battle.turnoAtual == 1) 2 else 1
        }

        // Salva o estado atualizado da batalha no banco e retorna
        return battleRepository.save(battle)
    }
}