package edu.project3.writer;

import edu.project3.writer.table.MarkdownTablePrinter;
import edu.project3.writer.table.Table;

public class MarkdownLogInfoWriter extends LogInfoWriter {
    @Override
    protected String getExtension() {
        return Extension.MARKDOWN.getExt();
    }

    @Override
    protected String printTitle(String title) {
        return "#### " + title;
    }

    @Override
    protected String printTable(Table table) {
        return new MarkdownTablePrinter().printTable(table);
    }
}
