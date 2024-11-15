package com.alejandro.literalura.Implementations;

import com.alejandro.literalura.Exceptions.HttpRequestException;
import com.alejandro.literalura.Exceptions.ParseJsonException;
import com.alejandro.literalura.Services.APIService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class APIServiceImpl implements APIService {

    @Value("${domain}")
    private String domain;

    private final HttpClient client;

    private HttpRequest requestInfo(String paramURL) throws HttpRequestException {
        Optional.ofNullable(paramURL).orElseThrow(
                () -> new HttpRequestException("Parámetro de URL vacio, intente de nuevo")
        );
        String param = paramURL.toLowerCase().replaceAll(" ", "%20").trim();
        String requestUrl = domain + "?search=" + param;
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(requestUrl))
                .build();
        return request;
    }

    @Override
    public String httpResponse(String paramURL) throws NullPointerException, IOException, InterruptedException, ParseJsonException, HttpRequestException {
        try {
            HttpRequest request = requestInfo(paramURL);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        }
        catch (IOException | InterruptedException e){
            throw new ParseJsonException("Algo ha salido mal en la obtención de información de la API", e.getCause());
        }
        catch (HttpRequestException e){
            throw new HttpRequestException("No se puede procesar la URL debido a un error de sintaxis", e.getCause());
        }
    }
}
