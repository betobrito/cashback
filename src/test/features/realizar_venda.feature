# language: pt
Funcionalidade: Realizar venda

    Cenario de Fundo:
        Dado que foram persistidos quatro generos
        E que foram persistidos cashback semanal para cada genero
        E que foram inseridos discos

    Cenario: 01 - Realizar venda calculando cashback numa terca feira
        Dado que foi solicitada a realizacao de uma venda com dois discos do genero rock de 20,5 e 30,5
        Entao deveria calcular o cashback de quinze porcento e armazenar o valor de "7.65"
