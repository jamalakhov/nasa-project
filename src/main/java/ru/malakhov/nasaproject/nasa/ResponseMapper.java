package ru.malakhov.nasaproject.nasa;

import java.io.IOException;
import java.io.InputStream;

public interface ResponseMapper<T>{

    T mapping(InputStream inputStream) throws IOException;
}
