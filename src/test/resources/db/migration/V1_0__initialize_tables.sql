CREATE TABLE t_mt_user (
   id UUID NOT NULL,
   username VARCHAR(50) NOT NULL,
   password VARCHAR(100) NOT NULL,
   first_name VARCHAR(50) NOT NULL,
   last_name VARCHAR(50) NOT NULL,
   role VARCHAR(50) NOT NULL
);

CREATE TABLE t_mt_task (
   id UUID NOT NULL,
   user_id UUID NOT NULL,
   description varchar(200) NOT NULL,
   status VARCHAR(50) NOT NULL,
   create_dt TIMESTAMP NOT NULL,
   update_dt TIMESTAMP
);

CREATE INDEX idx_mt_user_id ON t_mt_user(id);
CREATE INDEX idx_mt_task_id ON t_mt_task(id);

ALTER TABLE t_mt_user ALTER COLUMN id SET DEFAULT random_uuid();

ALTER TABLE t_mt_task ALTER COLUMN id SET DEFAULT random_uuid();
