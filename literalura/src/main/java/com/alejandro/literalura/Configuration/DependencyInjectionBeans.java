package com.alejandro.literalura.Configuration;

import com.alejandro.literalura.Implementations.AppMenu;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Scanner;

@Configuration
public class DependencyInjectionBeans{

    private final Scanner entry = new Scanner(System.in).useDelimiter("\n");

    @Bean
    public HttpClient httpClient(){
        return HttpClient
                .newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
    }

    @Bean
    public Scanner scanner(){
        return entry;
    }

    @PreDestroy
    public void closeScanner(){
        if(entry != null){
            entry.close();
        }
        System.out.println("Finalizado recursos de scanner");
    }
}