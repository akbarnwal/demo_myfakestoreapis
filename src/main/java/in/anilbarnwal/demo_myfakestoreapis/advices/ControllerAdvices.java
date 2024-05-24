package in.anilbarnwal.demo_myfakestoreapis.advices;

import in.anilbarnwal.demo_myfakestoreapis.dtos.ErrorDto;
import in.anilbarnwal.demo_myfakestoreapis.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvices {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundException(ProductNotFoundException e) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }
}
