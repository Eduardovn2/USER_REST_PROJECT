package CadrastroUsuario.Project.Controller;

import CadrastroUsuario.Project.Entity.User;
import CadrastroUsuario.Project.Repository.UserRepository;
import CadrastroUsuario.Project.Service.UserService;
import Security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Map;
import java.util.Optional;

/*CRUD User, Functions:
* /Client/create - cria um usuario utilizando um ID como primary key gerada automatica, e utilizando um 'name' e 'email' inserido pelo usuario.
* /Client/list - retorna a lista de todos usuarios e suas informações inseridas.
* /Client/findby{id} - Função que retorna um usuario de acordo com seu id, pensando em um escalonamento para futuras requisições.
* /Client/replace{id} - Atualiza um usuario buscando pelo id e informando suas novas credencias
* /Client/delete{id} - Delete o usuario utilizando seu id, feito para utilidade de administridadores e deletação de sua propria conta.*/

@RestController
@RequestMapping("/Client")
public class UserController {

    public UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/create")
    User create(@RequestBody Map<String, String> request){
        return userService.create(request.get("username"), request.get("password"), request.get("email"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        Optional<User> usuario = userService.findByUserName(request.get("username"));
        if (usuario.isPresent() && usuario.get().getPassword().equals(request.get("password"))) {
            String token = JwtUtil.generateToken(usuario.get().getUsername());
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(401).body("Credenciais inválidas");
    }

    @GetMapping("/list")
    List<User> allList(){
        return userService.list();
    }

    @GetMapping("/findBy{id}")
   Optional<User> findByUser(@PathVariable Long id){
        return userService.FindByUserId(id);
    }

    @PutMapping("/replace{id}")
    ResponseEntity<User> replace(
            @PathVariable Long id,
            @RequestBody User userDetails)
    {
        User userUpdate = userService.replace(id, userDetails);


        return ResponseEntity.ok(userUpdate);
    }

    @DeleteMapping("/delete{id}")
    List<User> delete(@PathVariable("id") Long id){
        return userService.delete(id);
    }





}
