package jreadify.application;

import jreadify.domain.EbookFormat;

import java.nio.file.Path;

public interface JReadifyParams {

    EbookFormat getFormat();
    Path getMdFilesDir();
    Path getOutputFilesDir();
    boolean isVerboseMode();

    void parseArgs(String[] args);
}
