package name.ulbricht.convert.fxml.ui;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import name.ulbricht.convert.fxml.io.IconFinder;
import name.ulbricht.convert.fxml.io.Size;
import name.ulbricht.convert.fxml.io.Style;
import name.ulbricht.convert.fxml.io.Variant;

public final class MainWindow {

    @FXML
    private TableView<IconItem> iconsTableView;

    @FXML
    private void initialize() {

        final Callback<TableColumn<IconItem, Variant>, TableCell<IconItem, Variant>> variantCellFactory = _ -> new VariantTableCell();

        for (final var style : new Style[] { Style.REGULAR, Style.FILLED, Style.LIGHT }) {
            for (final var size : Size.values()) {
                final var column = new TableColumn<IconItem, Variant>(Integer.toString(size.size()));
                column.setCellValueFactory(
                        features -> new ReadOnlyObjectWrapper<Variant>(features.getValue().getVariant(style, size))
                                .getReadOnlyProperty());
                column.setCellFactory(variantCellFactory);
                column.setPrefWidth(Math.max(50, size.size() * 1.5));
                this.iconsTableView.getColumns().add(column);
            }
        }

        final var iconDirectory = Path.of("../assets");
        final var icons = IconFinder.findIcons(iconDirectory);

        this.iconsTableView.getItems().addAll(icons.stream().map(IconItem::new).toList());
    }
}
