package edu.project3.log;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseCodes {
    private HttpResponseCodes() {
    }

    @SuppressWarnings("MagicNumber")
    private static final Map<Integer, String> MAP = new HashMap<>() {{
        put(100, "Continue");
        put(101, "Switching Protocols");
        put(200, "OK");
        put(201, "Created");
        put(202, "Accepted");
        put(203, "Non-Authoritative Information");
        put(204, "No Content");
        put(205, "Reset Content");
        put(206, "Partial Content");
        put(300, "Multiple Choices");
        put(302, "Found");
        put(303, "See Other");
        put(304, "Not Modified");
        put(305, "Use Proxy");
        put(400, "Bad Request");
        put(401, "Unauthorized");
        put(402, "Payment Required");
        put(403, "Forbidden");
        put(404, "Not Found");
        put(405, "Method Not Allowed");
        put(406, "Not Acceptable");
        put(407, "Proxy Authentication Required");
        put(408, "Request Timeout");
        put(409, "Conflict");
        put(410, "Gone");
        put(411, "Length Required");
        put(412, "Precondition Failed");
        put(413, "Request Entity Too Large");
        put(414, "Request-URI Too Long");
        put(415, "Unsupported Media Type");
        put(416, "Requested Range Not Satisfiable");
        put(417, "Expectation Failed");
        put(500, "Internal Server Error");
        put(501, "Not Implemented");
        put(502, "Bad Gateway");
        put(503, "Service Unavailable");
        put(504, "Gateway Timeout");
        put(505, "HTTP Version Not Supported");
    }};

    public static String getName(int code) {
        String result = MAP.get(code);
        if (result == null) {
            throw new IllegalLogException("Неизвестный код ответа HTTP");
        }
        return result;
    }

}
