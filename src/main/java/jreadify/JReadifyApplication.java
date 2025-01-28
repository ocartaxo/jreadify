package jreadify;

import jreadify.application.JReadify;
import jreadify.cli.OptionsReaderCLI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class JReadifyApplication implements CommandLineRunner {

    private final JReadify jReadify;
    private final OptionsReaderCLI cliOptions;

    public JReadifyApplication(JReadify jReadify, OptionsReaderCLI optionsReaderCLI) {
        this.jReadify = jReadify;
        this.cliOptions = optionsReaderCLI;
    }

    public static void main(String[] args) {
        SpringApplication.run(JReadifyApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            cliOptions.parseArgs(args);
            jReadify.execute(cliOptions);

            log.info("Arquivo gerado com sucesso! Path: {}", cliOptions.getOutputFilesDir());
        } catch (Exception ex) {
            log.error("Erro ao gerar arquivo: {}", ex.getMessage());
            if (cliOptions.isVerboseMode()) {
                ex.printStackTrace();
            }
            System.exit(1);
        }
    }
}