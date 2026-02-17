# Relatório de Auditoria — OWASP Top 10 (2021)

Resumo: este relatório apresenta uma avaliação estática do código-fonte do projeto `flor-de-lotus` (diretório raiz do repositório fornecido) em relação ao OWASP Top 10 2021. Para cada item do Top 10 eu indico: o que foi encontrado no projeto, grau de severidade (Crítico / Alto / Médio / Baixo), evidências (arquivos/trechos) e recomendações práticas de correção.

Observação: a auditoria foi feita apenas via análise do código-fonte e arquivos de configuração disponíveis no repositório. Testes ativos (pentest, injeções reais) não foram executados.

---

## Sumário executivo (top issues)
1. Broken Access Control (A01) — CRÍTICO: várias rotas CRUD estão permitidas sem autenticação (ex.: `/testes`, `/consultas`) devido a `SecurityConfiguracao.URLS_PERMITIDAS`.
2. Sensitive Data Exposure / Cryptographic Failures (A02) — ALTO: segredos em texto claro em `application.properties` e `application-prod.properties` (JWT secret, mail password, DB password).
3. Security Misconfiguration (A05) — ALTO: CORS permissivo, CSRF globalmente desativado, exposição do `actuator` e `h2-console` em `URLS_PERMITIDAS`.
4. Identification and Authentication Failures (A07) — ALTO/MÉDIO: manipulação de JWT e geração de token com `GerenciadorTokenJwt` (possível má-injeção de `@Value`), e atualização de senha sem re-hash em `UsuarioService.atulizarParcial`.
5. Security Logging and Monitoring Failures (A09) — MÉDIO: logs que expõem o subject do token quando expirado e uso inadequado de formato de log.

---

## Metodologia
- Buscas estáticas por padrões relevantes (controllers, config de segurança, JWT, CORS, upload, queries nativas, ObjectMapper, Feign, logs, properties).
- Leitura manual dos arquivos de configuração e dos pontos críticos identificados.
- Mapeamento de evidências para cada item do OWASP Top 10.

---

## Achados por item OWASP Top 10 (2021)

A01 — Broken Access Control (Crítico)
- O que encontrei:
  - A classe `SecurityConfiguracao` define `URLS_PERMITIDAS` que inclui caminhos amplos e sensíveis: `"/testes/*", "/estoques/*", "/consultas/*", "/movimentacoes/*"`, além de `"/actuator/*"` e `"/h2-console/**"`.
  - Esses caminhos são liberados com `requestMatchers(URLS_PERMITIDAS).permitAll()` (autenticação não exigida).
  - Vários controllers expõem operações de escrita/remoção/atualização nessas rotas (ex.: `TesteController` e `ConsultaController` têm `@PostMapping`, `@DeleteMapping`, `@PatchMapping`).
- Evidências (paths):
  - `src/main/java/flor_de_lotus/config/SecurityConfiguracao.java` — definição `URLS_PERMITIDAS` e `permitAll()`.
  - `src/main/java/flor_de_lotus/teste/TesteController.java` — endpoints `POST /testes`, `DELETE /testes/{id}`, etc.
  - `src/main/java/flor_de_lotus/consulta/ConsultaController.java` — endpoints `POST /consultas`, `DELETE /consultas/{id}`, `PATCH /consultas/{id}`.
- Impacto: usuários não autenticados podem criar, alterar e excluir dados sensíveis sem autorização — perda de confidencialidade, integridade.
- Recomendação:
  - Revise `URLS_PERMITIDAS`: deixar apenas endpoints públicos reais (por exemplo `/usuarios/login`, `/usuarios/cadastro`, docs públicos). Remova permissões gerais (`/testes/*`, `/consultas/*`, `/movimentacoes/*`, `/estoques/*`) se não forem explicitamente públicas.
  - Proteger endpoints sensíveis com roles/authorities (`.hasRole(...)` ou `@PreAuthorize`) e testes automatizados que validam 401/403 quando acessados sem credenciais apropriadas.
  - Fazer revisão de cada controller listado e aplicar controle de acesso no nível de método (ex.: `@PreAuthorize("hasRole('ADMIN')")` para operações administrativas).

A02 — Cryptographic Failures / Sensitive Data Exposure (Alto)
- O que encontrei:
  - Segredos armazenados em texto claro nas propriedades:
    - `src/main/resources/application.properties` contém `jwt.secret` e `spring.mail.password`.
    - `src/main/resources/application-prod.properties` contém `spring.datasource.password`.
  - Arquivo `data.sql` contém hashes de senha (bcrypt) — isto é ok, mas segredos em properties são preocupantes.
- Evidências:
  - `src/main/resources/application.properties`:
    - `jwt.secret=...` (valor em texto claro)
    - `spring.mail.password=lntuzmfuvybxutlu` (texto claro)
  - `src/main/resources/application-prod.properties`:
    - `spring.datasource.password=florDeLotus#2025` (texto claro)
