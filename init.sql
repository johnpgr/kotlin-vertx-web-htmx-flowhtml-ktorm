CREATE TABLE IF NOT EXISTS "user"
(
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_name       VARCHAR NOT NULL UNIQUE,
    email           VARCHAR NOT NULL UNIQUE,
    bio             VARCHAR,
    avatar_url      VARCHAR,
    hashed_password VARCHAR,
    email_verified  BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS "post"
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    author_id   UUID,
    slug        VARCHAR NOT NULL,
    title       VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    body        VARCHAR NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES "user"(id)
);

INSERT INTO "user"(user_name, email) VALUES ('TestUser', 'test_user@email.com');
INSERT INTO "post"(author_id, slug, title, description, body) VALUES (
    (SELECT id FROM "user" WHERE "user".user_name = 'TestUser'),
    'test-post',
    'Test Post',
    'This is a test description for a test post.',
    'Lorem, ipsum dolor sit amet consectetur adipisicing elit. Minima assumenda, temporibus labore laborum rerum illo sint modi ut sequi nostrum accusantium placeat at? A aliquam quis harum beatae voluptatem aliquid.'
)
