# ⚔️ RPG Battle Simulator

API REST de batalha RPG desenvolvida com Kotlin + Spring Boot + PostgreSQL.

## 🛠️ Tecnologias
- Kotlin + Spring Boot 4.0.6
- Maven
- PostgreSQL + JPA/Hibernate

## ▶️ Como rodar

### 1. Pré-requisitos
- JDK 21
- PostgreSQL instalado e rodando
- IntelliJ IDEA

### 2. Banco de dados
Crie o banco no PgAdmin:
```sql
CREATE DATABASE rpg_battle;
```

### 3. Configurar senha
Abra `src/main/resources/application.properties` e troque:
spring.datasource.password=SUA_SENHA_AQUI

### 4. Rodar o projeto
Abra no IntelliJ e clique em ▶️. O Hibernate cria as tabelas automaticamente.

### 5. Testar
Importe o arquivo `collection_postman.json` no Postman.

## 🌐 Endpoints

### Personagens
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | /characters | Criar personagem |
| GET | /characters | Listar todos |
| GET | /characters/{id} | Buscar por ID |
| PUT | /characters/{id} | Editar personagem |
| DELETE | /characters/{id} | Deletar personagem |

### Batalha
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | /battles?p1Id=X&p2Id=Y | Iniciar batalha |
| POST | /battles/{id}/attack?atacanteId=X | Atacar |
| GET | /battles/{id} | Ver estado da batalha |

### Chat
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | /msg?remetente=X&mensagem=Y | Enviar mensagem |
| POST | /ouvir | Receber mensagem |
| GET | /messages | Listar mensagens |

## 💬 Chat Peer-to-Peer
Cada máquina deve configurar o IP do rival no `application.properties`:
rival.url=http://IP_DO_RIVAL:8080/ouvir
Para descobrir o IP: rode `ipconfig` no terminal e use o IPv4.

## ⚔️ Regras de Batalha
- Dano = Ataque do atacante - Defesa do defensor
- Variação aleatória de ±20% no dano
- 30% de chance de acerto crítico (40% a mais de dano)
- Turnos alternados entre os jogadores
