package net.ryanland.empire.sys.message.builders;

public enum PresetType {
    DEFAULT(0x2f3136, true),
    NOTIFICATION(0x5dadec, true),
    ERROR(0xdd2e44, "Error"),
    WARNING(0xffcc4d, "Warning"),
    SUCCESS(0x4ccd6a, "Success");

    private final int color;
    private final String defaultTitle;
    private final boolean showFooter;

    PresetType(int color) {
        this(color, null);
    }

    PresetType(int color, String defaultTitle) {
        this(color, defaultTitle, false);
    }

    PresetType(int color, boolean showFooter) {
        this(color, null, showFooter);
    }

    PresetType(int color, String defaultTitle, boolean showFooter) {
        this.color = color;
        this.defaultTitle = defaultTitle;
        this.showFooter = showFooter;
    }

    public int getColor() {
        return color;
    }

    public String getDefaultTitle() {
        return defaultTitle;
    }

    public boolean shouldShowFooter() {
        return showFooter;
    }
}
