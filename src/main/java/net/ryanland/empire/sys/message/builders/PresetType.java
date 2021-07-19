package net.ryanland.empire.sys.message.builders;

public enum PresetType {
    DEFAULT(0x2f3136),
    NOTIFICATION(0x5dadec),
    ERROR(0xdd2e44, "Error"),
    WARNING(0xffcc4d, "Warning"),
    SUCCESS(0x4ccd6a, "Success");

    private final int color;
    private final String defaultTitle;

    PresetType(int color) {
        this.color = color;
        this.defaultTitle = null;
    }

    PresetType(int color, String defaultTitle) {
        this.color = color;
        this.defaultTitle = defaultTitle;
    }

    public int getColor() {
        return color;
    }

    public String getDefaultTitle() {
        return defaultTitle;
    }
}
