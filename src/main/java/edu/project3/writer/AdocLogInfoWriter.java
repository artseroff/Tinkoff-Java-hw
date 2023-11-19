package edu.project3.writer;

import edu.project3.writer.table.AdocTablePrinter;
import edu.project3.writer.table.Table;

public class AdocLogInfoWriter extends LogInfoWriter {
    @Override
    protected String getExtension() {
        return Extension.ADOC.getExt();
    }

    @Override
    protected String printTitle(String title) {
        return "==== " + title;
    }

    @Override
    protected String printTable(Table table) {
        return new AdocTablePrinter().printTable(table);
    }
}
