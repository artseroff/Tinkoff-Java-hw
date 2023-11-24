package edu.hw6.task3;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.Arrays;

public class MagicNumberFilter implements AbstractPathFilter {
    private final String[] hexBytes;

    public MagicNumberFilter(int... parameters) {
        hexBytes = new String[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            hexBytes[i] = Integer.toHexString(parameters[i]);
        }
    }

    @Override
    public boolean accept(Path entry) throws IOException {
        int bufferSize = hexBytes.length;
        try (FileChannel fileChannel = FileChannel.open(entry)) {
            ByteBuffer buffer = ByteBuffer.allocate(bufferSize);

            int countReadBytes = fileChannel.read(buffer);

            if (countReadBytes == -1) {
                return false;
            }
            buffer.flip();

            String[] readHexBytes = new String[bufferSize];
            for (int i = 0; i < bufferSize; i++) {
                readHexBytes[i] = "%02x".formatted(buffer.get());
            }

            return Arrays.equals(readHexBytes, hexBytes);

        }
    }
}
