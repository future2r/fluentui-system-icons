package name.ulbricht.convert.fxml.ui;

import java.io.IOException;
import java.nio.file.Files;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import name.ulbricht.convert.fxml.io.Variant;

public final class VariantTableCell extends TableCell<IconItem, Variant> {

    @Override
    protected void updateItem(final Variant item, final boolean empty) {
        super.updateItem(item, empty);

        this.setText(null);

        if (empty || item == null) {
            this.setGraphic(null);
        } else {
            this.setGraphic(loadIcon(item));
        }
    }

    private static Node loadIcon(final Variant variant) {
        final var fxmlFile = variant.file().getParent().getParent().resolve("FXML").resolve(variant.fxmlFileName());
        try (final var is = Files.newInputStream(fxmlFile)) {
            final var fxmlLoader = new FXMLLoader();
            return fxmlLoader.load(is);
        } catch (final IOException ex) {
            throw new RuntimeException("Failed to load FXML for variant: " + variant, ex);
        }
    }
}
