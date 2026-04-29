-- ============================================================
-- DASHBOARD PACIENTES
-- ============================================================

CREATE OR REPLACE VIEW total_pacientes AS
SELECT COUNT(*) AS total_pacientes_ativos
FROM paciente
WHERE ativo = true;

CREATE OR REPLACE VIEW pacientes_ativos_no_ano AS
SELECT
    COUNT(DISTINCT p.id_paciente) AS qtd,
    YEAR(c.data) AS ano_consulta
FROM paciente p
    JOIN consulta c ON p.id_paciente = c.fk_paciente
WHERE p.ativo = true
GROUP BY ano_consulta;

CREATE OR REPLACE VIEW taxa_retencao AS
    WITH limites AS (
        SELECT
            DATE_SUB(CURDATE(), INTERVAL 12 MONTH) AS dt_inicio,
            CURDATE() AS dt_fim
    ),
    clientes_inicio AS (
        SELECT COUNT(*) AS E
        FROM paciente p
        JOIN usuario u ON u.id_usuario = p.fk_usuario
        CROSS JOIN limites l
        WHERE p.ativo = TRUE AND u.data_cadastro < l.dt_inicio
    ),
    clientes_final AS (
        SELECT COUNT(*) AS N
        FROM paciente p
        JOIN usuario u ON u.id_usuario = p.fk_usuario
        CROSS JOIN limites l
        WHERE p.ativo = TRUE AND u.data_cadastro <= l.dt_fim
    ),
    novos_clientes AS (
        SELECT COUNT(*) AS S
        FROM paciente p
        JOIN usuario u ON u.id_usuario = p.fk_usuario
        CROSS JOIN limites l
        WHERE p.ativo = TRUE
          AND u.data_cadastro >= l.dt_inicio
          AND u.data_cadastro <= l.dt_fim
    )
SELECT
    CASE
        WHEN (SELECT E FROM clientes_inicio) = 0 THEN NULL
        ELSE ROUND(
                ((SELECT N FROM clientes_final) - (SELECT S FROM novos_clientes))
                    / (SELECT E FROM clientes_inicio) * 100, 2)
        END AS taxa_percentual
FROM limites;

CREATE OR REPLACE VIEW taxa_retencao_mes AS
SELECT
    mes,
    ROUND(
            SUM(CASE WHEN total_consultas < 2 THEN 1 ELSE 0 END) / COUNT(*) * 100, 2
    ) AS percentual_retencao
FROM (
         SELECT
             fk_paciente,
             DATE_FORMAT(data, '%Y-%m') AS mes,
             COUNT(*) AS total_consultas
         FROM consulta
         WHERE data >= DATE_SUB(CURDATE(), INTERVAL 12 MONTH)
         GROUP BY fk_paciente, DATE_FORMAT(data, '%Y-%m')
     ) AS t
GROUP BY mes
ORDER BY mes;

CREATE OR REPLACE VIEW novos_pacientes_por_mes AS
SELECT
    MONTH(mes_primeira_consulta) AS mes,
    COUNT(*) AS novos_pacientes
FROM (
    SELECT fk_paciente AS id_paciente, MIN(data) AS mes_primeira_consulta
    FROM consulta
    GROUP BY fk_paciente
    ) AS primeira
    JOIN paciente p ON p.id_paciente = primeira.id_paciente
WHERE p.ativo = true
  AND YEAR(mes_primeira_consulta) = YEAR(CURDATE())
GROUP BY mes
ORDER BY mes;

CREATE OR REPLACE VIEW top_5_pacientes AS
SELECT
    u.nome AS nome_paciente,
    COUNT(c.fk_paciente) AS consultas,
    SUM(c.valor) AS valor,
    p.ativo AS ativo
FROM consulta c
         JOIN paciente p ON c.fk_paciente = p.id_paciente
         JOIN usuario u ON u.id_usuario = p.fk_usuario
GROUP BY c.fk_paciente, u.nome, p.ativo
ORDER BY consultas DESC
    LIMIT 5;

-- ============================================================
-- DASHBOARD FINANCEIRO
-- ============================================================

CREATE OR REPLACE VIEW kpi_faturamento_mes AS
SELECT SUM(valor) AS faturamento
FROM consulta
WHERE MONTH(data) = MONTH(CURDATE())
  AND YEAR(data) = YEAR(CURDATE());

CREATE OR REPLACE VIEW kpi_faturamento_melhor_mes AS
    WITH faturamento_meses AS (
        SELECT
            MONTH(data) AS mes,
            YEAR(data) AS ano,
            SUM(valor) AS total_mes
        FROM consulta
        GROUP BY MONTH(data), YEAR(data)
    )
