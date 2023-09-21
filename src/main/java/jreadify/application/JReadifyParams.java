package jreadify.application;

import java.nio.file.Path;

public interface JReadifyParams {

    String getFormat();
    Path getMdFilesDir();
    Path getOutputFilesDir();
}
