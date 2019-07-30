# language: pt
Funcionalidade: Consultar Disco por Genero

    Cenario de Fundo:
        Dado que foram inseridos generos a base de dados
        E que foram inseridos discos

    Cenario: 01 - Consultar Discos Por Genero com discos existentes
        Dado que o genero informado possua id "4" deveria retornar os "10" primeiros registros ordenados por "descricao"
        Entao deveria retornar dois discos:
            |  AC/DC 101                        |
            |  Enema Of The State - Blink 182   |

    Cenario: 02 - Consultar Disco Por Genero com discos inexistentes
        Dado que o genero informad o possua id "5" deveria retornar os "10" primeiros registros ordenados por "descricao"
        Entao deveria retornar uma lista vazia
