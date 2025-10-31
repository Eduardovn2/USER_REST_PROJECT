package CadrastroUsuario.Project.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//ResponseStatus em caso do status do https ser Not Found 405.
@ResponseStatus(HttpStatus.NOT_FOUND)

public class NotFoundUser extends RuntimeException{

    //Message padrao.
    public NotFoundUser(Long id){
        super("O usuario com o ID " + id + " nao foi encontrado.");
    }

    public NotFoundUser(String message){
        super(message);
    }


}
