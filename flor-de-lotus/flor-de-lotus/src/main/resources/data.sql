-- ======================================
-- TABELA: ENDERECO
-- ======================================
INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, cep)
VALUES
    ('Rua das Flores', '123', 'Apto 12', 'Centro', 'São Paulo', 'SP', '01000-000'),
    ('Avenida Paulista', '1000', 'Conjunto 1501', 'Bela Vista', 'São Paulo', 'SP', '01310-100'),
    ('Rua Verdejante', '45', NULL, 'Jardins', 'São Paulo', 'SP', '01420-020');

-- ======================================
-- TABELA: USUARIO
-- ======================================
INSERT INTO usuario (nome, email, newsletter, telefone, cpf, senha, nivel_permissao, fk_endereco_id)
VALUES
    ( 'Luíza Oliveira', 'robert.souza@sptech.school', true, '11988887777', '38187355816', '$2a$10$wG.iHUxik1kGmMkce3tLwe0NoAwiu/p2KzW48ioLX8iYLeQ1iFjYC', '1', 1),
    ( 'Carlos Almeida', 'gabriel.iwakura@sptech.school', false, '11999998888', '81663390800', '$2a$10$akQHCSovr2q5kSrrRZktte4LkJYFQCaK86XzRfdwX0Byc6So/HamS', '2', 2),
    ( 'Marina Souza', 'maria.guilherme@sptech.school', true,'11977776666', '50010994807', '$2a$10$72vJ8sN0PpXtRrWZ9R5u5edD9Sg9jJvPpfVg3owcCwZf5X5Kj2a9C', '1', 3);

-- ======================================
-- TABELA: PACIENTE
-- ======================================
INSERT INTO paciente (ativo, fk_usuario_id)
VALUES
    ( 'S', 1),
    ( 'S', 3);

-- ======================================
-- TABELA: FUNCIONARIO
-- ======================================
INSERT INTO funcionario (crp, especialidade, dt_admissao, ativo, fk_usuario_id)
VALUES
    ('123456/BR', 'Psicologia Clínica', '2023-03-15', true, 2);

-- ======================================
-- TABELA: CONSULTA
-- ======================================
INSERT INTO consulta (data_consulta, valor_consulta, especialidade, fk_funcionario_id, fk_paciente_id)
VALUES
    ( '2025-10-25', 250.00, 'Psicologia Clínica', 1, 1),
    ( '2025-10-28', 300.00, 'Psicologia Clínica', 1, 2);
