package com.sang.common.exception;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * BaseException
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter
@Getter
public class BaseException extends Exception {
    private Integer code;
    private String message;
}
