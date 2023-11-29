INSERT INTO roles (rol,createdat,modifiedat) VALUES ('Administrador','1900-01-01 00:00:00.123','1900-01-01 00:00:00.123'),('Editor','1900-01-01 00:00:00.123','1900-01-01 00:00:00.123'),('Lector','1900-01-01 00:00:00.123','1900-01-01 00:00:00.123')
ON CONFLICT (rol) DO NOTHING;
INSERT INTO usuarios (nombres, email, password, estatus, estatusbloqueo, createdat, modifiedat) VALUES ('ADMINISTRADOR', 'administrador@domain.com', '$2a$10$2cuCFicESUZLGlyh/35oxuKz7rt98VAswbQGpqZlRxHnc8BIplS8O' , true, true, '1900-01-01 00:00:00.123','1900-01-01 00:00:00.123')
ON CONFLICT (email) DO NOTHING;
INSERT INTO usuariosroles (idusuario, idrol) VALUES (1,1),(1,2),(1,3)
ON CONFLICT (idusuario, idrol) DO NOTHING;