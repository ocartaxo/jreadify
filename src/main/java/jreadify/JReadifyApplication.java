package jreadify;

import jreadify.application.JReadify;
import jreadify.cli.OptionsReaderCLI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Path;

@SpringBootApplication
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
        boolean verboseMode = false;
        try {
            cliOptions.parseArgs(args);

            Path outputFilesDir = cliOptions.getOutputFilesDir();
            verboseMode = cliOptions.isVerboseMode();

            jReadify.execute(cliOptions);

            System.out.println("Arquivo gerado com sucesso: " + outputFilesDir);

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            if (verboseMode) {
                ex.printStackTrace();
            }
            System.exit(1);
        }
    }
}