package CadrastroUsuario.Project.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

//
@RestControllerAdvice
public class GlobalExceptionHandler {


    //Qualquer exceção generica capturada vai retornar esse json completo.
    @ExceptionHandler(NotFoundUser.class)
    public ResponseEntity<Object> HandleRecursoNotFound(NotFoundUser ex){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Erro interno do servidor..");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
