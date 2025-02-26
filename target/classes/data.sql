INSERT INTO roles (rol,createdat,modifiedat,createdby,modifiedby) VALUES ('Administrador','2000-01-01 01:01:01.123456-06','2000-01-01 01:01:01.123456-06','administrador@domain.com','administrador@domain.com'),('Editor','2000-01-01 01:01:01.123456-06','2000-01-01 01:01:01.123456-06','administrador@domain.com','administrador@domain.com'),('Lector','2000-01-01 01:01:01.123456-06','2000-01-01 01:01:01.123456-06','administrador@domain.com','administrador@domain.com')
ON CONFLICT (rol) DO NOTHING;
INSERT INTO usuarios (nombres, primerapellido, segundoapellido, username, email, password, estatus, estatusbloqueo, createdat, modifiedat,createdby,modifiedby) VALUES ('ADMINISTRADOR', 'ADMINISTRADOR', 'ADMINISTRADOR', 'ADMINISTRADOR', 'administrador@domain.com', '$2a$10$2cuCFicESUZLGlyh/35oxuKz7rt98VAswbQGpqZlRxHnc8BIplS8O' , true, true, '2000-01-01 01:01:01.123456-06','2000-01-01 01:01:01.123456-06','administrador@domain.com','administrador@domain.com'),('SUPERVISOR', 'SUPERVISOR', 'SUPERVISOR', 'SUPERVISOR', 'supervisor@domain.com', '$2a$10$2cuCFicESUZLGlyh/35oxuKz7rt98VAswbQGpqZlRxHnc8BIplS8O' , true, true, '2000-01-01 01:01:01.123456-06','2000-01-01 01:01:01.123456-06','administrador@domain.com','administrador@domain.com'),('USUARIO', 'USUARIO', 'USUARIO', 'USUARIO', 'usuario@domain.com', '$2a$10$2cuCFicESUZLGlyh/35oxuKz7rt98VAswbQGpqZlRxHnc8BIplS8O' , true, true, '2000-01-01 01:01:01.123456-06','2000-01-01 01:01:01.123456-06','administrador@domain.com','administrador@domain.com')
ON CONFLICT (email) DO NOTHING;
INSERT INTO usuariosroles (idusuario, idrol) VALUES (1,1),(1,2),(1,3),(2,2),(2,3),(3,3)
ON CONFLICT (idusuario, idrol) DO NOTHING;