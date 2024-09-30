CREATE TABLE "user"
(
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_name       VARCHAR NOT NULL UNIQUE,
    email           VARCHAR NOT NULL UNIQUE,
    bio             VARCHAR,
    avatar_url      VARCHAR,
    hashed_password VARCHAR,
    email_verified  BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO "user"(user_name, email) VALUES ('TestUser', 'test_user@email.com');