- Impacto: exposição de credenciais, possibilidade de geração/falsificação de tokens JWT, acesso a mailbox ou DB em caso de vazamento do repositório.
- Recomendação:
  - Remover segredos do repositório. Usar um cofre de segredos (HashiCorp Vault, AWS Secrets Manager, Azure Key Vault) ou variáveis de ambiente injetadas no runtime (e.g., `SPRING_DATASOURCE_PASSWORD`).
  - Para JWT secret, use um segredo com entropia suficiente e não commitá-lo no repo. Preferir carregar via `Environment`/variáveis e usar `@ConfigurationProperties` ou fornecer via fornecedor de secrets.
  - Rotacionar segredos que já foram comprometidos (e-mail, DB, JWT secret) e mudar credenciais usadas em `application.properties`/prod.

A03 — Injection (Crítico em geral) — Observações no projeto: BAIXO risco detectado
- O que encontrei:
  - Código usa Spring Data JPA e `@Query` com parâmetros vinculados (`@Param`) em `ArtigoRepository`, que é a prática correta.
  - Não foram encontradas queries nativas concatenadas, uso de `Statement` dinâmico ou `createNativeQuery` com concatenação.
- Evidências:
  - `src/main/java/flor_de_lotus/artigo/ArtigoRepository.java` — `@Query("... :termo ...")` com `@Param`.
- Recomendação:
  - Continuar evitando concatenação manual em JPQL/SQL. Se for necessário usar SQL nativo, utilize `PreparedStatement`/parâmetros.
  - Adicionar testes que tentem strings de input maliciosas em ambientes de teste.

A04 — Insecure Design (Médio)
- O que encontrei:
  - Arquitetura de autorização parece depender fortemente do `URLS_PERMITIDAS` global, o que facilita erros de configuração acidental.
- Recomendação:
  - Usar defense-in-depth: checks no controller/service com `@PreAuthorize` e validação robusta de entradas; revisar design de roles/permissões.

A05 — Security Misconfiguration (Alto)
- O que encontrei:
  - CSRF está globalmente desativado: em `SecurityConfiguracao` existe `.csrf(CsrfConfigurer<HttpSecurity>::disable)`.
  - CORS é configurado com `CorsConfiguration configuracao = new CorsConfiguration(); configuracao.applyPermitDefaultValues();` e registrado para `/**`. `applyPermitDefaultValues()` define um conjunto permissivo de valores (origens/metodos permitidos) e, ao registrar em `/**`, torna o CORS permissivo para toda a API.
  - Métodos permitidos incluem `TRACE` e `HEAD` explicitamente; `TRACE` não deveria ser exposto.
  - `URLS_PERMITIDAS` inclui `"/actuator/*"` e `"/h2-console/**"` — possíveis pontos sensíveis caso ambientes deixem essas rotas ativas em produção.
- Evidências:
  - `src/main/java/flor_de_lotus/config/SecurityConfiguracao.java` — método `filterChain(...)` e `corsConfigurationSource()`.
  - `src/main/resources/application-dev.properties` contém `spring.h2.console.enabled=true` e credenciais H2 `admin/admin` (dev).
- Impacto: configuração insegura permite requests cross-origin e possivelmente CSRF/exfiltration ou acesso a endpoints sensíveis sem proteção adequada.
- Recomendação:
  - Evitar `csrf().disable()` a menos que seja uma API totalmente stateless com proteção adequada; se for API JWT, CSRF pode ser desabilitado mas precisa estar justificado e documentado.
  - Fortalecer CORS: definir `allowedOrigins` explicitamente (domínios confiáveis), remover `applyPermitDefaultValues()` e evitar `/**` broad registration.
  - Remover `/actuator/*` e `/h2-console/**` de `URLS_PERMITIDAS` em produção; proteger o actuator e habilitar apenas endpoints necessários com autenticação e restrição por IP quando aplicável.
  - Remover métodos HTTP desnecessários (ex.: TRACE) da lista de `allowedMethods`.

A06 — Vulnerable and Outdated Components (Alto)
- O que encontrei:
  - O `pom.xml` mostra dependências modernas (Spring Boot parent 3.5.5 no projeto), mas não executei um scanner de CVEs automático aqui.
- Recomendação:
  - Rodar `mvn dependency:tree` e um scanner de vulnerabilidades (OWASP Dependency-Check, Snyk, or GitHub Dependabot) para identificar bibliotecas com CVEs.
  - Manter dependências atualizadas e aplicar policies de atualização.

