# language: pt
Funcionalidade: Consultar Vendas por Período

    Cenario de Fundo:
        Dado que foram inseridos generos a base de dados
        E que foram inseridos discos
        E que foram persistidos cashback semanal para cada genero
        E que foram realizadas vendas

    Cenario: 01 - Consultar por periodo com Venda Existente
        Dado que foi informado um periodo de "2019-07-29" a "2019-07-30" e paginacao retornando os primeiros "10" registros
        Entao deveria retonar uma lista com uma venda e com data em "2019-07-30"

    Cenario: 02 - Consultar por periodo com Venda Inexistente
        Dado que foi informado um periodo de "2019-07-01" a "2019-07-02" e paginacao retornando os primeiros "10" registros
        Entao deveria retornar uma lista vazia
