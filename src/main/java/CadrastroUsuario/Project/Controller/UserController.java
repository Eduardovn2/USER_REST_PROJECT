package CadrastroUsuario.Project.Controller;

import CadrastroUsuario.Project.Entity.User;
import CadrastroUsuario.Project.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Register")
public class UserController {

    public UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    User create(@RequestBody User user){
        return userService.create(user);
    }

    @GetMapping
    List<User> allList(){
        return userService.list();
    }

    @GetMapping("/{id}")
    ResponseEntity<User> findByUser(@PathVariable Long id){
        return  userService.FindByUser(id);

    }

    @PutMapping("/{id}")
    ResponseEntity<User> replace(
            @PathVariable Long id,
            @RequestBody User userDetails)
    {
        User userUpdate = userService.replace(id, userDetails).getBody();


        return ResponseEntity.ok(userUpdate);
    }

    @DeleteMapping("{id}")
    List<User> delete(@PathVariable("id") Long id){
        return userService.delete(id);
    }





}
