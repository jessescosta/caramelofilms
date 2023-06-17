    create table filmes(
    id bigint not null auto_increment,
    nome varchar(100) not null,
    duracao_em_minutos int not null,
    ano_lancamento int not null,
    genero varchar(100),
    poster varchar(800),
    primary key(id)
    );