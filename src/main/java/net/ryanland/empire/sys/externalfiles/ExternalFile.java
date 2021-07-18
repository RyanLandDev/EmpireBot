package net.ryanland.empire.sys.externalfiles;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ExternalFile extends File {
    public ExternalFile(@NotNull String pathname) {
        super(pathname);
    }

    public String getContent() throws IOException {
        return new String(Files.readAllBytes(this.toPath()));
    }
}
