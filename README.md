# SplShell

`SplShell` is an interpreter for the **Simple Programming Language (SPL)**.
It parses SPL source code with a PEG grammar (`Spl.peg`), builds an abstract
syntax tree (AST), and executes the program at runtime.

## What the project provides

- a parser facade via `SplParser`
- AST-based execution via `AstInterpreter`
- function and method mapping through alias definition files (`*.def`)
- extensibility through custom aliases without changing the parser
- process-scoped runtime state via `ProcessStore`

## Architecture at a glance

1. SPL source is read from a file or provided as a string.
2. `SplParser` parses the source against `src/main/resources/Spl.peg`.
3. An AST made of `Ast*TreeNode` nodes is created.
4. `SplInterpreter` executes the AST through `AstInterpreter`.

Important entry points:

- `src/main/java/com/github/uscexp/splshell/interpreter/SplInterpreter.java`
- `src/main/java/com/github/uscexp/splshell/parser/SplParser.java`
- `src/main/resources/Spl.peg`
- `src/main/resources/MethodAliases.def`

## Build and test

Prerequisites: Maven and a Java version compatible with the Maven compiler
configuration (`release 26` in `pom.xml`).

```powershell
mvn clean compile
mvn test
mvn package
```

Test focus areas:

- parser tests: `src/test/java/com/github/uscexp/splshell/parser/SplParserTest.java`
- interpreter tests: `src/test/java/com/github/uscexp/splshell/interpreter/SplInterpreterTest.java`
- SPL fixtures: `src/test/resources/*.spl`

## Using `SplInterpreter`

Typical usage goes through the singleton `SplInterpreter`:

```java
import java.nio.charset.StandardCharsets;

import com.github.uscexp.splshell.interpreter.SplInterpreter;

class FileExecutionExample {

    static void run() throws Exception {
        SplInterpreter interpreter = SplInterpreter.getInstance();
        interpreter.executeFromFile("path/to/script.spl", StandardCharsets.UTF_8);
    }
}
```

Alternatively, you can execute SPL code directly from a string:

```java
import com.github.uscexp.splshell.interpreter.SplInterpreter;

class StringExecutionExample {

    static void run() throws Exception {
        SplInterpreter interpreter = SplInterpreter.getInstance();
        interpreter.executeFromStringInput("int main(string args[]){ return 0; }");
    }
}
```

## Quick Start

### Example SPL script

Create a file named `hello.spl`:

```spl
int main(string args[])
{
    write "Hello from SPL!" + '\n';
    return 0;
}
```

Expected output:

```text
Hello from SPL!
```

### Example with variables and a loop

```spl
int main(string args[])
{
    int sum = 0;

    for (int i = 1; i <= 5; ++i)
    {
        sum = sum + i;
    }

    write "sum = " + sum + '\n';
    return 0;
}
```

Expected output:

```text
sum = 15
```

### Running SPL from Java

```java
import java.nio.charset.StandardCharsets;

import com.github.uscexp.splshell.interpreter.SplInterpreter;

class Example {

    static void main(String[] args) throws Exception {
        SplInterpreter interpreter = SplInterpreter.getInstance();
        interpreter.executeFromFile("hello.spl", StandardCharsets.UTF_8);
    }
}
```

## Alias extension via `~/.splshell`

In addition to the built-in definitions in `MethodAliases.def`, SplShell also
loads additional `*.def` files as an extension mechanism.

Definition file discovery and loading behavior:

1. scan the current working directory for `*.def`
2. if nothing was found, scan the classpath directory for `*.def`
3. if still nothing was found, load the built-in `MethodAliases.def` as fallback
4. additionally, scan the user directory `~/.splshell` (`user.home/.splshell`, created on demand)

This allows users to add their own SPL-callable aliases without modifying the
parser or interpreter implementation.

### Format of an alias definition (`*.def`)

An alias is defined as a `method` block:

```text
method aliasName
{
  params = { "string", "int" };
  type = "java.lang.String";
  realMethod = "substring";
  static = false;
  realParams = { "int" };
  returnType = "string";
}
```

Field meanings:

- `method <name>`: the name used by SPL code
- `params`: the SPL-visible method signature
- `type`: fully qualified Java class name
- `realMethod`: Java method name or `constructor`
- `static`: `true` for static methods, `false` for instance methods
- `realParams`: parameter types used for the reflective Java lookup
- `returnType`: SPL return type name

For instance methods:

- the first entry in `params` is the receiver object
- the receiver object is **not** part of `realParams`

### Example custom alias file

Create a file such as `~/.splshell/MyAliases.def`:

```text
method strlen
{
  params = { "string" };
  type = "java.lang.String";
  realMethod = "length";
  static = false;
  realParams = { };
  returnType = "int";
}
```

## License

GNU Lesser General Public License (LGPL), see `LICENSE`.
