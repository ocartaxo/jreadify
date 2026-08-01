# JReadify 📚

**JReadify** is a Java-based command-line interface (CLI) application that converts Markdown (`.md`) files into **PDF**, **EPUB**, and **HTML** e-books.

## 🔍 About The Project

The main goal of JReadify is to showcase the practical application of SOLID principles and software design patterns to tackle real-world problems like code extensibility and coupling.

### Key Concepts & Applied Patterns:

- **S - Single Responsibility Principle (SRP)**: Clear separation of concerns across CLI option parsing, Markdown rendering, domain model assembly, and e-book generation.
- **O - Open/Closed Principle (OCP)**: Uses Plugins via the Java Service Loader API (SPI) to extend generator capabilities (e.g., applying CSS themes, calculating statistics) without touching the core codebase (core).
- **L - Liskov Substitution Principle (LSP)**: Favors composition over inheritance and ensures seamless subtype substitution without contract violations.
- **I - Interface Segregation Principle (ISP)**: Keeps interfaces lean and client-specific (AoRenderizarHTML, AoFinalizarGeracao).
- **D - Dependency Inversion Principle (DIP)**: High-level modules depend strictly on abstractions. Applies the Factory pattern for concrete object instantiation.
- **Hexagonal Architecture (Ports & Adapters)**: Completely decouples core domain logic from delivery mechanisms (CLI/Web) and infrastructure libraries.
- **Immutability & Encapsulation**: Leverages immutable objects and the Builder pattern to construct domain models (Capitulo, Ebook).

## 🚀 Getting Started

Follow the instructions below to get a local copy up and running, and to build its dependencies and plugins.

### Prerequisites

To compile and run this project, you will need:

- **Java Development Kit (JDK)**: Version 17 or higher.
- **Apache Maven**: Version 3.8 or higher.
- **Unix-like Environment** (Linux/macOS) or **WSL/Git Bash** on Windows.

### Installation

The project is structured as a Maven multi-module repository. Run the following commands in your terminal to build and install the components into your target execution directory:

1. Build and install the jreadify-cli module

```bash
cd jreadify-cli
mvn clean install
unzip -o target/jreadify-*-distribution.zip -d ~/Desktop
```

2. Build and install the jreadify-core module

```bash
cd ../jreadify-core
mvn clean install
unzip -o target/jreadify-*-distribution.zip -d ~/Desktop
```

3. Build and package the tema-paradizo theme plugin

```bash
cd ../tema-paradizo
mvn clean package
cp target/tema-paradizo-*.jar ~/Desktop/libs/
cp ~/.m2/repository/org/jsoup/jsoup/1.11.2/jsoup-1.11.2.jar ~/Desktop/libs/
```


### 💻 Usage

Once installation is complete and the distribution is extracted to your Desktop, navigate to the target directory and run the jreadify.sh script to generate your e-books.

**Standard execution command:**

```bash
./jreadify.sh -d /path/to/your/sample-book -f pdf
```

**Available CLI Options:**

| Option |    Long Flag     |                      Description                       |           Default |
|:-------|:----------------:|:------------------------------------------------------:|------------------:|
| `-d`   |  `--dir <arg>`   |      Directory containing the book's `.md` files.      | Current directory |
| `-f`   | `--format <arg>` | Output format for the e-book (`pdf`, `epub` or `html`) |             `pdf` |
| `-o`   | `--output <arg>` |              Custom output filename/path               |   `book.{format}` |
| `-v`    |   `--verbose`    |       Enables detailed logging during execution        |          Disabled |



# 📄 License

Distributed under the MIT License. See [LICENSE](LICENSE) for more information.

# 🙌 Acknowledgments

This project was built based on the concepts presented in the book "Desbravando SOLID: Práticas avançadas para códigos de qualidade em Java moderno" by Alexandre Aquiles (Editora Casa do Código).