A07 — Identification and Authentication Failures (Alto / Médio)
- O que encontrei:
  - `GerenciadorTokenJwt` declara campos com `@Value("${jwt.secret}")` e `@Value("${jwt.validity}")` mas a instância é criada em `SecurityConfiguracao` via `new GerenciadorTokenJwt()` dentro de um `@Bean` (método `jwtAuthenticationUtilBean()`):
    - `public GerenciadorTokenJwt jwtAuthenticationUtilBean() { return new GerenciadorTokenJwt(); }`
  - Isso pode impedir a injeção automática de `@Value` se a instância não for totalmente gerenciada/processada pelo container do Spring (potencial risco de `secret`/`jwtTokenValidity` serem null ou não carregados corretamente).
  - `UsuarioService.atulizarParcial` atualiza a senha com `usuario.setSenha(entity.getSenha())` sem re-hash/encoding (linha analisada). Assim, alteração de senha pode gravar senha em texto claro.
- Evidências:
  - `src/main/java/flor_de_lotus/config/GerenciadorTokenJwt.java`
  - `src/main/java/flor_de_lotus/config/SecurityConfiguracao.java` — `jwtAuthenticationUtilBean()`
  - `src/main/java/flor_de_lotus/usuario/UsuarioService.java` — método `atulizarParcial(...)` (atribuição sem `passwordEncoder.encode(...)`).
- Impacto:
  - Má configuração/injeção do JWT secret pode quebrar autenticação ou permitir validação incorreta de tokens.
  - Armazenamento de senha em texto claro ao atualizar representa alto risco de comprometimento de credenciais.
- Recomendação:
  - Tornar `GerenciadorTokenJwt` um bean gerenciado pelo Spring (anotar `@Component`) ou fornecer o `secret`/`validity` via construtor no `@Bean` (injetar valores no `@Bean` factory method) para garantir injeção correta das propriedades.
  - Corrigir `UsuarioService.atulizarParcial` para aplicar `passwordEncoder.encode(newPassword)` antes de salvar. Adicionar testes que verificam que senhas gravadas no DB estão sempre hashadas.
  - Revisar política de expiração de tokens e invalidar tokens quando senha/roles mudarem, se aplicável.

A08 — Software and Data Integrity Failures (Médio)
- Observações:
  - Não foram encontrados usos óbvios de deserialização insegura (`ObjectMapper.enableDefaultTyping(...)`) ou execuções de código externas. Nenhuma estrutura de plugin/artefato assinado foi analisada.
- Recomendação:
  - Evitar habilitar tipos default de Jackson e usar whitelist para tipos permitidos. Adicionar assinatura/verificação de artefatos na pipeline de CI se for necessário executar artefatos externos.

A09 — Security Logging and Monitoring Failures (Médio)
- O que encontrei:
  - Em `AutenticacaoFilter`, no catch de `ExpiredJwtException`, há log com:
    - `LOGGER.info("FALHA AUTENTICACAO - Token expirado. usuario: {} - {}", exception.getClaims().getSubject(), exception.getMessage());`
    - Esse log expõe o `subject` (usuário) ao arquivo de log quando um token expirou.
  - Também há uma chamada `LOGGER.trace("FALHA AUTENTICACAO - stack trace: %s", exception);` que usa placeholder `%s` (não padrão SLF4J) — possivelmente formata incorretamente.
- Impacto:
  - Informações sensíveis (usernames, possivelmente claims) sendo gravadas nos logs podem facilitar análise para atacantes com acesso a logs.
  - Formatação incorreta reduz utilidade de logs e pode ocultar traços necessários para investigação.
- Recomendação:
  - Evitar logar claims sensíveis. Logar apenas o mínimo necessário (ex.: `username` truncado/anônimo ou apenas um evento), e nunca a senha.
  - Usar placeholders SLF4J `{}` ao invés de `%s` e incluir exceções como último parâmetro (e.g., `LOGGER.trace("...", exception);` é ok mas prefira `LOGGER.debug("...", exception)` com cuidado).
  - Implementar rotação de logs e retenção segura; assegurar que logs de aplicação não vazem para repositórios públicos.

A10 — Server-Side Request Forgery (SSRF) (Alto em geral) — Observações no projeto: BAIXO risco detectado
- O que encontrei:
  - Existe um `@FeignClient` fixo para ViaCEP (`EnderecoFeign` com `url = "https://viacep.com.br/ws/"`) e não foram encontrados endpoints que aceitem URLs arbitrárias para requisições HTTP.
- Recomendação:
  - Se adicionar endpoints que fazem fetch de URLs fornecidas pelo usuário, validar e restringir domínios, usar whitelist e evitar solicitações para IPs internos.

---

## Achados adicionais e notas menores
- `src/main/resources/application-dev.properties` habilita `spring.h2.console.enabled=true` e usa credenciais `admin/admin` (dev). Não deixar H2 console habilitado em produção; remover antes de subir ou proteger via autenticação e ACL.
- `CorsConfigurationSource` registra `/**` como padrão; prefira caminhos específicos.
- Dependências no `pom.xml` devem ser scaneadas para CVEs. Recomendo integração de scanner no CI.

