package edu.project3.writer.table;

import java.util.List;

public record Table(String[] headers, List<String[]> rows) {
}
