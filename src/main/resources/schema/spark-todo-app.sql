CREATE TABLE "user" (
	user_id SERIAL PRIMARY KEY,
	username VARCHAR (11) NOT NULL UNIQUE,
	password TEXT NOT NULL
);

CREATE TABLE task (
	task_id SERIAL PRIMARY KEY,
	user_id INT NOT NULL,
	task text NOT NULL,
	CONSTRAINT fk_user
		FOREIGN KEY(user_id)
			REFERENCES "user"(user_id)
);