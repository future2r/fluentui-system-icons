package name.ulbricht.convert.fxml.io;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public final class IconConverter {

    public static void convert(final Icon icon) throws IOException {
        final var fxmlDirectory = icon.directory().resolve("FXML");

        // Ensure directory exists and is empty
        if (Files.exists(fxmlDirectory))
            emptyDirectory(fxmlDirectory);
        else
            Files.createDirectory(fxmlDirectory);

        // Convert each variant
        icon.variants().stream()
                .filter(variant -> variant.style() != Style.COLOR) // ignore color icons
                .forEach(variant -> convertVariant(fxmlDirectory, icon, variant));
    }

    /// The FXML file template, with placeholders.
    /// 
    /// - %1$s: The size of the icon, used for the width and height of the Rectangle.
    /// - %2$s: The SVG path elements
    private static final String FXML_TEMPLATE = """
            <?xml version="1.0" encoding="UTF-8"?>
            <?import javafx.scene.Group?>
            <?import javafx.scene.shape.Rectangle?>
            <?import javafx.scene.shape.SVGPath?>
            <Group xmlns="http://javafx.com/javafx/25" xmlns:fx="http://javafx.com/fxml/1">
                <Rectangle width="%1$s" height="%1$s" stroke="transparent" fill="transparent"/>
            %2$s
            </Group>""";

    /// The FXML template for the SVGPath element, with placeholders.
    /// 
    /// - %1$s: The style class, derived from the variant's properties.
    /// - %2$s: The SVG path content place holder
    private static final String FXML_PATH_TEMPLATE = "    <SVGPath styleClass=\"fluent-icon-%1$s\" content=\"%2$s\"/>";

    private static final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    private static final DocumentBuilder documentBuilder;
    static {
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (final ParserConfigurationException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static void convertVariant(final Path fxmlDirectory, final Icon icon, final Variant variant) {
        // Extract the path
        try (final var is = Files.newInputStream(variant.file())) {
            final var doc = documentBuilder.parse(is);
            final var paths = doc.getElementsByTagName("path");

            if (paths.getLength() == 0) {
                throw new IOException("No <path> element found in SVG file: " + variant.file());
            }

            final var pathElements = new ArrayList<String>();
            for (int i = 0; i < paths.getLength(); i++) {
                final var pathElement = paths.item(i);
                final var pathData = pathElement.getAttributes().getNamedItem("d").getNodeValue();

                // Derive style class from variant properties
                final var styleClass = variant.style().name().toLowerCase();

                // Format the SVGPath element
                final var svgPathElement = String.format(FXML_PATH_TEMPLATE, styleClass, pathData);
                pathElements.add(svgPathElement);
            }

            // Format the FXML content
            final var fxmlContent = String.format(FXML_TEMPLATE, Integer.toString(variant.size().size()),
                    pathElements.stream().collect(Collectors.joining("\n")));

            // Write to file
            final var outputFile = fxmlDirectory.resolve(variant.fxmlFileName());
            Files.writeString(outputFile, fxmlContent);

        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        } catch (final SAXException ex) {
            throw new UncheckedIOException(new IOException("Failed to parse SVG file: " + variant.file(), ex));
        }
    }

    private static void emptyDirectory(final Path directory) {
        try (var walk = Files.list(directory)) {
            walk.forEach(path -> {
                try {
                    if (Files.isDirectory(path)) {
                        // Recursively delete directory
                        try (var subtree = Files.walk(path)) {
                            subtree.sorted((a, b) -> b.compareTo(a))
                                    .forEach(p -> {
                                        try {
                                            Files.delete(p);
                                        } catch (final IOException ex) {
                                            throw new UncheckedIOException(ex);
                                        }
                                    });
                        }
                    } else {
                        Files.delete(path);
                    }
                } catch (final IOException ex) {
                    throw new UncheckedIOException(ex);
                }
            });
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
