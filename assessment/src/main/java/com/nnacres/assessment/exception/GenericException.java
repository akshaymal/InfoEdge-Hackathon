package com.nnacres.assessment.exception;

import com.nnacres.assessment.enums.ErrorCategory;
import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * @author anand
 * @since 0.0.1
 */
@Getter
public class GenericException extends Exception {

    private String errorCode;

    public GenericException(ErrorCategory errorCategory){
        super(errorCategory.getMessage());
        this.errorCode = errorCategory.getCode();
    }

    public GenericException(ErrorCategory errorCategory, @NotNull String message) {
        super(message);
        this.errorCode = errorCategory.getCode();
    }
}
