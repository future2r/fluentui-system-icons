package name.ulbricht.convert.fxml.io;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.List;

public record Icon(Path directory, List<Variant> variants) {

    public Icon {
        requireNonNull(directory);
        requireNonNull(variants);
    }

    public String name() {
        return this.directory.getFileName().toString();
    }
}
