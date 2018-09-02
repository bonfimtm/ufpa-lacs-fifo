# UFPA/LACS FIFO

Solução computacional de apoio à pesquisa "Planejamento Cultural: Uma Proposta de Intervenção Cultural na Fila de um Restaurante Universitário"

## Objetivo

Prover uma solução computacional de apoio à pesquisa "Planejamento Cultural: Uma Proposta de Intervenção Cultural na Fila de um Restaurante Universitário" do Laboratório de Comportamento Social e Seleção Cultural (LACS) da Universidade Federal do Pará (UFPA) envolvendo o Restaurante Universitário (RU) da Universidade Federal do Pará, unidade do Campus Básico.

## Metas

- Mensurar o tempo entre chegada dos clientes a fila (TEC);
- Calcular o tempo médio de espera dos usuários que respeitam a ordem de chegada à fila;
- Detectar e contar os usuários que não respeitam a ordem de chegada à fila;
- Exibir tempo médio de espera dos usuários que respeitam a ordem de chegada a fila;
- Exibir o número de produtos decorrentes de respostas auto controladas dos usuários da fila;
- Gerar relatórios detalhados contendo os dados computados.

## Solução

As páginas são acessadas por dois pesquisadores que devem registrar a chegada e saída dos usuários na fila do RU através de número do cartão de registro do próprio RU. A figura a seguir mostra uma visão geral do sistema durante a execução do experimento:

<img src="/docs/system-chart.png"
     alt="Visão geral do sistema durante a execução do experimento"
     height="400" />

- O Pesquisador/Monitor 1, posicionado na cauda da fila, é responsável por registrar a entrada dos usuários na fila. Este acessará o sistema através da internet utilizando um computador portátil, tablet, ou smartphone;

- O Pesquisador/Monitor 2, posicionado na cabeça da fila, é responsável por registrar a saída dos usuários na fila. Este acessará o sistema através da internet utilizando um computador portátil, tablet, ou smartphone;

Usuários do RU podem visualizar o tempo médio de espera na fila e a número de produtos decorrentes de respostas auto controladas dos usuários na fila em tempo real através de um monitor de vídeo ligado a um computador portátil que acessa o sistema via internet.

A posteriori os pesquisadores envolvidos no estudo poderão utilizar o sistema para gerar um relatório detalhado contendo o horário, com precisão de segundos, de admissão do usuário na fila, o horário, também com precisão de segundos, da saída do usuário da fila, se este usuário respeitou a ordem de chegada a fila, e o tempo de espera deste usuário em minutos. Estes dados são categorizados de acordo a data da medição.
  
## Características Técnicas

O sistema tem uma arquitetura cliente/servidor em que o servidor executa uma aplicação web que utiliza um banco de dados relacional para armazenamento dos dados. Os pesquisadores podem acessar ao sistema através de um endereço eletrônico via internet através de um navegador web.
