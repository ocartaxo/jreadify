package jreadify.md;

import jreadify.domain.Chapter;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Text;

public class TitleExtractor extends AbstractVisitor {

    private final Chapter chapter;

    public TitleExtractor(Chapter chapter) {
        this.chapter = chapter;
    }

    @Override
    public void visit(Heading heading) {
        if (heading.getLevel() == 1) {
            chapter.setTitle(((Text) heading.getFirstChild()).getLiteral());
        } else if (heading.getLevel() == 2) {
            // TODO seção
        } else if (heading.getLevel() == 3) {
            // TODO título
        }
    }
}
