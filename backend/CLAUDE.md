# AGENTS.md

## Contexto do projeto

- Backend Java 25 com Micronaut.
- Build com Gradle Wrapper (`./gradlew`).
- Persistencia com Micronaut Data e Hibernate JPA.
- Banco principal configurado para MySQL.
- Testes com Spock, H2 e ArchUnit.

## Estrutura esperada

- `src/main/java/com/devhouse/core`: dominio, modelos e portas.
- `src/main/java/com/devhouse/adapters/inbound`: controllers HTTP, DTOs e conversores.
- `src/main/java/com/devhouse/adapters/outbound`: persistencia e integracoes externas.
- `src/test`: testes de arquitetura, unidade e integracao.

## Comandos uteis

- Validar alteracoes: `./gradlew test`
- Subir a aplicacao: `./gradlew run`
- Gerar build: `./gradlew build`
- Subir banco local: `docker compose up -d`

## Regras de trabalho

- Preserve a arquitetura hexagonal existente.
- Nao mova regra de negocio para controllers ou entidades JPA.
- Prefira mudancas pequenas e localizadas.
- Ao alterar comportamento, rode pelo menos os testes relacionados.
- Nao altere infraestrutura sem necessidade direta da tarefa.

## Configuracao local

- Aplicacao: `icr-church`
- Banco: `jdbc:mysql://localhost:3306/icr`
- Usuario: `root`
- Senha: `root`

## Regras basicas do repositorio

- Preserve a arquitetura hexagonal existente.
- Nao mova regra de negocio para controllers ou entidades JPA.
- Valide alteracoes com `./gradlew test` sempre que a mudanca afetar comportamento.
