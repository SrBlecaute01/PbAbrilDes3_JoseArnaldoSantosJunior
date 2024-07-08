# Desafio 3 - Spring Boot - Back-End Journey

O terceiro desafio consiste em três micro serviços para simular pagamentos onde estão localizados na pasta de [ms-calculate](https://github.com/SrBlecaute01/PbAbrilDes3_JoseArnaldoSantosJunior/tree/main/ms-calculate), [ms-customer](https://github.com/SrBlecaute01/PbAbrilDes3_JoseArnaldoSantosJunior/tree/main/ms-customer) e [ms-payments](https://github.com/SrBlecaute01/PbAbrilDes3_JoseArnaldoSantosJunior/tree/main/ms-payments) responsáveis respectivamente por calcular e criar regras de cálculo, gerenciar os clientes e realizar os pagamentos. Além disso, uma pasta para gerar a análise de código com JaCoCo na pasta [code-coverage](https://github.com/SrBlecaute01/PbAbrilDes3_JoseArnaldoSantosJunior/tree/main/code-coverage)

## Sumário

- [Instalação do projeto](#instalando-o-projeto)
- [Configuração do RabbitMQ](#configuração-do-rabbitmq)
  - [Criando a exchange](#criando-a-exchange)
  - [Criando a queue](#criando-a-queue)
  - [Criando a routing key](#criando-a-routing-key)
- [Cobertura de código](#cobertura-de-código)
- [Configurando o S3](#configurando-o-s3)
- [Serviço de calculo](#serviço-de-cálculo)
- [Serviço de clientes](#serviço-de-clientes)
- [Serviço de pagamentos](#serviço-de-pagamentos)

## Instalando o projeto

O projeto pode ser iniciado a partir do [docker-compose](https://github.com/SrBlecaute01/PbAbrilDes3_JoseArnaldoSantosJunior/blob/main/docker-compose.yml) localizado na pasta principal do projeto. Sendo necessário configurar apenas o banco de dados e as configurações da AWS. Outras configurações podem ser aplicadas utilizando as variáveis de ambiente, entretanto caso não seja definida irá utilizar as configurações padrões.

Para executar o docker-compose pode-se utilizar o seguinte comando passando também as variáveis de ambiente necessárias ou executar diretamente dentro da ide se ela oferecer suporte.

```cmd
docker-compose run -e DB_NAME=compass -e DB_HOST=localhost
```
As variáveis de ambiente que devem ser colocadas ao executar o docker-compose são as seguintes:

| Variável de ambiente | Descrição  | Padrão |
|--|--| -- |
| DB_NAME | nome da database | compass |
| DB_HOST| host da database | lolcahost |
| DB_USER| usuário da database | root |
| DB_PASSWORD | senha do usuário | admin |
| AWS_ACCESS_KEY | chave de acesso da AWS
| AWS_SECRET_KEY | chave de acesso secreta da AWS |
| AWS_ACCESS_TOKEN | token de acesso da aws
| AWS_BUCKET_NAME | nome da bucket do S3
| AWS_BUCKET_FOLDER | pasta da bucket no S3
| AWS_REGION | região do S3

Essas são as principais variáveis do projeto, entretanto outras podem ser utilizadas como as seguintes:

| Variável de ambiente | Descrição  | Padrão |
|--|--|--|
| RABBIT_USER | usuário do rabbitmq | root
| RABBIT_PASSWORD | senha do usuário no rabbitmq | admin
| RABBIT_HOST | host do rabbitmq 
| RABBIT_QUEUE | nome da queue no rabbitmq | points-request-queue
| RABBIT_EXCHANGE | nome do exchange no rabbitmq | points-request-exchange
| RABBIT_ROUTING_KEY | chave de rota no rabbitmq | points-request-key
| CALCULATE_HOST | host do serviço de calculo
| CUSTOMER_HOST | host do serviço de clientes

## Configuração do RabbitMQ

o docker-compose irá criar um novo RabbitMQ ao ser  executado com o usuário de root e a senha de admin caso as variáveis de ambiente definidas anteriormente não forem alteradas.

Desse modo, ainda é necessário criar o exchange, a queue e a routing-key para que o projeto faça a comunicação.

### Criando a exchange

Para criar a exchange vá para o painel do RabbitMQ na área de exchange e crie uma nova enxchange denominada: `points-request-exchange` assim como na imagem:

![exchange](https://i.imgur.com/ez2nDuc.png)

### Criando a queue

Para criar a fila, é necessário ir no painel de queues do rabbitmq e criar uma nova queue denominada de `points-request-queue` assim como na imagem:

![Imgur](https://imgur.com/S4CPtA1.png)

### Criando a routing key

Para criar a routing-key é necessário voltar a área de exchange e selecionar a exchange criada anteriormente. Na área de bindings da exchange você pode criar a routing-key e adicioná-la também a queue já criada assim como na imagem:

![Imgur](https://imgur.com/OLd1KDj.png)

## Configurando o S3

Para configurar o S3 é necessário criar uma política na bucket para permitir que a pasta em que as imagens dos clientes serão enviadas seja de acesso público. Desse modo, a seguinte política dentro da bucket foi utilizada para a pasta `profile` , sendo essa a pasta padrão de envio.
```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "Statement1",
            "Effect": "Allow",
            "Principal": "*",
            "Action": "s3:GetObject",
            "Resource": "arn:aws:s3:::arnaldos3/profile/*"
        }
    ]
}
```

## Cobertura de código

A cobertura de código foi feita utilizando o JaCoCo e pode ser vista a partir 
da pasta code-coverage ao gerar o relatório de cobertura com o comando:

```maven
mvn clean verify
```

Ao finalizar a execução o relatório estará disponível na pasta target/site

![Imgur](https://imgur.com/hfB7Z0i.png)

## Serviço de cálculo

O serviço de cálculo é responsável por calcular a quantidade de pontos gerada por cada pagamento além de aplicar regras para a categoria de cada pagamento. Está localizado na porta 8080 caso não seja definida e seus endpoints são os seguintes:

| Endpoint | Método| Descrição  |
|--|--|--|
| v1/calculate | POST | calcula a quantidade de pontos
| v1/rules | POST | cria uma nova regra de cálculo
| v1/rules | GET | obtém todas as regras
| v1/rules/{id} | GET | obtém uma regra a partir do ID
| v1/rules/{id} | UPDATE | atualiza uma regra
| v1/rules/{id} | DELETE | deleta uma regra

**cUrl para calcular um valor**
```curl
curl --request POST \
  --url http://localhost:8080/v1/calculate \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/9.3.1' \
  --data '{
	"categoryId": "1",
	"value": 100
}'
```
**cUrl para criar uma regra**
```curl
curl --request POST \
  --url http://localhost:8080/v1/rules \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/9.3.1' \
  --data '{
	"category": "electronics",
	"parity": 10
}'
```
**cUrl para listar as regras**
```curl
curl --request GET \
  --url http://localhost:8080/v1/rules \
  --header 'User-Agent: insomnia/9.3.1'
```
**cUrl para buscar uma regra pelo id**
```curl
curl --request GET \
  --url http://localhost:8080/v1/rules/1 \
  --header 'User-Agent: insomnia/9.3.1'
```
**cUrl para atualizar uma regra**
```curl
curl --request PUT \
  --url http://localhost:8080/v1/rules/1 \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/9.3.1' \
  --data '{
	"category": "eletronics",
	"parity": 50
}'
```
**cUrl para deletar uma regra**
```curl
curl --request DELETE \
  --url http://localhost:8080/v1/rules/2 \
  --header 'User-Agent: insomnia/9.3.1'
```

### Cobertura de código do serviço
![Imgur](https://imgur.com/Qio77Cs.png)


## Serviço de clientes

O serviço de clientes é responsável por criar e gerenciar os clientes. Está localizado na porta 8081 caso não seja definida e seus endpoints são os seguintes:

| Endpoint | Método| Descrição  |
|--|--|--|
| v1/customers| POST | cria um novo cliente
| v1/customers/{id} | GET | obtém um cliente pelo id
| v1/customers/{id} | UPDATE | atualiza um cliente
| v1/customers/{id} | DELETE | deleta um cliente

**cUrl para criar um cliente**
```curl
curl --request POST \
  --url http://localhost:8081/v1/customers \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/9.2.0' \
  --data '{
	"cpf": "218.618.579-26",
	"name": "Test",
	"gender": "masculine",
	"email": "test@email.com",
	"birthDate": "26/10/2002",
	"photo": "imageInBase64String"
}'
```
**cUrl para buscar um cliente**

```curl
curl --request GET \
  --url http://localhost:8081/v1/customers/1 \
  --header 'User-Agent: insomnia/9.2.0'
```
**cUrl para atualizar um cliente**
```curl
curl --request PUT \
  --url http://localhost:8081/v1/customers/1 \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/9.2.0' \
  --data '{
	"cpf": "218.618.579-26",
	"name": "Test",
	"gender": "masculine",
	"email": "test2@email.com",
	"birthDate": "10/10/2002",
	"photo": "imageInBase64String"
}'
```
**cUrl para deletar um cliente**
```curl
curl --request DELETE \
  --url http://localhost:8081/v1/customers/1 \
  --header 'User-Agent: insomnia/9.2.0'
```

### Cobertura de código do serviço
![Imgur](https://imgur.com/UXJ9RNa.png)


## Serviço de pagamentos

O serviço de pagamentos é responsável por criar e gerenciar os pagamentos dos clientes. Está localizado na porta 8082 caso não seja definida e seus endpoints são os seguintes:

| Endpoint | Método| Descrição  |
|--|--|--|
| v1/payments| POST | cria um novo pagamento
| v1/payments/{id} | GET | obtém um pagamento pelo id
| v1/payments/user/{id} | GET | obtém todos os pagamentos de um cliente

**cUrl para criar um novo pagamento**
```curl
curl --request POST \
  --url http://localhost:8082/v1/payments \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/9.3.1' \
  --data '{
	"categoryId": "1",
	"customerId": "1",
	"value": 100
}'
```
**cUrl para buscar um pagamento pelo id**
```curl
curl --request GET \
  --url http://localhost:8082/v1/payments/b2cd6b56-f5d0-47c7-85ae-7b2c24acda87 \
  --header 'User-Agent: insomnia/9.3.1'
```
**cUrl para buscar todos os pagamento de um cliente**
```curl
curl --request GET \
  --url http://localhost:8082/v1/payments/user/1 \
  --header 'User-Agent: insomnia/9.3.1'
```
### Cobertura de código do serviço
![Imgur](https://imgur.com/eYIkHO3.png)
