package com.example.androidprotocolbuffers;

import java.io.InputStream;

/**
 * Interfaz que implementará las peticiones de un Request.
 */
public interface IRequest {
    /**
     * Hace referencia a la implementación de una petición GET.
     * @param inputStream Respuesta de la petición.
     */
    void GetRequest(InputStream inputStream);

    /**
     * Hace referencia a la implementación de una petición POST.
     * @param inputStream Respuesta de la petición.
     */
    void PostRequest(InputStream inputStream);
}
