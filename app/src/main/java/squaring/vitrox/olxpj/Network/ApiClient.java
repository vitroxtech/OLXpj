package squaring.vitrox.olxpj.Network;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;
import squaring.vitrox.olxpj.Helper.Config;

public class ApiClient {

    private static final String BASE_URL = "https://api.flickr.com/services/";
    private static Retrofit retrofit = null;
    private static final String NOJSONCB = "1";
    private static final String JSON = "json";

    /*
   RETROFIT CLIENT with already setup fix parameters
   */
    public static Retrofit getClient() {

        OkHttpClient httpClient =
                new OkHttpClient();
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.httpUrl();
                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("nojsoncallback", NOJSONCB)
                        .addQueryParameter("format", JSON)
                        .addQueryParameter("api_key", Config.API_KEY)
                        .build();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create(mapper))
                    .client(httpClient)
                    .build();
        }
        return retrofit;
    }
}