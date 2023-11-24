package edu.hw6.task6;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PortScannerTest {

    @Test
    public void filterBusyPorts() {
        List<PortInfo> portsInfo = PortScanner.filterBusyPorts();
        Assertions.assertNotNull(portsInfo);
        Assertions.assertFalse(portsInfo.isEmpty());
    }
}
