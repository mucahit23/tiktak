package com.tiktak.util;

import com.tiktak.dto.response.base.GenericResponse;
import lombok.experimental.UtilityClass;

import java.time.ZonedDateTime;
import java.util.Objects;

@UtilityClass
public class TiktakUtils {

    private final static int MAX_EXCEPTION_LENGTH = 4096;

    public static <T> GenericResponse<T> generateGenericResponse(boolean result, T content) {
        return GenericResponse.<T>builder()
                .success(result)
                .content(content)
                .zonedDateTime(ZonedDateTime.now())
                .build();
    }

    public static String getFormattedMessage(String message) {
        message = Objects.nonNull(message) ? message.trim() : "";
        return message.length() > MAX_EXCEPTION_LENGTH ? message.substring(4096) : message;
    }

}
