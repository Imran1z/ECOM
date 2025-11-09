package com.ecom.exception;

import com.ecom.payload.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String,String> response=new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err->{
            String fieldName=((FieldError)err).getField();
            String message =err.getDefaultMessage();
            response.put(fieldName,message);

        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> myResourceNotFoundException(ResourceNotFoundException e){
    APIResponse apiResponse =new APIResponse(e.getMessage(),false);
    return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<APIResponse> myApiException(ApiException e){
        APIResponse apiResponse =new APIResponse(e.getMessage(),false);
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }

}
