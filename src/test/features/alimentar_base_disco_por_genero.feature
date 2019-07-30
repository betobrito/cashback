# language: pt
Funcionalidade: Alimentar Base de Discos por Genero

    Cenario de Fundo:
        Dado que foram persistidos quatro generos
        E que foram persistidos cashback semanal para cada genero

    Cenario: 01 - Alimentar base discos com cinquenta discos por genero
        Dado que foi solicitado alimentar base de discos
        Entao ao verificar base de discos deveria haver duzentos registros
