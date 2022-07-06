package com.sang.common.exception;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * BaseException
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BaseException extends Exception {
    protected Integer code;
    protected String message;
}
