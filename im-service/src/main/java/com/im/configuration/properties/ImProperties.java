package com.im.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dalong
 * @date 2022/4/12 18:37
 */
@Data
@ConfigurationProperties(prefix = "im")
public class ImProperties {

    private final Socket socket = new Socket();

    private final WebSocket webSocket = new WebSocket();


    @Data
    public static class Socket {
        private Integer port;
    }

    @Data
    public static class WebSocket {
        private Integer port;
    }

    public Integer getSocketPort() {
        return socket.port;
    }

    public Integer getWebSocketPort() {
        return webSocket.port;
    }
}
