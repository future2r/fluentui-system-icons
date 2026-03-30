package name.ulbricht.convert.fxml.io;

public enum Size {

    SIZE_10(10),

    SIZE_12(12),

    SIZE_16(16),

    SIZE_20(20),

    SIZE_24(24),

    SIZE_28(28),

    SIZE_32(32),

    SIZE_48(48);

    private final int size;

    Size(final int size) {
        this.size = size;
    }

    public int size() {
        return this.size;
    }
}
