package edu.hw6.task6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class PortScanner {

    private static final Map<Integer, String> PORTS = Map.of(
        21, "FTP (File Transfer Protocol)",
        22, "SSH (Secure Shell)",
        25, "SMTP (Simple Mail Transfer Protocol)",
        53, "DNS (Domain Name System)",
        80, "HTTP (HyperText Transfer Protocol)",
        445, "Microsoft-DS Active Directory",
        3306, "MySQL Database",
        3389, "Remote Desktop Protocol (RDP)",
        5432, "PostgreSQL Database",
        27017, "MongoDB Database"
    );
    private static final int MAX_PORT_NUMBER = 49151;

    private PortScanner() {
    }

    private static boolean isTcpPortBusy(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            return false;
        } catch (IOException e) {
            return true;
        }
    }

    private static boolean isUdpPortBusy(int port) {
        try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
            return false;
        } catch (IOException e) {
            return true;
        }
    }

    public static List<PortInfo> filterBusyPorts() {
        List<PortInfo> portInfoList = new ArrayList<>();

        for (int port = 0; port <= MAX_PORT_NUMBER; port++) {

            if (isTcpPortBusy(port)) {
                portInfoList.add(new PortInfo(PortInfo.Protocol.TCP, port, PORTS.getOrDefault(port, "")));
            }
            if (isUdpPortBusy(port)) {
                portInfoList.add(new PortInfo(PortInfo.Protocol.UDP, port, PORTS.getOrDefault(port, "")));
            }
        }

        return portInfoList;
    }

    public static String stringifyPorts(@NotNull List<PortInfo> ports) {
        return ports.stream()
            .map(PortInfo::toString)
            .collect(Collectors.joining("\n"));
    }
}
