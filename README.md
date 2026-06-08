# Sistema de Avaliação de Sintomas e Encaminhamento Médico

API REST desenvolvida em **Java com Spring Boot** para gestão de pacientes, médicos, avaliações de sintomas e encaminhamentos médicos, com autenticação JWT, controlo de acesso por perfis, persistência em MySQL e documentação automática com Swagger/OpenAPI.

---

## Índice

- [Sobre o Projecto](#sobre-o-projecto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Funcionalidades Implementadas](#funcionalidades-implementadas)
- [Autenticação e Perfis de Acesso](#autenticação-e-perfis-de-acesso)
- [Estrutura do Projecto](#estrutura-do-projecto)
- [Modelo de Dados](#modelo-de-dados)
- [Configuração da Base de Dados](#configuração-da-base-de-dados)
- [Como Executar o Projecto](#como-executar-o-projecto)
- [Swagger/OpenAPI](#swaggeropenapi)
- [Credenciais de Teste](#credenciais-de-teste)
- [Endpoints Principais](#endpoints-principais)
- [Exemplos de Requisições](#exemplos-de-requisições)
- [Testes](#testes)
- [Versionamento](#versionamento)
- [Repositório](#repositório)
- [Grupo](#grupo)

---

## Sobre o Projecto

O presente projecto consiste no desenvolvimento de um **Sistema de Avaliação de Sintomas e Encaminhamento Médico**, cujo objectivo é apoiar o processo de registo de pacientes, médicos, avaliações preliminares de sintomas e encaminhamentos para especialidades médicas adequadas.

O sistema permite que pacientes sejam cadastrados, médicos sejam registados com as suas respectivas especialidades e disponibilidade, avaliações de sintomas sejam efectuadas e, quando necessário, seja criado um encaminhamento médico com prioridade, observações e estado de acompanhamento.

A aplicação foi desenvolvida como uma **API REST**, utilizando uma arquitectura organizada em camadas, com separação entre entidades, repositórios, serviços, controladores, DTOs, mapeadores, tratamento de excepções e componentes de segurança.

---

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **Spring Security**
- **JWT**
- **MySQL**
- **Hibernate**
- **Maven**
- **Lombok**
- **Bean Validation**
- **Swagger/OpenAPI**
- **Postman**
- **Git e GitHub**
- **XAMPP/MySQL local**

---

## Funcionalidades Implementadas

### Gestão de Pacientes

O sistema permite:

- Cadastro de pacientes;
- Listagem de pacientes;
- Consulta de paciente por identificador;
- Consulta de paciente por NID;
- Consulta de paciente por nome;
- Actualização dos dados do paciente;
- Remoção de paciente.

Campos principais:

- `id`
- `nome`
- `apelido`
- `NID`
- `género`
- `data de nascimento`
- `grupo sanguíneo`
- `alergias`

---

### Gestão de Médicos

O sistema permite:

- Cadastro de médicos;
- Listagem de médicos;
- Consulta de médico por identificador;
- Consulta de médicos por especialidade;
- Listagem de médicos disponíveis;
- Actualização dos dados do médico;
- Remoção de médico.

Campos principais:

- `id`
- `nome`
- `apelido`
- `especialidade`
- `número da Ordem`
- `celular`
- `disponibilidade`

---

### Gestão de Avaliação de Sintomas

O sistema permite:

- Registo de avaliação de sintomas;
- Listagem de avaliações;
- Consulta de avaliação por identificador;
- Consulta de avaliações por paciente;
- Consulta de avaliações por gravidade;
- Actualização da avaliação;
- Remoção da avaliação.

Campos principais:

- `id`
- `data da avaliação`
- `paciente`
- `descrição dos sintomas`
- `temperatura`
- `gravidade`
- `recomendação`

---

### Gestão de Encaminhamentos Médicos

O sistema permite:

- Criação de encaminhamento médico;
- Listagem de encaminhamentos;
- Consulta de encaminhamento por identificador;
- Consulta de encaminhamentos por médico;
- Consulta de encaminhamentos por especialidade;
- Consulta de encaminhamentos por prioridade;
- Consulta de encaminhamentos por estado;
- Actualização de encaminhamento;
- Actualização do estado do encaminhamento;
- Remoção de encaminhamento.

Campos principais:

- `id`
- `data do encaminhamento`
- `avaliação associada`
- `médico associado`
- `especialidade`
- `prioridade`
- `observações`
- `estado`

Estados disponíveis:

- `PENDENTE`
- `EM_ANALISE`
- `AGENDADO`
- `CONCLUIDO`
- `CANCELADO`

---

## Autenticação e Perfis de Acesso

O sistema utiliza **Spring Security** com autenticação baseada em **JWT**.

Foram definidos três perfis principais:

- `ADMIN`
- `PACIENTE`
- `MEDICO`

Após o login, o sistema retorna um token JWT. Esse token deve ser enviado no cabeçalho das requisições protegidas.

```http
Authorization: Bearer TOKEN_GERADO
```

---

## Estrutura do Projecto

A aplicação foi organizada em pacotes de acordo com a responsabilidade de cada camada:

```text
src/main/java/com/grupoiv/triagemmedica
│
├── config
├── controller
├── dto
├── entity
├── enums
├── exception
├── mapper
├── repository
├── security
└── service
```

| Pacote | Descrição |
|---|---|
| `config` | Configurações gerais da aplicação e dados iniciais |
| `controller` | Controladores REST responsáveis pelos endpoints |
| `dto` | Objectos de entrada e saída da API |
| `entity` | Entidades JPA mapeadas para a base de dados |
| `enums` | Enumerações utilizadas no sistema |
| `exception` | Tratamento de erros e excepções |
| `mapper` | Conversão entre entidades e DTOs |
| `repository` | Interfaces de acesso à base de dados |
| `security` | Configuração de autenticação, JWT e permissões |
| `service` | Camada de regras de negócio |

---

## Modelo de Dados

As principais entidades do sistema são:

- `Paciente`
- `Medico`
- `AvaliacaoSintomas`
- `Encaminhamento`
- `Usuario`
- `Role`

Principais relações:

- Um paciente pode possuir várias avaliações de sintomas;
- Cada avaliação pertence a um paciente;
- Uma avaliação pode originar um encaminhamento;
- Um médico pode estar associado a vários encaminhamentos;
- Um usuário pode possuir um ou mais perfis;
- Um perfil pode estar associado a vários usuários.

---

## Configuração da Base de Dados

A aplicação utiliza **MySQL**. Para ambiente local, foi utilizado o MySQL através do **XAMPP**.

Crie a base de dados:

```sql
CREATE DATABASE triagem_medica_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

Configuração utilizada no ficheiro `application.properties`:

```properties
spring.application.name=triagem-medica

server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/triagem_medica_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.jackson.time-zone=Africa/Maputo
```

> Em ambiente local com XAMPP, normalmente o usuário do MySQL é `root` e a senha fica vazia.

---

## Como Executar o Projecto

### Pré-requisitos

Antes de executar o projecto, é necessário ter instalado:

- Java 21;
- Maven ou Maven Wrapper;
- MySQL/XAMPP;
- Git;
- Postman, opcional para testes.

### Passos para execução

Clone o repositório:

```bash
git clone https://github.com/MarciaPapela/triagem-medica.git
```

Entre na pasta do projecto:

```bash
cd triagem-medica
```

Inicie o MySQL no XAMPP e confirme que a base `triagem_medica_db` existe.

Execute a aplicação:

```bash
mvnw.cmd spring-boot:run
```

Ou, em Linux/macOS:

```bash
./mvnw spring-boot:run
```

A aplicação ficará disponível em:

```text
http://localhost:8080
```

---

## Swagger/OpenAPI

A documentação automática da API pode ser acedida em:

```text
http://localhost:8080/swagger-ui/index.html
```

Através do Swagger é possível visualizar os endpoints, os métodos HTTP, os parâmetros, os corpos das requisições e os modelos de dados utilizados pela API.

---

## Credenciais de Teste

A aplicação possui dados iniciais para facilitar os testes.

### Administrador

```text
Email: marcia@triagem.co.mz
Senha: Admin@123
Perfil: ADMIN
```

### Médico

```text
Email: herminia@triagem.co.mz
Senha: Medico@123
Perfil: MEDICO
```

### Paciente

```text
Email: jacinto@triagem.co.mz
Senha: Paciente@123
Perfil: PACIENTE
```

---

## Endpoints Principais

### Autenticação

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/auth/login` | Realiza login e retorna token JWT |

Exemplo de login:

```json
{
  "email": "marcia@triagem.co.mz",
  "password": "Admin@123"
}
```

---

### Pacientes

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/paciente` | Lista todos os pacientes |
| GET | `/api/paciente/id/{id}` | Busca paciente por ID |
| GET | `/api/paciente/nid/{nid}` | Busca paciente por NID |
| GET | `/api/paciente/nome/{nome}` | Busca paciente por nome |
| POST | `/api/paciente` | Cadastra paciente |
| PUT | `/api/paciente/{id}` | Actualiza paciente |
| DELETE | `/api/paciente/{id}` | Remove paciente |

---

### Médicos

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/medico` | Lista todos os médicos |
| GET | `/api/medico/{id}` | Busca médico por ID |
| GET | `/api/medico/especialidade/{especialidade}` | Busca médicos por especialidade |
| GET | `/api/medico/disponiveis` | Lista médicos disponíveis |
| POST | `/api/medico` | Cadastra médico |
| PUT | `/api/medico/{id}` | Actualiza médico |
| DELETE | `/api/medico/{id}` | Remove médico |

---

### Avaliações de Sintomas

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/avaliacao` | Lista todas as avaliações |
| GET | `/api/avaliacao/{id}` | Busca avaliação por ID |
| GET | `/api/avaliacao/paciente/{pacienteId}` | Busca avaliações por paciente |
| GET | `/api/avaliacao/gravidade/{gravidade}` | Busca avaliações por gravidade |
| POST | `/api/avaliacao` | Regista avaliação |
| PUT | `/api/avaliacao/{id}` | Actualiza avaliação |
| DELETE | `/api/avaliacao/{id}` | Remove avaliação |

---

### Encaminhamentos

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/encaminhamento` | Lista todos os encaminhamentos |
| GET | `/api/encaminhamento/{id}` | Busca encaminhamento por ID |
| GET | `/api/encaminhamento/medico/{medicoId}` | Busca encaminhamentos por médico |
| GET | `/api/encaminhamento/especialidade/{especialidade}` | Busca encaminhamentos por especialidade |
| GET | `/api/encaminhamento/prioridade/{prioridade}` | Busca encaminhamentos por prioridade |
| GET | `/api/encaminhamento/estado/{estado}` | Busca encaminhamentos por estado |
| POST | `/api/encaminhamento` | Cria encaminhamento |
| PUT | `/api/encaminhamento/{id}` | Actualiza encaminhamento |
| PATCH | `/api/encaminhamento/{id}/estado` | Actualiza estado do encaminhamento |
| DELETE | `/api/encaminhamento/{id}` | Remove encaminhamento |

---

### Usuários

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/usuarios` | Lista usuários |
| GET | `/api/usuarios/{id}` | Busca usuário por ID |
| GET | `/api/usuarios/email/{email}` | Busca usuário por email |
| POST | `/api/usuarios` | Cria usuário |
| PATCH | `/api/usuarios/{id}/desativar` | Desactiva usuário |

---

## Exemplos de Requisições

### Criar Paciente

```json
{
  "nome": "Joao",
  "apelido": "Mucavele",
  "nid": "987654321B",
  "genero": "MASCULINO",
  "dataNascimento": "2000-02-15",
  "grupoSanguineo": "A_POSITIVO",
  "alergias": "Penicilina"
}
```

### Criar Médico

```json
{
  "nome": "Antonio",
  "apelido": "Sitoe",
  "especialidade": "Pediatria",
  "numeroOrdem": "OM-002",
  "celular": "840000002",
  "disponibilidade": true
}
```

### Criar Avaliação de Sintomas

```json
{
  "pacienteId": 1,
  "descricaoSintomas": "Febre, dor de cabeça e tosse",
  "temperatura": 38.5,
  "gravidade": "MEDIA",
  "recomendacao": "Consulta médica recomendada"
}
```

### Criar Encaminhamento

```json
{
  "avaliacaoId": 1,
  "medicoId": 1,
  "especialidade": "Clínica Geral",
  "prioridade": "MEDIA",
  "observacoes": "Encaminhamento inicial para consulta geral"
}
```

### Actualizar Estado do Encaminhamento

```json
{
  "estado": "AGENDADO",
  "observacoes": "Consulta agendada com o médico responsável"
}
```

---

## Testes

Os testes da API foram realizados com recurso ao **Swagger/OpenAPI** e ao **Postman**.

Foram testados os principais fluxos do sistema:

- Login e geração de token JWT;
- Cadastro de paciente;
- Cadastro de médico;
- Registo de avaliação de sintomas;
- Criação de encaminhamento;
- Actualização do estado do encaminhamento;
- Consultas por filtros;
- Actualização de dados;
- Remoção de registos;
- Validação de permissões por perfil.

---

## Versionamento

O projecto foi versionado com **Git** e armazenado no **GitHub**.

Durante o desenvolvimento foram realizados commits incrementais e descritivos, demonstrando a evolução do sistema por fases, incluindo:

- Configuração inicial do projecto;
- Organização dos pacotes;
- Criação das entidades JPA;
- Criação dos repositórios;
- Criação dos DTOs;
- Implementação dos serviços;
- Implementação dos controladores REST;
- Configuração de segurança;
- Implementação de autenticação JWT;
- Criação de dados iniciais;
- Testes e documentação.

---

## Repositório

Link do repositório público:

```text
https://github.com/MarciaPapela/triagem-medica/
```

---

## Grupo

Projecto desenvolvido para fins académicos.

Tema: **Sistema de Avaliação de Sintomas e Encaminhamento Médico**

Grupo: **Grupo IV**

### Integrantes

- Fernando Mataruca
- Jacinto Lazaro
- Herminia Hernesto
- Marcia Vicente Papela

### Supervisor

- Eng. Momade Abdul

---

## Observações

Este projecto foi desenvolvido para fins académicos e demonstra a implementação de uma API REST com Spring Boot, persistência em MySQL, autenticação com JWT, controlo de acesso por perfis e documentação automática com Swagger/OpenAPI.
