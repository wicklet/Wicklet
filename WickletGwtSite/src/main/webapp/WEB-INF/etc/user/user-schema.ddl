CREATE TABLE IF NOT EXISTS User (
    id IDENTITY NOT NULL,
    name VARCHAR(255) NOT NULL,
    pass VARCHAR(255),
    roles VARCHAR(255),
    lastPasswordChange TIMESTAMP,
    lastLogin TIMESTAMP,
    loginFailureCount TINYINT
);

CREATE TABLE  IF NOT EXISTS Salt (
    id IDENTITY NOT NULL,
    salt VARCHAR(255) NOT NULL
);
