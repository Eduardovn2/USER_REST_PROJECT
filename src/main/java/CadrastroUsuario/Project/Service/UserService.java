package CadrastroUsuario.Project.Service;

import CadrastroUsuario.Project.Entity.User;
import CadrastroUsuario.Project.Repository.UserRepository;
import org.aspectj.weaver.ast.Var;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    public UserRepository userRepository;

    //Injeção de dependencia jpa.
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User create(User user){
        return userRepository.save(user);
    }

    public List<User> list(){
        Sort sort = Sort.by("name").ascending();

        return userRepository.findAll(sort);
    }

    //ResponseEntity para retorna o padrao http.
    public ResponseEntity<User> FindByUser(Long id){
        //Optional user, requisitado pelo findById.
        Optional<User> userSearch = userRepository.findById(id);
        return ResponseEntity.of(userSearch);
    }

    public ResponseEntity<User> replace(Long id, User userDetails){

        User userExisting = userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Usuario com o id " + id + "nao foi encontrado."));

        userExisting.setName(userDetails.getName());
        userExisting.setEmail(userDetails.getEmail());

        userRepository.save(userExisting);
        return ResponseEntity.ok(userExisting);
    }

    public List<User> delete(Long id){
        userRepository.deleteById(id);
        return list();
    }




}
