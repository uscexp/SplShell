# AGENTS Guide for SplShell

## Project purpose and execution path
- SplShell is an interpreter for SPL scripts: source text -> PEG parser -> AST -> runtime execution.
- Primary entrypoint is `SplInterpreter` (`src/main/java/com/github/uscexp/splshell/interpreter/SplInterpreter.java`).
- Typical flow in code: `executeFromFile(...)` / `executeFromStringInput(...)` parses with `SplParser` and executes via `AstInterpreter`.
- Runtime state is process-scoped using `ProcessStore.getInstance(id)`; keep `id` handling consistent when adding execution paths.

## Architecture map (read these first)
- `src/main/java/com/github/uscexp/splshell/parser/SplParser.java`: parser façade and parse orchestration.
- `src/main/resources/Spl.peg`: SPL grammar (compilation unit, statements, expressions); grammar edits require parser test updates.
- `src/main/java/com/github/uscexp/splshell/parser/Ast*TreeNode.java`: AST node types used by parser/interpreter bridge.
- `src/main/java/com/github/uscexp/splshell/interpreter/MethodDefinition.java`: reflection-based invocation layer.
- `src/main/resources/MethodAliases.def`: maps SPL-callable names/signatures to Java methods/constructors.

## Project-specific conventions
- AST class naming pattern is `Ast<Concept>TreeNode` (for example `AstIfStatementTreeNode`, `AstAdditiveExpressionTreeNode`).
- `SplInterpreter` is a singleton (`getInstance(OutputStream)`); avoid creating alternate interpreter instances unless refactoring deliberately.
- New built-in SPL functions should be wired through `MethodAliases.def` first, then backed by Java methods (see mappings to `util/HelperMethods`).
- Reflection calls support static and instance targets; preserve existing `MethodDefinition` behavior for parameter conversion and invocation.
- Use `SplShellException` for wrapping interpreter/parser execution failures.

## Build and test workflow
- Build/compile: `mvn clean compile`
- Run tests: `mvn test`
- Package artifact: `mvn package`
- Java version is set via Maven compiler release `26` in `pom.xml`; keep compatibility in mind when introducing APIs.
- Tests are split by concern:
  - parser tests: `src/test/java/com/github/uscexp/splshell/parser/SplParserTest.java`
  - interpreter tests: `src/test/java/com/github/uscexp/splshell/interpreter/SplInterpreterTest.java`
  - script fixtures: `src/test/resources/*.spl` (for example `fact.spl`, `sqrt.spl`).

## Integration points and dependencies
- Parsing/runtime internals rely on `grappa.extension` classes (`AstInterpreter`, `ProcessStore`, `Primitive`, `MethodDeclaration`).
- Built-in method alias loading uses `BlockFormatPropertyFile` over `MethodAliases.def`.
- Commons/lang utilities appear in core classes (for example `StringUtils`, reflection helpers); follow existing utility choices before adding new libs.

## Safe change checklist for agents
- If you change grammar (`Spl.peg`), verify matching AST/parser behavior and run parser + interpreter tests.
- If you change method invocation or aliases, validate with a script-level test in `src/test/resources` and interpreter tests.
- If you add runtime state behavior, confirm process isolation semantics via `ProcessStore` usage in `SplInterpreter` paths.
- Keep changes minimal and localized: parser changes in parser/grammar files, runtime behavior in interpreter/util files.

## PR checklist (team-ready)
- Confirm changed files are in the expected layer only (parser in `parser/` + `Spl.peg`, runtime in `interpreter/`/`util/`).
- For grammar edits, include at least one parser assertion in `src/test/java/com/github/uscexp/splshell/parser/SplParserTest.java`.
- For runtime/alias edits, include or update a script fixture in `src/test/resources/*.spl` plus `SplInterpreterTest` coverage.
- If touching invocation (`MethodDefinition`) or aliases (`MethodAliases.def`), verify both static and instance call paths still work.
- If touching execution entrypoints in `SplInterpreter`, verify `id` flow and `ProcessStore` isolation are preserved.
- Run `mvn test` before merge; if behavior spans parsing and execution, run full suite rather than single test class.

## Do / Don't for AI agents in this repo
- Do start with `SplInterpreter`, `SplParser`, `Spl.peg`, and `MethodAliases.def` before proposing structural changes.
- Do preserve the Java release configured in `pom.xml` (currently `26`) when introducing APIs and language features.
- Do prefer extending existing helper/mapping patterns (`util/HelperMethods`, alias definitions) over adding new integration layers.
- Do wrap parser/interpreter execution failures in `SplShellException`, consistent with current entrypoints.
- Don't introduce alternate interpreter lifecycle patterns while `SplInterpreter` is singleton-based.
- Don't bypass alias mapping by hard-coding built-ins directly in parser/interpreter logic.
- Don't change AST naming/style (`Ast<Concept>TreeNode`) or split behavior across unrelated packages.
- Don't add dependencies for utility functionality already covered by existing libs (`commons-lang3`, grappa extension stack).

## First 30 minutes (agent fast start)
- Read in order: `SplInterpreter.java` -> `SplParser.java` -> `Spl.peg` -> `MethodAliases.def`.
- Decide change layer first (grammar/parser vs runtime/interpreter) and keep file edits within that boundary.
- For new SPL-callable behavior, add alias mapping first, then implement Java backing method.
- Reuse existing AST naming and runtime patterns; avoid introducing new lifecycle or integration paths.
- Validate with `mvn test`; for parser-affecting changes, verify both `SplParserTest` and `SplInterpreterTest` impact.

