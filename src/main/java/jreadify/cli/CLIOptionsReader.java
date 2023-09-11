package jreadify.cli;

import lombok.Getter;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

@Getter
class CLIOptionsReader {
    private String formato;
    private boolean modoVerboso = false;
    private Path diretorioDosMD;
    private Path arquivoDeSaida;

    public CLIOptionsReader(String[] args) throws IOException {

        var options = AvailableOptionsCLI.getOptions();

        CommandLine cmd = parseArgs(args, options);

        String mdDirName = cmd.getOptionValue("dir");

        if (mdDirName != null) {
            diretorioDosMD = Paths.get(mdDirName);
            if (!Files.isDirectory(diretorioDosMD)) {
                throw new IllegalArgumentException(mdDirName + " não é um diretório.");
            }
        } else {
            diretorioDosMD = Paths.get("");
        }

        String ebookFormat = cmd.getOptionValue("format");

        if (ebookFormat != null) {
            formato = ebookFormat.toLowerCase();
        } else {
            formato = "pdf";
        }

        String ebookOutputName = cmd.getOptionValue("output");
        if (ebookOutputName != null) {
            arquivoDeSaida = Paths.get(ebookOutputName);
        } else {
            arquivoDeSaida = Paths.get("book." + formato.toLowerCase());
        }
        if (Files.isDirectory(arquivoDeSaida)) {
            // deleta arquivos do diretório recursivamente
            Files.walk(arquivoDeSaida).sorted(Comparator.reverseOrder())
                    .map(Path::toFile).forEach(File::delete);
        } else {
            Files.deleteIfExists(arquivoDeSaida);
        }

    }

    private CommandLine parseArgs(String[] args, Options options){
        var cmdParser = new DefaultParser();
        var help = new HelpFormatter();

        try {
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
            options.addOption(new Option("f", "format", true,"Formato de saída do ebook. Pode ser: pdf ou epub. Default: pdf"));
            options.addOption(new Option("o", "output", true,"Arquivo de saída do ebook. Default: book.{formato}."));
            options.addOption(new Option("v", "verbose", false,"Habilita modo verboso."));

            return options;
        }

    }


}
