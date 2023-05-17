-- liquibase formatted sql

-- changeset liquibase:2
INSERT INTO USER VALUES ('user','$2a$12$PxMkuFnGdELRP55jVoj9Ke5J506Gaa5yZDJv2a32oYnt5jHn2AlcS', 'User', 'Name');
INSERT INTO USER_ROLE VALUES ('user','USER');
INSERT INTO USER VALUES ('admin','$2a$12$vnoZPU2HWNgV/Ujfju4ZguKbf2i2mvIcMiX86.G4.sn9q/Xz5VATy', 'Admin', 'Name');
INSERT INTO USER_ROLE VALUES ('admin','USER');
INSERT INTO USER_ROLE VALUES ('admin','ALLOW_EDIT');