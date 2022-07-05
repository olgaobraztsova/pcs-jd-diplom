import com.google.gson.*;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        //System.out.println(listToJsonString(engine.search("бизнес")));


        // здесь создайте сервер, который отвечал бы на нужные запросы
        // слушать он должен порт 8989
        // отвечать на запросы /{word} -> возвращённое значение метода search(word) в JSON-формате

        try (ServerSocket serverSocket = new ServerSocket(8989);) { // стартуем сервер один(!) раз
            while (true) { // в цикле(!) принимаем подключения
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                ) {
                    // принимаем запрос от клиента
                    String request = in.readLine();

                    // отправляем ответ в виде json String
                    out.println(listToJsonString(engine.search(request)));
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }

    }


        // функция для сохранения списка в String в формате json
        public static String listToJsonString(List<PageEntry> entries) {
            Type listType = new TypeToken<List<PageEntry>>() {
            }.getType();
            GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
            Gson gson = builder.create();

           return gson.toJson(entries, listType);
        }
}