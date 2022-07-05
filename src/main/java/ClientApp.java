import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String host = "netology.homework";
        int port = 8989;

        try (Socket socket = new Socket(host, port); // ждем подключения
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {

            String response = "";

            System.out.println("Введите запрос для поиска\n>>");

            String input = scanner.nextLine();
            // отправляем серверу запрос на поиск
            out.println(input);
            // считываем ответ сервера
            while((response = in.readLine()) != null){
                System.out.println(response);
            }


        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
