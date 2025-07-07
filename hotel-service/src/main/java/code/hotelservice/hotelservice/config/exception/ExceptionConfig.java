package code.hotelservice.hotelservice.config.exception;

import code.hotelservice.hotelservice.dto.exception.ExceptionDto;
import code.hotelservice.hotelservice.service.exception.BundleMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ExceptionDto> response(RuntimeException runtimeException){
        return ResponseEntity.ok(new ExceptionDto(HttpStatus.NOT_ACCEPTABLE,BundleMessageService.getMessage(runtimeException.getMessage())));
    }

    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<ExceptionDto> response(BadCredentialsException badCredentialsException){
        return ResponseEntity.ok(new ExceptionDto(HttpStatus.FORBIDDEN,BundleMessageService.getMessage(badCredentialsException.getMessage())));
    }
}
