package edu.project3.writer;

import edu.project3.reader.IllegalConfigurationException;

public enum Extension {
    MARKDOWN("md"),
    ADOC("adoc");

    private final String ext;

    Extension(String extension) {
        this.ext = extension;
    }

    public String getExt() {
        return ext;
    }

    public static Extension parse(String extensionName) throws IllegalConfigurationException {
        for (Extension extension : Extension.values()) {
            if (extension.name().toLowerCase().equals(extensionName)) {
                return extension;
            }
        }
        throw new IllegalConfigurationException("Неверное значение формата вывода");
    }
}
