package name.ulbricht.convert.fxml;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

import name.ulbricht.convert.fxml.io.IconConverter;
import name.ulbricht.convert.fxml.io.IconFinder;

/// The main class of the application, serving as the entry point.
final class Main {

    /// The main method.
    /// 
    /// @param args The command-line arguments passed to the application.
    static void main(final String... args) {

        final var iconDirectory = Path.of("../assets");

        final var icons = IconFinder.findIcons(iconDirectory);

        icons.forEach(icon -> {
            try {
                IconConverter.convert(icon);
            } catch (final IOException ex) {
                throw new UncheckedIOException(ex);
            }
        });
    }
}
