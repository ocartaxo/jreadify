package jreadify.cli;

import jreadify.application.JReadify;

import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {

        boolean verboseMode = false;

        try {

            var cliOptions = new CliOptionsReader(args);

            Path outputFilesDir = cliOptions.getOutputFilesDir();
            verboseMode = cliOptions.isVerboseMode();

            JReadify jReadify = new JReadify();
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