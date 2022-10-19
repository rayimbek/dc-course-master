package org.example;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.URL;


/**
 * Processor of HTTP request.
 */
public class Processor {
    private final Socket socket;
    private final HttpRequest request;

    public Processor(Socket socket, HttpRequest request) {
        this.socket = socket;
        this.request = request;
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public void process() throws IOException {
        // Print request that we received.
        System.out.println("Got request:");
        System.out.println(request.toString());
        System.out.flush();

        // To send response back to the client.
        PrintWriter output = new PrintWriter(socket.getOutputStream());
        // We are returning a simple web page now.

        String firstLine = request.toString().split("\n")[0];
        String resource = firstLine.split(" ")[1];
        System.out.println(resource);
        if(resource.equals("/hello")) {
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>Hello</title></head>");
            output.println("<body><p>Hello, world!</p></body>");
            output.println("</html>");
            output.flush();
        }else if(resource.equals("/fav")){
            FileInputStream image = new FileInputStream("C:\\Users\\Raikobek\\Downloads\\dc-course-master\\examples\\WebServer\\src\\main\\java\\org\\example\\fav.jpg");
            InputStreamReader input = new InputStreamReader(image);
            BufferedReader reader = new BufferedReader(input);
            File file = new File("C:\\Users\\Raikobek\\Downloads\\dc-course-master\\examples\\WebServer\\src\\main\\java\\org\\example\\fav.jpg");


            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><image link = "+file+"></image></head>");
            output.println("</html>");
            output.flush();
        }else if(resource.equals("/prime")){
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><p>Prime Numbers:</head>");
            output.println("<body><p>");
            for(int i = 1; i <= 120000; ++i){
                int d= 0;
                for(int j = 2; j < i; ++j){
                    if(i % j == 0){
                        d = 1;
                    }
                }
                if(d == 0){
                    output.println(i + " ");
                }
            }
            output.println("</p></body>");
            output.println("</html>");
            output.flush();
        }else if(resource.equals("/words")){
            File file = new File("C:\\Users\\Raikobek\\Downloads\\dc-course-master\\examples\\WebServer\\src\\main\\java\\org\\example\\words.txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            int wordCount = 0;
            int characterCount = 0;
            int paraCount = 0;
            int whiteSpaceCount = 0;
            int sentenceCount = 0;;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("")) {
                    paraCount += 1;
                }
                else {

                    characterCount += line.length();
                    String words[] = line.split("\\s+");
                    wordCount += words.length;
                    whiteSpaceCount += wordCount - 1;
                    String sentence[] = line.split("[!?.:]+");
                    sentenceCount += sentence.length;
                }
            }
            if (sentenceCount >= 1) {
                paraCount++;
            }
            System.out.println("Total word count = "+ wordCount);
            System.out.println("Total number of sentences = "+ sentenceCount);
            System.out.println("Number of paragraphs = "+ paraCount);
            System.out.println("Total number of whitespaces = "+ whiteSpaceCount);

            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>Words</title></head");
            output.println("<body><p>Total word count = "+wordCount+"<p><body>");
            output.println("<body><p>Total number of sentences = "+ sentenceCount +"<p><body>");
            output.println("<body><p>Total number of characters = "+ characterCount+"<p><body>");
            output.println("<body><p>Number of paragraphs ="+ paraCount+" <p><body>");
            output.println("</html>");
            output.flush();
        }else{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>DElay</title></head");
            output.println("<body><p>What are you trying to output<p><body>");
            output.println("</html>");
            output.flush();
        }
        socket.close();
    }
}
