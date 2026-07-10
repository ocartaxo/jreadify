package jreadify.cli;

import jreadify.application.JReadify;
import jreadify.application.JReadifyParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"jreadify"})
public class Main implements CommandLineRunner {

    private final JReadify jReadify;
    private final JReadifyParams cliOptions;

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public Main(JReadify jReadify, JReadifyParams optionsReaderCLI) {
        this.jReadify = jReadify;
        this.cliOptions = optionsReaderCLI;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            cliOptions.parseArgs(args);
            jReadify.execute(cliOptions);

            log.info("Arquivo gerado com sucesso! Path: {}", cliOptions.getOutputFilesDir());
        } catch (Exception ex) {
            log.error("Erro ao gerar arquivo: {}", ex);
            if (cliOptions.isVerboseMode()) {
                ex.printStackTrace();
            }
            System.exit(1);
        }
    }
}