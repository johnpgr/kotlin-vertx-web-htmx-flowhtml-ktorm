CREATE TABLE IF NOT EXISTS "user"
(
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name            VARCHAR(32) NOT NULL UNIQUE,
    bio             VARCHAR(2000),
    hashed_password VARCHAR(100)
);
CREATE TABLE IF NOT EXISTS "session"
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    token TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "post"
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    author_id   UUID NOT NULL,
    slug        VARCHAR(255) NOT NULL,
    title       VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    body        TEXT NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES "user"(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "comment"
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    post_id     UUID NOT NULL,
    author_id   UUID NOT NULL,
    body        VARCHAR(2000) NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES "post"(id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES "user"(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "favorite"
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id     UUID NOT NULL,
    post_id     UUID NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES "post"(id) ON DELETE CASCADE,
    UNIQUE (user_id, post_id)
);

CREATE TABLE IF NOT EXISTS "follow"
(
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    follower_id     UUID NOT NULL,
    followed_id     UUID NOT NULL,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (follower_id) REFERENCES "user"(id) ON DELETE CASCADE,
    FOREIGN KEY (followed_id) REFERENCES "user"(id) ON DELETE CASCADE,
    UNIQUE (follower_id, followed_id)
);

ALTER TABLE "follow" ADD CONSTRAINT check_self_follow CHECK (follower_id != followed_id);

CREATE INDEX idx_user_username ON "user" (user_name);

CREATE INDEX idx_post_author_id ON "post" (author_id);
CREATE INDEX idx_post_created_at ON "post" (created_at);

CREATE INDEX idx_comment_post_id ON "comment" (post_id);
CREATE INDEX idx_comment_author_id ON "comment" (author_id);
CREATE INDEX idx_comment_created_at ON "comment" (created_at);

CREATE INDEX idx_favorite_user_id ON "favorite" (user_id);
CREATE INDEX idx_favorite_post_id ON "favorite" (post_id);

CREATE INDEX idx_follow_follower_id ON "follow" (follower_id);
CREATE INDEX idx_follow_followed_id ON "follow" (followed_id);

CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
   NEW.updated_at = CURRENT_TIMESTAMP;
   RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_user_timestamp
BEFORE UPDATE ON "user"
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER update_post_timestamp
BEFORE UPDATE ON "post"
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER update_comment_timestamp
BEFORE UPDATE ON "comment"
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();
