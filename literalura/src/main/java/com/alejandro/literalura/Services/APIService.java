package com.alejandro.literalura.Services;


import com.alejandro.literalura.Exceptions.HttpRequestException;
import com.alejandro.literalura.Exceptions.ParseJsonException;

import java.io.IOException;

public interface APIService {

    String httpResponse(String paramURL) throws NullPointerException, IOException, InterruptedException, ParseJsonException, HttpRequestException;
}
