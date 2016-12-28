import java.io.*;


import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.concurrent.Executors;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
/**
*   Don't forget to change properties files and change the relative path to properties files in JDBCConection class
*   http://localhost:8088/?a=andy
 */


import java.net.URI;

public class HttpServerDemo {

    public static void main(String[] args) throws IOException {
        InetSocketAddress addr = new InetSocketAddress(8088);
        HttpServer server = HttpServer.create(addr, 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        System.out.println("Server is listening on port 8088");
    }
}

class MyHandler implements HttpHandler {
    private String answer;

    public void handle(HttpExchange exchange) throws IOException {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "text/plain");


        URI requestedUri = exchange.getRequestURI();
        String query = requestedUri.getRawQuery();
        String param[] = null;
        if (query != null) {
            String pairs[] = query.split("[&]");
            for (String pair : pairs) {
                param = pair.split("[=]");
            }
        }
        int number = 0;

        Connection con = JDBCConnetion.getConnection();
        try {
            String queryDB = "select id from stud where name =" + "'" + param[1].trim().toLowerCase() + "'";

            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(queryDB);
            if (rs.next()) {
                number = rs.getInt("id");

                System.out.println("getID");
                System.out.println(number);
            }

            s.close();
            rs.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        answer = ""  + number;

//      byte[] bytes = intToBytesArray(number);

//      We don't send a String, we send a byte array
        OutputStream responseBody = exchange.getResponseBody();
        exchange.sendResponseHeaders(200,answer.length());
        responseBody.write(answer.getBytes());
        responseBody.close();


        //Send a photo to browser
//        File fi = new File("C:\\a.jpg");
//        System.out.println(fi.length());
//        exchange.sendResponseHeaders(200, fi.length());
//        //  Files.copy(fi.toPath(),responseBody);
//        //or
//        byte[] fileContent = Files.readAllBytes(fi.toPath());
//        responseBody.write(fileContent);
//        responseBody.close();


    }

    public static byte[] intToBytesArray(int number) {
        int i = 0;
        int length = (int) Math.log10(number);
        int divisor = (int) Math.pow(10, length);
        System.out.println(length);
        System.out.println(divisor);
        byte[] temp = new byte[length + 1];
        while (number != 0) {


            temp[i] = (byte) (number / divisor);

            if (i < length) {
                ++i;
            }
            number = number % divisor;
            if (i != 0) {
                divisor = divisor / 10;
            }
        }

        return temp;


    }

}
