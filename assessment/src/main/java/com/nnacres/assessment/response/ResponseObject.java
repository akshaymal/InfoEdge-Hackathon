package com.nnacres.assessment.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nnacres.assessment.enums.ErrorCategory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;



@Getter
@Setter
@ToString(doNotUseGetters = true)
public class ResponseObject<T> {

    private T responseObject;

    private ErrorCategory status;

    private String errorMessage;

    @JsonIgnore
    private HttpStatus statusCode;

    public ResponseObject() {
        this.statusCode = HttpStatus.OK;
    }

    public ResponseObject(T responseObject) {
        this.responseObject = responseObject;
        this.statusCode = HttpStatus.OK;
    }

    public ResponseObject(ErrorCategory status) {
        this();
        this.status = status;
    }
}
