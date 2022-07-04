package com.sang.common.exception;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 业务异常
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class BusinessException extends BaseException{
}
