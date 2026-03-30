package name.ulbricht.convert.fxml.io;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.regex.Pattern;

public record Variant(String iconName, Path file, Size size, Style style, Direction direction) {

    static Pattern fileNamePattern = Pattern
            .compile("^.*_(10|12|16|20|24|28|32|48)_(regular|filled|light|color)(_(ltr|rtl))?\\.svg$");

    static Variant of(final Path file) {
        final var filename = file.getFileName().toString();

        if (!fileNamePattern.matcher(filename).matches()) {
            throw new IllegalArgumentException("Invalid file name: " + filename);
        }

        final var matcher = fileNamePattern.matcher(filename);
        if (!matcher.matches())
            throw new IllegalArgumentException("Invalid file name: " + filename);

        final var sizeGroup = matcher.group(1);
        final var size = Size.valueOf("SIZE_" + sizeGroup);

        final var styleGroup = matcher.group(2);
        final var style = Style.valueOf(styleGroup.toUpperCase());

        final var directionGroup = matcher.group(4);
        final var direction = directionGroup == null ? Direction.NONE
                : Direction.valueOf(directionGroup.toUpperCase());

        final var iconName = file.getParent().getParent().getFileName().toString();
        return new Variant(iconName, file, size, style, direction);
    }

    public Variant {
        requireNonNull(file);
        requireNonNull(size);
        requireNonNull(style);
        requireNonNull(direction);
    }

    public String fxmlFileName() {
        return "%s_%s_%s%s.fxml".formatted(
                this.iconName.toLowerCase().replace(' ', '_'),
                Integer.toString(this.size.size()),
                this.style.name().toLowerCase(),
                this.direction == Direction.NONE ? ""
                        : "_" + this.direction.name().toLowerCase());
    }
}
