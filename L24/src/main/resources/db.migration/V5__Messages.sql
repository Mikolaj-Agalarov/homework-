create table messages (
    id serial primary key unique,
    sender_id bigint not null,
    receiver_id bigint not null,
    FOREIGN KEY (sender_id) REFERENCES users (id),
    FOREIGN KEY (receiver_id) REFERENCES users (id)
)