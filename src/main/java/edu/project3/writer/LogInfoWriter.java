package edu.project3.writer;

import edu.project3.writer.table.Table;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Map;

public abstract class LogInfoWriter {
    private static final String DEFAULT_PATH = "src/main/resources/project3";


    public String write(Map<String, Table> infos) throws IOException {
        String filename = getOutputFilename();
        String formattedText = createFormattedText(infos);
        try {
            Path file = Paths.get(filename);
            Files.write(file, formattedText.getBytes(), StandardOpenOption.CREATE);
            return file.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new IOException("Не удалось записать статистику логов в файл");
        }
    }

    private String getOutputFilename() {
        return DEFAULT_PATH + "/log_stat_"
            + LocalDateTime.now().format(Formatter.FILE_DATE) + "." + getExtension();
    }

    protected abstract String getExtension();

    private String createFormattedText(Map<String, Table> infos) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Table> entry : infos.entrySet()) {
            result.append(printTitle(entry.getKey()))
                .append("\n\n")
                .append(printTable(entry.getValue()))
                .append("\n");
        }
        return result.toString();
    }

    protected abstract String printTitle(String title);

    protected abstract String printTable(Table table);
}
