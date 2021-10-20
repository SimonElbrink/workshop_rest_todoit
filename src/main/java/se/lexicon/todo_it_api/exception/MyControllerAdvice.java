package se.lexicon.todo_it_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import se.lexicon.todo_it_api.exception.AppResourceNotFoundException;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(AppResourceNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleAppResourceNotFoundException(AppResourceNotFoundException ex, WebRequest request){

        Map<String, Object> response = new HashMap<>();

        response.put("timestamp", LocalDateTime.now());
        response.put("value", HttpStatus.NOT_FOUND.value());
        response.put("name", HttpStatus.NOT_FOUND.name());
        response.put("message", ex.getMessage());

        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request){

        Map<String, String> violationList = new HashMap<>();
        for(FieldError err : ex.getBindingResult().getFieldErrors()){
            violationList.put(err.getField(), err.getDefaultMessage());
        }

        Map<String, Object> response = new HashMap<>();

        response.put("timestamp", LocalDateTime.now());
        response.put("value", HttpStatus.BAD_REQUEST.value());
        response.put("name", HttpStatus.BAD_REQUEST.name());
        response.put("message", ex.getMessage());
        response.put("violation", violationList);

        return response;

    }



}
