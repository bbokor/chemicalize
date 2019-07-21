package com.chemaxon.chemicalize.exceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class ExceptionDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() == HttpStatus.BAD_REQUEST.value()) {
            try {
                String errorJson = IOUtils.toString(response.body().asInputStream());
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(errorJson, ErrorResponse.class);
            } catch (IOException e) {
                log.error("Exception decoding error", e);
                ErrorResponse err = new ErrorResponse();
                err.setMessage("Unknown error");
                return err;
            }
        }
        return new Exception(response.reason());
    }

}