SELECT mes, ano, total_mes
FROM faturamento_meses
WHERE total_mes = (SELECT MAX(total_mes) FROM faturamento_meses);

CREATE OR REPLACE VIEW kpi_faturamento_ano AS
SELECT SUM(valor) AS faturamento
FROM consulta
WHERE YEAR(data) = YEAR(CURDATE());

CREATE OR REPLACE VIEW grafico_faturamento_mensal AS
SELECT MONTH(data) AS mes, SUM(valor) AS valor
FROM consulta
WHERE YEAR(data) = YEAR(CURDATE())
GROUP BY MONTH(data)
ORDER BY MONTH(data);

CREATE OR REPLACE VIEW grafico_consultas_mes AS
SELECT MONTH(data) AS mes, COUNT(*) AS qtd_consultas
FROM consulta
WHERE YEAR(data) = YEAR(CURDATE())
GROUP BY MONTH(data)
ORDER BY MONTH(data);

CREATE OR REPLACE VIEW grafico_comparacao_custo_receita AS
    WITH todos_custos_consultas AS (
        SELECT
            m.preco AS valor_teste,
            c.valor AS valor_consulta,
            c.data
        FROM (
            SELECT m.fk_consulta, t.preco
            FROM movimentacao_estoque m
            JOIN teste t ON t.id_teste = m.fk_teste
        ) m
        RIGHT JOIN consulta c ON m.fk_consulta = c.id_consulta
        WHERE YEAR(c.data) = YEAR(CURDATE())
    )
SELECT
    SUM(valor_teste) AS valor_teste,
    SUM(valor_consulta) AS valor_consulta,
    MONTH(data) AS mes
FROM todos_custos_consultas
GROUP BY MONTH(data);

CREATE OR REPLACE VIEW resumo_financeiro_mes AS
    WITH TodasReceitas AS (
        SELECT data AS data_faturamento, valor AS valor_receita
        FROM consulta
        UNION ALL
        SELECT m.data_movimentacao AS data_faturamento, (m.qtd * t.preco) AS valor_receita
        FROM movimentacao_estoque m
        JOIN teste t ON m.fk_teste = t.id_teste
    ),
    FaturamentoAgrupado AS (
        SELECT
            COALESCE(SUM(CASE
                WHEN MONTH(data_faturamento) = MONTH(CURDATE())
                 AND YEAR(data_faturamento) = YEAR(CURDATE())
                THEN valor_receita ELSE 0 END), 0) AS faturamento_atual,
            COALESCE(SUM(CASE
                WHEN MONTH(data_faturamento) = MONTH(DATE_SUB(CURDATE(), INTERVAL 1 MONTH))
                 AND YEAR(data_faturamento) = YEAR(DATE_SUB(CURDATE(), INTERVAL 1 MONTH))
                THEN valor_receita ELSE 0 END), 0) AS faturamento_anterior
        FROM TodasReceitas
    )
SELECT
    faturamento_atual,
    faturamento_anterior,
    ROUND(
            CASE
                WHEN faturamento_anterior = 0 AND faturamento_atual > 0 THEN 100.00
                WHEN faturamento_anterior = 0 AND faturamento_atual = 0 THEN 0.00
                ELSE ((faturamento_atual - faturamento_anterior) / faturamento_anterior) * 100
                END, 2) AS crescimento_percentual
FROM FaturamentoAgrupado;

-- ============================================================
-- DASHBOARD AGENDAMENTOS
-- ============================================================

CREATE OR REPLACE VIEW kpi_agendamentos_semana AS
SELECT COUNT(*) AS qtd
FROM consulta
WHERE status = 'PENDENTE'
  AND data >= CURDATE() - INTERVAL 7 DAY;

CREATE OR REPLACE VIEW kpi_taxa_comparecimento AS
    WITH consultas_confirmadas AS (
        SELECT COUNT(*) AS qtd_confirmadas
        FROM consulta
        WHERE status = 'REALIZADA'
          AND MONTH(data) = MONTH(CURDATE())
          AND YEAR(data) = YEAR(CURDATE())
    ),
    consultas_canceladas AS (
        SELECT COUNT(*) AS qtd_canceladas
        FROM consulta
        WHERE status = 'CANCELADA'
          AND MONTH(data) = MONTH(CURDATE())
          AND YEAR(data) = YEAR(CURDATE())
    )
SELECT
    CONCAT(
            CAST(ROUND(
                    c.qtd_confirmadas * 100.0 / (c.qtd_confirmadas + ca.qtd_canceladas), 2
                 ) AS CHAR(10)), '%'
    ) AS percentual
