package edu.project3.reader;

import edu.project3.log.Log;
import java.util.List;

public record LogsData(List<Log> logs, List<String> paths) {
}
