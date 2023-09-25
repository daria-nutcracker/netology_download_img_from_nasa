import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class Utils {
    public static final ObjectMapper mapper = new ObjectMapper();

    //настраиваем наш HTTP клиент, который будет отправлять запросы
    private static CloseableHttpClient httpClient = HttpClients.createDefault();

    public static String getUrl (String uri) throws IOException {
        CloseableHttpResponse response = httpClient.execute(new HttpGet((uri)));
        NasaObject nasaObject = mapper.readValue(response.getEntity().getContent(),NasaObject.class);
        return nasaObject.getUrl();

    }
}
