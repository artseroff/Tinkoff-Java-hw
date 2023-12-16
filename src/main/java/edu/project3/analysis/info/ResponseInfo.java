package edu.project3.analysis.info;

import edu.project3.log.HttpResponseCodes;
import edu.project3.writer.Formatter;
import edu.project3.writer.table.Table;
import java.util.List;

public record ResponseInfo(int response, int count) {

    public static Table toTable(List<ResponseInfo> infos) {
        return new Table(new String[]{"Код", "Имя", "Количество"},
            infos.stream()
                .map(i -> new String[]{
                    String.valueOf(i.response),
                    HttpResponseCodes.getName(i.response),
                    Formatter.INT.format(i.count)})
                .toList());
    }
}
