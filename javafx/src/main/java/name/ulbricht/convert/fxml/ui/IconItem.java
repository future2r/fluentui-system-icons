package name.ulbricht.convert.fxml.ui;

import static java.util.Objects.requireNonNull;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import name.ulbricht.convert.fxml.io.Icon;
import name.ulbricht.convert.fxml.io.Size;
import name.ulbricht.convert.fxml.io.Style;
import name.ulbricht.convert.fxml.io.Variant;

public final class IconItem {

    private final Icon icon;
    private final ReadOnlyStringWrapper name = new ReadOnlyStringWrapper();

    public IconItem(final Icon icon) {
        this.icon = requireNonNull(icon);

        this.name.set(icon.name());
    }

    public ReadOnlyStringProperty nameProperty() {
        return this.name.getReadOnlyProperty();
    }

    public String getName() {
        return nameProperty().get();
    }

    public Variant getVariant(final Style style, final Size size) {
        return this.icon.variants().stream()
                .filter(variant -> variant.style() == style)
                .filter(variant -> variant.size() == size)
                .findFirst().orElse(null);
    }
}
