# language: pt
Funcionalidade: Consultar Venda por ID

    Cenario de Fundo:
        Dado que foram inseridos generos a base de dados
        E que foram inseridos discos
        E que foram persistidos cashback semanal para cada genero
        E que foram realizadas vendas

    Cenario: 01 - Consultar Venda Existente
        Dado que a venda informada possua id "1"
        Entao deveria retonar uma venda com data em "2019-07-30"

    Cenario: 02 - Consultar Venda Inexistente
        Dado que a venda informada possua id "2"
        Entao deveria retornar um exception com status 404
