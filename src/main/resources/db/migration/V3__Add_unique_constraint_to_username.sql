-- V3__Add_unique_constraint_to_username.sql
ALTER TABLE user_login ADD CONSTRAINT unique_username UNIQUE (username);