---

## Plano de correção (priorizado)
1. Imediato (critico / 24-72h)
   - Remover de `URLS_PERMITIDAS` todas as rotas que não deveriam ser públicas. Implementar autenticação/autorizao nas rotas CRUD (ex.: `/testes`, `/consultas`).
   - Remover segredos de `application*.properties` do repositório e rotacionar as credenciais que já foram expostas (DB, e-mail, JWT secret).
   - Corrigir `UsuarioService.atulizarParcial` para sempre hashear senhas antes de persistir.
2. Alto (1-2 semanas)
   - Revisar a configuração de CORS para especificar `allowedOrigins` explicitamente.
   - Revisar CSRF policy e justificar/descrever o motivo de estar desabilitado; aplicar proteção quando relevante.
   - Proteger `/actuator` e `/h2-console`, ou removê-los do ambiente de produção.
   - Garantir `GerenciadorTokenJwt` recebe `secret`/`validity` corretamente (transformar em bean gerenciado ou injetar via construtor no `@Bean`).
3. Médio (2-4 semanas)
   - Integrar OWASP Dependency-Check / Snyk / Dependabot e corrigir dependências com CVEs.
   - Revisar logging: evitar exposio de claims e melhorar formatação.
   - Criar testes de integração/automatizados para validar controles de acesso (401/403) e hashing de senhas.

---

## Evidências (resumo com arquivos/linhas relevantes)
- `src/main/java/flor_de_lotus/config/SecurityConfiguracao.java` — `URLS_PERMITIDAS` e `permitAll()`; `csrf(CsrfConfigurer<HttpSecurity>::disable)`; `corsConfigurationSource()`.
- `src/main/java/flor_de_lotus/teste/TesteController.java` — endpoints CRUD expostos em `/testes` (POST/DELETE/GET).
- `src/main/java/flor_de_lotus/consulta/ConsultaController.java` — endpoints CRUD expostos em `/consultas` (POST/DELETE/PATCH/GET).
- `src/main/java/flor_de_lotus/config/GerenciadorTokenJwt.java` e `src/main/java/flor_de_lotus/config/SecurityConfiguracao.java` — possível má-injeção de `@Value` para `jwt.secret`/`jwt.validity` devido a instanciação via `new` no `@Bean`.
- `src/main/java/flor_de_lotus/usuario/UsuarioService.java` — método `atulizarParcial` que atualiza senha sem `passwordEncoder.encode(...)`.
- `src/main/resources/application.properties` — contém `jwt.secret` e `spring.mail.password` em texto claro.
- `src/main/resources/application-prod.properties` — contém `spring.datasource.password` em texto claro.
- `src/main/resources/application-dev.properties` — `spring.h2.console.enabled=true` e credenciais `admin/admin` usadas em dev.
- `src/main/java/flor_de_lotus/config/AutenticacaoFilter.java` — logs que expõem `exception.getClaims().getSubject()` no caso de token expirado e uso de formatação `"%s"` em SLF4J.

---

## Checklist rápido para correção
- [ ] Remover rotas sensíveis de `URLS_PERMITIDAS` e proteger endpoints com autenticação/roles.
- [ ] Remover segredos do código e usar variáveis de ambiente/cofre de segredos; rotacionar segredos atuais.
- [ ] Corrigir `atulizarParcial` para hashear senhas antes de salvar.
- [ ] Fortalecer CORS e revisar CSRF policy.
- [ ] Proteger `actuator` e `h2-console` em ambientes de produção.
- [ ] Garantir `GerenciadorTokenJwt` recebe propriedades corretamente (transformar em bean gerenciado ou injetar valores no factory method).
- [ ] Remover logs sensíveis e corrigir placeholders de SLF4J.
- [ ] Executar scanner de dependências e corrigir CVEs.

---

## Próximos passos sugeridos (opcionais)
- Executar um scan de dependências (OWASP Dependency-Check / Snyk) localmente e integrar no CI.
- Rodar um DAST leve (OWASP ZAP) contra um ambiente de staging para validar controles de acesso e possíveis injeções/XSS.
- Implementar testes automatizados que validem as proteções: 401/403 para rotas restritas, hashing de senhas, políticas CORS.

---

Se quiser, eu posso:
- Aplicar correções de código simples e seguras (por exemplo: ajustar `UsuarioService.atulizarParcial` para usar `passwordEncoder.encode(...)` e remover `/testes` e `/consultas` de `URLS_PERMITIDAS`) e rodar os testes.
- Gerar um conjunto de comandos PowerShell para varredura de padrões no repositório (grep/Select-String) e um relatório CSV com os locais encontrados.

Fim do relatório.