FROM consultas_confirmadas c
         CROSS JOIN consultas_canceladas ca;

CREATE OR REPLACE VIEW kpi_consultas_realizadas AS
SELECT COUNT(*) AS qtd
FROM consulta
WHERE status = 'REALIZADA'
  AND data >= CURDATE() - INTERVAL 7 DAY;

CREATE OR REPLACE VIEW kpi_cancelamentos AS
    WITH canceladas AS (
        SELECT COUNT(*) AS qtd_canceladas
        FROM consulta
        WHERE status = 'CANCELADA'
          AND data >= CURDATE() - INTERVAL 7 DAY
    ),
    todas AS (
        SELECT COUNT(*) AS qtd_todas
        FROM consulta
        WHERE data >= CURDATE() - INTERVAL 7 DAY
    )
SELECT
    t.qtd_todas,
    c.qtd_canceladas,
    IFNULL(CONCAT(
                   CAST(ROUND(c.qtd_canceladas * 100.0 / t.qtd_todas, 2) AS CHAR(10)), '%'
           ), '0%') AS percentual
FROM todas t
         CROSS JOIN canceladas c;

CREATE OR REPLACE VIEW grafico_desempenho_semanal AS
SELECT status, COUNT(*) AS quantidade
FROM consulta
WHERE data >= CURDATE() - INTERVAL 7 DAY
  AND MONTH(data) = MONTH(CURDATE())
  AND YEAR(data) = YEAR(CURDATE())
GROUP BY status;

CREATE OR REPLACE VIEW grafico_distribuicao_horario AS
    WITH todos_periodos AS (
        SELECT
            CASE
                WHEN TIME(data) BETWEEN '08:00:00' AND '09:59:59' THEN '08:00 às 10:00'
                WHEN TIME(data) BETWEEN '10:00:00' AND '11:59:59' THEN '10:00 às 12:00'
                WHEN TIME(data) BETWEEN '12:00:00' AND '13:59:59' THEN '12:00 às 14:00'
                WHEN TIME(data) BETWEEN '14:00:00' AND '15:59:59' THEN '14:00 às 16:00'
                WHEN TIME(data) BETWEEN '16:00:00' AND '17:59:59' THEN '16:00 às 18:00'
                ELSE 'Fora do horário comercial'
            END AS periodo
        FROM consulta
    )
SELECT COUNT(periodo) AS quantidade, periodo
FROM todos_periodos
GROUP BY periodo
ORDER BY periodo;

-- ============================================================
-- DASHBOARD AVALIAÇÕES
-- ============================================================

CREATE OR REPLACE VIEW grafico_avaliacoes_por_funcionario AS
SELECT
    f.nome,
    SUM(CASE WHEN a.estrelas = 5 THEN 1 ELSE 0 END) AS cinco_estrelas,
    SUM(CASE WHEN a.estrelas = 4 THEN 1 ELSE 0 END) AS quatro_estrelas,
    SUM(CASE WHEN a.estrelas = 3 THEN 1 ELSE 0 END) AS tres_estrelas,
    SUM(CASE WHEN a.estrelas = 2 THEN 1 ELSE 0 END) AS duas_estrelas,
    SUM(CASE WHEN a.estrelas = 1 THEN 1 ELSE 0 END) AS uma_estrela,
    SUM(CASE WHEN a.estrelas = 0 THEN 1 ELSE 0 END) AS zero_estrelas
FROM avaliacao a
         JOIN (
    SELECT f.id_funcionario, u.nome
    FROM usuario u
             JOIN funcionario f ON u.id_usuario = f.fk_usuario
) f ON a.fk_funcionario = f.id_funcionario
GROUP BY a.fk_funcionario, f.nome;

CREATE OR REPLACE VIEW grafico_avaliacoes_por_consulta AS
SELECT
    SUM(CASE WHEN a.estrelas = 5 THEN 1 ELSE 0 END) AS cinco_estrelas,
    SUM(CASE WHEN a.estrelas = 4 THEN 1 ELSE 0 END) AS quatro_estrelas,
    SUM(CASE WHEN a.estrelas = 3 THEN 1 ELSE 0 END) AS tres_estrelas,
    SUM(CASE WHEN a.estrelas = 2 THEN 1 ELSE 0 END) AS duas_estrelas,
    SUM(CASE WHEN a.estrelas = 1 THEN 1 ELSE 0 END) AS uma_estrela,
    SUM(CASE WHEN a.estrelas = 0 THEN 1 ELSE 0 END) AS zero_estrela
FROM avaliacao a;