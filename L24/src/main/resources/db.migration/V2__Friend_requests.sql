CREATE TABLE friend_requests (
     id SERIAL PRIMARY KEY NOT NULL UNIQUE,
     from_user_id BIGINT NOT NULL,
     to_user_id BIGINT NOT NULL,
     status CHARACTER VARYING (55) NOT NULL,
     CONSTRAINT fk_from_user FOREIGN KEY (from_user_id) REFERENCES users (id),
     CONSTRAINT fk_to_user FOREIGN KEY (to_user_id) REFERENCES users (id)
);