CREATE TABLE customer (
	id       SERIAL      NOT NULL PRIMARY KEY,
	name     VARCHAR(30) NOT NULL,
	surname  VARCHAR(30) NOT NULL,
	birth    DATE        NOT NULL,
	data     VARCHAR,
	role     INT,
	email    VARCHAR(90) NOT NULL UNIQUE,
	password VARCHAR(12) NOT NULL,
	salt     VARCHAR(12) NOT NULL
);

CREATE TABLE picture (
	id         SERIAL      NOT NULL PRIMARY KEY,
	user_id    INT         NOT NULL,
	mime_type  VARCHAR(30) NOT NULL,
	image_file BYTEA       NOT NULL,
	FOREIGN KEY (user_id) REFERENCES customer (id) ON DELETE CASCADE
);