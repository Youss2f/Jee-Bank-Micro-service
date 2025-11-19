package net.atertour.mcpserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class McpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(McpServerApplication.class, args);
        System.out.println("✅ MCP Server started successfully!");
        System.out.println("📡 Server URL: http://localhost:8989/mcp");
    }
}
