package com.restapi.domain;

import lombok.Builder;
import lombok.Data;

/**
 * ResponseDto is data transfer object for representing ResponseEntity class to use in application
 *
 * @author Duygu Muslu
 * @since  2020-06-17
 * @version 1.0
 */

@Data
@Builder
public class ResponseDto<T> {
    private String status;

    @Builder.Default
    private String message = "Success";

    private T body;
}