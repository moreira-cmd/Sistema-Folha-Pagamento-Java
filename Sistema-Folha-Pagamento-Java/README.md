# Sistema de Folha de Pagamento em Java

Sistema que fiz (Guilherme Moreira de Jesus) em base de atividade acadêmica e consequentemente para o meu portifólio, desenvolvido um sistema de folha de pagamento, com funções de descontos, 

## Funcionalidades

- Cadastro de funcionários com matrícula automática
- Listagem e consulta por matrícula
- Atualização de dados e salário
- Ativação e desativação de funcionários
- Geração de folha por competência
- Cálculo de horas extras com adicional de 50%
- Cálculo de desconto por horas de falta
- Inclusão de adicionais e outros descontos informados
- Consulta do histórico de folhas
- Persistência local dos dados em arquivos binários

## Tecnologias e conceitos

- Java
- Programação Orientada a Objetos
- Encapsulamento
- Collections
- BigDecimal para valores monetários
- Tratamento de exceções
- Serialização de objetos
- Organização em camadas

## Requisitos

JDK 17 ou superior.

## Como executar

Na pasta raiz do projeto, compile:

```bash
javac -encoding UTF-8 -d out src/Main.java src/model/*.java src/service/*.java src/repository/*.java src/util/*.java
```

Execute:

```bash
java -cp out Main
```

Os arquivos de dados serão criados automaticamente na pasta `dados/`. Essa pasta é ignorada pelo Git para que dados locais de execução não sejam enviados ao repositório.

## Regras de cálculo

- Valor da hora normal: salário-base dividido por 220.
- Hora extra: valor da hora normal acrescido de 50%.
- Desconto por falta: valor da hora normal multiplicado pela quantidade de horas de falta.
- Salário bruto: salário-base + horas extras + adicionais.
- Salário líquido: salário bruto - desconto por faltas - outros descontos.

Os cálculos são simplificados para fins acadêmicos. Não representam uma folha legal completa e não calculam automaticamente INSS, IRRF, FGTS, férias, décimo terceiro ou encargos patronais. Os campos de outros descontos e adicionais são informados pelo operador.

## Persistência

Os cadastros e folhas são armazenados localmente em `dados/` usando serialização nativa do Java. Não é necessário instalar banco de dados ou dependências externas.

## Observação

Projeto desenvolvido para fins educacionais.
