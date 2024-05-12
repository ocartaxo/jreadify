package jreadify.application;

import jreadify.domain.Chapter;
import jreadify.md.MD2HtmlRenderWthCommonMark;

import java.nio.file.Path;
import java.util.List;

public interface MD2HtmlRender {
    List<Chapter> render(Path mdFilesDir);

}
