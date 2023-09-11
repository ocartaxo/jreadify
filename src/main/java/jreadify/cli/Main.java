package jreadify.cli;

import jreadify.application.JReadify;

import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {

        boolean verboseMode = false;

        try {

            var cliOptions = new CLIOptionsReader(args);
            String format = cliOptions.getFormato();
            Path mdFilesDir = cliOptions.getDiretorioDosMD();
            Path outputFilesDir = cliOptions.getArquivoDeSaida();
            verboseMode = cliOptions.isModoVerboso();

            JReadify jReadify = new JReadify();
            jReadify.execute(format, mdFilesDir, outputFilesDir);

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