# cashback

Esta aplicação foi desenvolvida para submissão da vaga de Engenheiro BackEnd Sênior da empresa Beblue.

## Link's disponíveis para teste da aplicação

A aplicação foi disponibilizada no link abaixo para testes:

    https://cashback-beblue.herokuapp.com
    
A mesma foi aplicada a uma ferramenta de Integração continua Circle CI através do link abaixo, onde na mesma executa 
passo a passo o processo de integração continua, baixando o código do repositório git, compilando, rodando os testes
(levantando o docker) e por fim concluido com sucesso, disponibilizará no serviço Heroku acima, sua versão "estável".

    https://circleci.com/gh/betobrito/cashback
    
Abaixo está sendo disponibilizado alguns exemplos de uso da aplicação utilizando uma ferramenta para requisições, 
no meu caso foi utilizada a Postman. É importante salientar que para um melhor teste, os link's abaixo foram removidos 
da segurança aplicada pelo spring, ficando assim sem a necessidade da utilização do token JWT.

<b>[GET] Funcionalidade de alimentar base de disco usando base do Spotify</b>

    https://cashback-beblue.herokuapp.com/api/spotify

<b>[GET] Funcionalidade de consultar disco por id</b>

    http://cashback-beblue.herokuapp.com/api/discos/1001

<b>[GET] Funcionalidade de consultar disco por genero paginada e ordenando por descrição</b>

    https://cashback-beblue.herokuapp.com/api/discos/genero?idGenero=4&page=0&size=10&sort=descricao
    
<b>[POST] Funcionalidade de realizar vendas</b>

    https://cashback-beblue.herokuapp.com/api/vendas/
    
Exemplo de corpo para realização da requisição post da funcionalidade acima
    
    {
      "itensVenda": [
            {
                "disco": {
                    "id": 1001
                },
                "quantidade": 2
            },
            {
                "disco": {
                    "id": 1002
                },
                "quantidade": 1
            }
        ]
    }
    
<b>[GET] Funcionalidade de consultar vendas por id</b>

    https://cashback-beblue.herokuapp.com/api/vendas/1401
    
<b>[POST] Funcionalidade de consultar vendas por periodo de forma paginada e ordenada por data de venda</b>

    https://cashback-beblue.herokuapp.com/api/vendas/periodo
    
Exemplo de corpo para realização da requisição post da funcionalidade acima

    {
      "dataInicial": "2019-07-27",
      "dataFinal": "2019-07-31",
      "pagina": 0,
      "tamanho": 10
    }

<b>É importante salientar que o spotify não dispobiliza em suas api's o filtro de albuns por genero, segue link do fórum
o qual a mesma foi solicitada por um usuário e não foi atendida até hoje, vide comentários no link [Issue Spotify]. Logo
a base foi alimentada de forma aleatória, porém seguindo os requisitos de limitar a 50 registros por genero, não houve 
limitação da quantidade de vezes que a mesma pode ser alimentada, gerando assim 200 registros por requisição.</b>  

## Desenvolvimento

Para baixar as dependências do projeto basta executar o comando abaixo:

    mvn clean install -DskipTests=true
    
Para rodar testes ou rodar a aplicação é <b>necessário que tenha levantado a imagem docker do postgres e do registry</b> para 
que o projeto possa se registrar e ficar disponível para uso:

    docker-compose -f src/main/docker/postgresql.yml up -d
   
   e também
   
    docker-compose -f src/main/docker/jhipster-registry.yml up -d
    
Após esse processo, caso queira rodar a api ou os testes é necessário gerar o banco de dados, utilizando o liquibase, 
com o seguinte comando:

    mvn liquibase:update -Dliquibase.dropFirst=true

Após os passos acima a api pode ser startada via linha de comando, executar na raiz do projeto o comando a seguir:

    ./mvnw - em caso de sistema linux
    
   Ou apenas:
    
    mvnw - em caso de sistema windows
    
Para o desenvolvimento foi utilizado framework [Jhipster].

Caso queira gerar um war do projeto basta executar o comando abaixo:

### Empacotando para war

    ./mvnw -Pdev,war clean verify

## Execução dos testes

Para execução dos testes :

    ./mvnw verify
    
## Documentos não funcionais

Foi disponibilizado arquivo html com relatório de execução dos testes na pasta arquivos auxiliares.

[Jhipster]: https://www.jhipster.tech/documentation-archive/v6.1.2/development/
[Issue Spotify]: https://github.com/spotify/web-api/issues/1122
