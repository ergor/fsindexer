BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "user" (
	"id"	bigint NOT NULL,
	"password_hash"	varchar(255),
	"user_name"	varchar(255),
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "unix_user" (
	"id"	bigint NOT NULL,
	"name"	TEXT,
	"uid"	integer NOT NULL,
	"host_id"	bigint,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "unix_group" (
	"id"	bigint NOT NULL,
	"gid"	integer NOT NULL,
	"name"	TEXT,
	"host_id"	bigint,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "sshkey" (
	"id"	bigint NOT NULL,
	"user_id"	bigint,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "indexing_run" (
	"id"	bigint NOT NULL,
	"timestamp"	datetime,
	"host_id"	bigint,
	"parent_run_id"	bigint,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "host_address" (
	"id"	bigint NOT NULL,
	"ip_address"	TEXT,
	"host_id"	bigint,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "host" (
	"id"	bigint NOT NULL,
	"display_name"	TEXT,
	"fqdn"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "hibernate_sequence" (
	"next_val"	bigint
);
CREATE TABLE IF NOT EXISTS "fs_node" (
	"id"	bigint NOT NULL,
	"creation_date"	datetime,
	"gid"	integer NOT NULL,
	"links_to"	TEXT,
	"modified_date"	datetime,
	"name"	TEXT,
	"node_type"	integer,
	"path"	TEXT,
	"permissions"	integer NOT NULL,
	"sha1checksum"	varchar(40),
	"size"	bigint NOT NULL,
	"uid"	integer NOT NULL,
	"host_id"	bigint,
	"indexing_run_id"	bigint,
	"parent_id"	bigint,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "flyway_schema_history" (
	"installed_rank"	INT NOT NULL,
	"version"	VARCHAR(50),
	"description"	VARCHAR(200) NOT NULL,
	"type"	VARCHAR(20) NOT NULL,
	"script"	VARCHAR(1000) NOT NULL,
	"checksum"	INT,
	"installed_by"	VARCHAR(100) NOT NULL,
	"installed_on"	TEXT NOT NULL DEFAULT (strftime('%Y-%m-%d %H:%M:%f','now')),
	"execution_time"	INT NOT NULL,
	"success"	BOOLEAN NOT NULL,
	PRIMARY KEY("installed_rank")
);
INSERT INTO "hibernate_sequence" ("next_val") VALUES (1);
INSERT INTO "hibernate_sequence" ("next_val") VALUES (1);
INSERT INTO "hibernate_sequence" ("next_val") VALUES (1);
INSERT INTO "hibernate_sequence" ("next_val") VALUES (1);
INSERT INTO "hibernate_sequence" ("next_val") VALUES (1);
INSERT INTO "hibernate_sequence" ("next_val") VALUES (1);
INSERT INTO "hibernate_sequence" ("next_val") VALUES (1);
INSERT INTO "hibernate_sequence" ("next_val") VALUES (1);
INSERT INTO "flyway_schema_history" ("installed_rank","version","description","type","script","checksum","installed_by","installed_on","execution_time","success") VALUES (1,'2020.04.16.0','initial','SQL','V2020_04_16_0__initial.sql',0,'','2020-04-16 16:30:50.082',1,1);
CREATE INDEX IF NOT EXISTS "flyway_schema_history_s_idx" ON "flyway_schema_history" (
	"success"
);
COMMIT;
