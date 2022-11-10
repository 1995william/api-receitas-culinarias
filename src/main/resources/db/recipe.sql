
CREATE TABLE IF NOT EXISTS recipe (
id          serial not null,
name        varchar(255) not null,
section     jsonb,
primary key (id)
);