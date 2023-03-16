package ru.malakhov.nasaproject.nasa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class NasaResponseMapper implements ResponseMapper<NasaResponseObject> {
    private ObjectMapper mapper;

    public void init() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public NasaResponseObject mapping(InputStream inputStream) throws IOException {
        return mapper.readValue(inputStream, NasaResponseObject.class);
    }
}