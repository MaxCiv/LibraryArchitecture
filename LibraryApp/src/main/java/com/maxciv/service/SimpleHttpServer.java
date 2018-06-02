package com.maxciv.service;

import com.maxciv.businesslogic.exceptions.NotFoundException;
import com.maxciv.gui.facades.ServiceFacade;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 * Run from console by "java -cp maxciv-1.0-all.jar com.maxciv.service.SimpleHttpServer".
 * Go to "http://localhost:8000/get" in your browser.
 */
public class SimpleHttpServer {

    private static HttpServer server;
    private static ServiceFacade facade = new ServiceFacade();

    public static void main(String[] args) {
        start();
    }

    private static void start() {
        try {
            server = HttpServer.create();
            server.bind(new InetSocketAddress(8000), 0);
            server.createContext("/get", new EchoHandler());    // http://localhost:8000/get
            server.setExecutor(null);
            server.start();
            System.out.println("Server started!");
        } catch (IOException ex) {
            System.out.println("Server not started");
        }
    }

    static class EchoHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            StringBuilder builder = new StringBuilder();
            try {
                builder.append(facade.getAllBooksAsJson());
            } catch (NotFoundException e) {
                e.printStackTrace();
                return;
            }

            String answer = new String(builder.toString().getBytes());
            byte[] bytes = answer.getBytes();

            exchange.sendResponseHeaders(200, bytes.length);

            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(bytes);
            outputStream.close();
        }
    }
}
