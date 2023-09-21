package jreadify.md;

import jreadify.domain.Chapter;

import java.nio.file.Path;
import java.util.List;

public interface Md2HtmlRender {
    List<Chapter> render(Path mdFilesDir);
}
