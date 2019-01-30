CREATE TABLE IF NOT EXISTS "berat" (
    id          BIGSERIAL       PRIMARY KEY NOT NULL,
    maximum     INT             NOT NULL DEFAULT 0,
    minimum     INT             NOT NULL DEFAULT 0,
    modified    TIMESTAMP       DEFAULT (now() AT TIME ZONE 'utc'),
    ts          TIMESTAMP       DEFAULT (now() AT TIME ZONE 'utc')
);