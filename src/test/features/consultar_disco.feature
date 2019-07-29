# language: pt
Funcionalidade: Consultar Disco por ID

    Cenario de Fundo:
        Dado que foram inseridos genero a base de dados
        E que foram inseridos discos

    Cenario: 01 - Consultar Disco Existente
        Dado que o disco informado possua id "10"

#    Cenário: 01 - Consultar BP-e - Dados Gerais
#        Dado que a chave de BP-e "27180261898813000801630010000000171280348997" seja informada
#        E que exista o seguite xml de origem cadastrado
#            | numeroBilhetePassagem | numeroSerie | valorTotal | dataEmissao		 |
#            | 17                    | 1	          | 376.10     | 09/02/2018 06:53:24 |
#        E que exista BP-e de chave "27180261898813000801630010000000171280348997" cadastrado
#        Quando uma requisição ao serviço "consultarPorChaveAcesso" for realizada
#        Então deveria retornar os seguintes dados do BP-e
#            | numeroBilhetePassagem | numeroSerie | valorTotal | dataEmissao		 |
#            | 17                    | 1	          | 376.10     | 09/02/2018 06:53:24 |
#
#    Cenário: 02 - Consultar BP-e - Valores
#        Dado que a chave de BP-e "27180261898813000801630010000000171280348997" seja informada
#        E que exista o seguite xml de origem cadastrado
#            | valorTotal | baseCalculo | valorIcms |
#            | 376.10	 | 364.79      | 62.01     |
#        E que exista BP-e de chave "27180261898813000801630010000000171280348997" cadastrado
#        Quando uma requisição ao serviço "consultarPorChaveAcesso" for realizada
#        Então deveria retornar os seguintes dados do BP-e
#            | valorTotal | baseCalculo | valorIcms |
#            | 376.10	 | 364.79      | 62.01     |
#
#    Cenário: 03 - Consultar BP-e - Dados do Emitente
#        Dado que a chave de BP-e "27180261898813000801630010000000171280348997" seja informada
#        E que exista o seguite xml de origem cadastrado
#            | cnpjEmitente	 | nomeRazaoSocial    | inscricaoEstadual |
#            | 61898813000801 | VIACAO NACIONAL SA | 240763777         |
#        E que exista BP-e de chave "27180261898813000801630010000000171280348997" cadastrado
#        Quando uma requisição ao serviço "consultarPorChaveAcesso" for realizada
#        Então deveria retornar os seguintes dados do BP-e
#            | cnpjEmitente	 | nomeRazaoSocial    | inscricaoEstadual |
#            | 61898813000801 | VIACAO NACIONAL SA | 240763777         |
#
#    Cenário: 04 - Consultar BP-e - Características
#        Dado que a chave de BP-e "27180261898813000801630010000000171280348997" seja informada
#        E que exista o seguite xml de origem cadastrado
#            | tipoBpe | tipoModalTransporte | tipoEmissao | codigoIbgeMunicipioInicioViagem | codigoIbgeMunicipioFimViagem | tipoPresencaComprador |
#            | 0       | 1                   | 1           | 2704302      	                | 3550308                      | 1                     |
#        E que exista BP-e de chave "27180261898813000801630010000000171280348997" cadastrado
#        Quando uma requisição ao serviço "consultarPorChaveAcesso" for realizada
#        Então deveria retornar os seguintes dados do BP-e
#            | tipoBpe | tipoModalTransporte | tipoEmissao | codigoIbgeMunicipioInicioViagem | codigoIbgeMunicipioFimViagem | tipoPresencaComprador |
#            | 0       | 1                   | 1           | 2704302      	                | 3550308                      | 1                     |
#
#    Cenario: 05 - Consultar BP-e - Sem informar a chave de acesso
#        Dado que não seja informada nenhuma chave de BP-e como parâmetro de entrada
#        Quando uma requisição ao serviço "consultarPorChaveAcesso" for realizada
#        Então a API deve informar como resposta a mensagem "Chave de BP-e não informada"
#
#    Cenario: 06 - Consultar BP-e - Com chave não cadastrada
#        Dado que a chave de BP-e "31180316624611009873630010000000011650133380" seja informada
#        Quando uma requisição ao serviço "consultarPorChaveAcesso" for realizada
#        Então a API deve informar como resposta a mensagem "BP-e não encontrado"
#
#    Cenario: 07 - Consultar BP-e - Com chave inválida
#        Dado que uma chave de BP-e seja informada como parâmetro de entrada
#        E que o tamanho da chave informada seja menor que 44 caracteres
#        Quando uma requisição ao serviço "consultarPorChaveAcesso" for realizada
#        Então a API deve informar como resposta a mensagem "Chave de BP-e inválida"
#
#    Cenário: 08 - Consultar BP-e - Chave com digito verificador inválido
#        Dado que a chave de BP-e "27180261898813000801630010000000171280348998" seja informada
#        Quando uma requisição ao serviço "consultarPorChaveAcesso" for realizada
#        Então a API deve informar como resposta a mensagem "Chave com dígito de controle inválido (posição 44)"
#
#    Cenário: 09 - Consultar BP-e - Chave com tipo modelo inválido
#        Dado que a chave de BP-e "27171216624611012076640010000000011280344997" seja informada
#        Quando uma requisição ao serviço "consultarPorChaveAcesso" for realizada
#        Então a API deve informar como resposta a mensagem "Chave com modelo diferente de 63-BP-e (posições 21-22)"
#
#    Cenário: 10 - Consultar BP-e - Chave com mês inválido
#        Dado que a chave de BP-e "27171316624611012076630010000000011280344997" seja informada
#        Quando uma requisição ao serviço "consultarPorChaveAcesso" for realizada
#        Então a API deve informar como resposta a mensagem "Chave com mês inválido (posições 05-06)"
