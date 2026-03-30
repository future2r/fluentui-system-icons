package name.ulbricht.convert.fxml.io;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class IconFinder {

    public static List<Icon> findIcons(final Path directory) {
        requireNonNull(directory);

        // Each subdirectory represents an icon
        try (final var directories = Files.list(directory)) {
            return directories.filter(Files::isDirectory)
                    .map(IconFinder::createIcon)
                    .toList();
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    private static Icon createIcon(final Path path) {
        return new Icon(path, findVariants(path.resolve("SVG")));
    }

    private static List<Variant> findVariants(final Path svgPath) {
        // Each file represents a variant icon
        try (final var files = Files.list(svgPath)) {
            return files.filter(Files::isRegularFile)
                    .map(Variant::of)
                    .toList();
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
