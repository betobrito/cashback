# language: pt
Funcionalidade: Consultar Venda por ID

    Cenario de Fundo:
        Dado que foram inseridos generos a base de dados
        E que foram inseridos discos
        E que foram realizadas vendas

    Cenario: 01 - Consultar Disco Existente
        Dado que o disco informado possua id "1"
        Entao deveria retornar um disco com descricao "AC/DC 101"
        E que pertenca ao genero "ROCK"

    Cenario: 02 - Consultar Disco Inexistente
        Dado que o disco informado possua id "10"
        Entao deveria retornar um exception com status 404
