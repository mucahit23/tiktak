package com.tiktak.dto.response.base;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.ZonedDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class GenericResponse<T> implements Serializable {

    @Builder.Default
    private boolean success = Boolean.TRUE;

    private T content;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime zonedDateTime;

    public GenericResponse(boolean res, String message) {
    }
}
