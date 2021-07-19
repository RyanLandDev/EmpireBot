package net.ryanland.empire.sys.externalfiles;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class ExternalFile extends File {
    public ExternalFile(@NotNull String pathname) {
        super(pathname);
    }

    public String getContent() throws IOException {
        return new String(Files.readAllBytes(this.toPath()));
    }

    public void write(byte[] content) throws IOException {
        this.delete();
        this.createNewFile();
        Files.write(this.toPath(), content, StandardOpenOption.WRITE);
    }

    public void write(String content) throws IOException {
        write(content.getBytes());
    }

    public void write(JsonObject json) throws IOException {
        write(json.toString());
    }

    public String getExtension() {
        return getPath().replaceFirst("^.*\\.", "");
    }

    public JsonObject parseJson() throws IOException {
        if (!getExtension().equals(ExternalFileType.JSON.getExtension())) {
            throw new UnsupportedOperationException();
        }
        return JsonParser.parseString(getContent()).getAsJsonObject();
    }
}
