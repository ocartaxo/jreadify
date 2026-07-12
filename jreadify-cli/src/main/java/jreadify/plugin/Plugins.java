package jreadify.plugin;

import jreadify.domain.Chapter;
import jreadify.domain.Ebook;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ServiceLoader;

@Component
public class Plugins {

    private final List<Plugin> plugins;

    public Plugins() {
        this(ServiceLoader.load(Plugin.class).stream().map(ServiceLoader.Provider::get).toList());
    }

    public Plugins(List<Plugin> plugins) {
        this.plugins = List.copyOf(plugins);
    }

    public void rendered(Chapter chapter) {
        plugins.forEach(plugin -> chapter.setHtmlContent(plugin.afterRender(chapter.getHtmlContent())));
    }

    public void generated(Ebook ebook) {
        plugins.forEach(plugin -> plugin.afterAssemble(ebook));
    }
}

