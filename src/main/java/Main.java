import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    //Наша ссылка, на которую будем отправлять запрос
    public static final String URI = "https://api.nasa.gov/planetary/apod?api_key=ahIAAEbbk2QJBmUQdPzn9R1axadqFLj9FUi8lMme";

    //Сущность, которая будет преобразовывать ответ в наш объект NASA
    public static final ObjectMapper mapper = new ObjectMapper();
    public static void main(String[] args) throws IOException {
        //настраиваем наш HTTP клиент, который будет отправлять запросы
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //отправляем запрос и получаем ответ
        CloseableHttpResponse response = httpClient.execute(new HttpGet(URI));
        NasaObject nasaObject = mapper.readValue(response.getEntity().getContent(),NasaObject.class);
        System.out.println(nasaObject);

        //отправляем запрос и получаем ответ с нашей картинкой
        CloseableHttpResponse pictureResponse = httpClient.execute(new HttpGet(nasaObject.getUrl()));

        //формируем автоматически название для файла
        String[] arr = nasaObject.getUrl().split("/");
        String fileName = arr[arr.length - 1];

        HttpEntity entity = pictureResponse.getEntity();

        //сохраняем в файл
        FileOutputStream fos = new FileOutputStream(fileName);
        entity.writeTo(fos);
        fos.close();
    }
}