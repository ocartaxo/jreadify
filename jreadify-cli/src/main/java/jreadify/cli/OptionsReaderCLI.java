package jreadify.cli;

import jreadify.application.JReadifyParams;
import jreadify.domain.EbookFormat;
import org.apache.commons.cli.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

@Component
public class OptionsReaderCLI implements JReadifyParams {
    private EbookFormat format;
    private boolean verboseMode;
    private Path mdFilesDir;
    private Path outputFilesDir;

    public void parseArgs(String[] args) {

        var options = AvailableOptionsCLI.getOptions();

        CommandLine cmd = parseArgs(args, options);

        setFormat(cmd);
        setMdFilesDir(cmd);
        setVerboseMode(cmd);
        setOutputFilesDir(cmd);

    }

    private void setVerboseMode(CommandLine cmd) {
        verboseMode = cmd.hasOption('v');
    }

    private void setOutputFilesDir(CommandLine cmd) {
        String ebookOutputName = cmd.getOptionValue("output");

        if (ebookOutputName != null) {
            outputFilesDir = Paths.get(ebookOutputName);
        } else {
            outputFilesDir = Paths.get("book-output/book." + format.name().toLowerCase());
        }

        try {
            if (Files.isDirectory(outputFilesDir)) {
                // deleta arquivos do diretório recursivamente
                Files.walk(outputFilesDir)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile).forEach(File::delete);
            } else {
                Files.deleteIfExists(outputFilesDir);
            }
        } catch (IOException | SecurityException ex) {
            System.err.println("Ocorreu um erro ao acessar o diretório de saída. Erro:" + ex.getMessage());
        }
    }

    private void setFormat(CommandLine cmd) {
        String ebookFormat = cmd.getOptionValue("format");
        if (ebookFormat != null) {
            format = EbookFormat.valueOf(ebookFormat.toUpperCase());
        } else {
            format = EbookFormat.PDF;
        }
    }

    private void setMdFilesDir(CommandLine cmd) {
        String mdDirName = cmd.getOptionValue("dir");

        if (mdDirName != null) {
            mdFilesDir = Paths.get(mdDirName);
            if (!Files.isDirectory(mdFilesDir)) {
                throw new IllegalArgumentException(mdDirName + " não é um diretório.");
            }
        } else {
            mdFilesDir = Paths.get("/home/ocartaxo/IdeaProjects/jreadify/livro-exemplo");
        }
    }

    private CommandLine parseArgs(String[] args, Options options) {
        var cmdParser = new DefaultParser();
        var help = new HelpFormatter();

        try {
            if (args.length == 0){
                throw new ParseException("Sem argumentos");
            }

            return cmdParser.parse(options, args);
        } catch (ParseException e) {
            help.printHelp("jreadify", options);
            throw new IllegalArgumentException("Opção inválida", e);
        }
    }

    private static class AvailableOptionsCLI {
        public static Options getOptions() {
            Options options = new Options();
            options.addOption(new Option("d", "dir", true, "Diretório que contém os arquivos md. Default: diretório atual."));
            options.addOption(new Option("f", "format", true, "Formato de saída do ebook. Pode ser: pdf ou epub. Default: pdf"));
            options.addOption(new Option("o", "output", true, "Arquivo de saída do ebook. Default: book.{formato}."));
            options.addOption(new Option("v", "verbose", false, "Habilita modo verboso. Default: false"));

            return options;
        }

    }

    @Override
    public EbookFormat getFormat() {
        return format;
    }

    @Override
    public boolean isVerboseMode() {
        return verboseMode;
    }

    @Override
    public Path getMdFilesDir() {
        return mdFilesDir;
    }

    @Override
    public Path getOutputFilesDir() {
        return outputFilesDir;
    }
}
