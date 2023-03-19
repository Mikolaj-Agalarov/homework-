CREATE TABLE friends (
     id SERIAL PRIMARY KEY NOT NULL UNIQUE,
     first_user BIGINT NOT NULL,
     second_user BIGINT NOT NULL,
     CONSTRAINT fk_from_user FOREIGN KEY (first_user) REFERENCES users (id),
     CONSTRAINT fk_to_user FOREIGN KEY (second_user) REFERENCES users (id)
);