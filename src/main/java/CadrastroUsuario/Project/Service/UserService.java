package CadrastroUsuario.Project.Service;

import CadrastroUsuario.Project.Entity.User;
import CadrastroUsuario.Project.Exception.NotFoundUser;
import CadrastroUsuario.Project.Repository.UserRepository;
import org.aspectj.weaver.ast.Not;
import org.aspectj.weaver.ast.Var;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    //Injeção de dependencia jpa.
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }



    public User create(String username, String password, String email){
        String hashedPassowrd = passwordEncoder.encode(password);
        User user = new User(username, hashedPassowrd, email);
        return userRepository.save(user);
    }

    public List<User> list(){
        Sort sort = Sort.by("name").ascending();

        return userRepository.findAll(sort);
    }

    //ResponseEntity para retorna o padrao http.
    public Optional<User> FindByUserId(Long id){
        //Optional user, requisitado pelo findById.
        return userRepository.findById(id);
    }

    public Optional<User> findByUserName(String username){
        return userRepository.findByUsername(username);
    }

    public User replace(Long id, User userDetails){

        User userExisting = userRepository.findById(id).orElseThrow(() -> new NotFoundUser(id));

        userExisting.setUsername(userDetails.getUsername());
        userExisting.setEmail(userDetails.getEmail());

        return userRepository.save(userExisting);
    }

    public List<User> delete(Long id){
        if(!userRepository.existsById(id)){
           throw new NotFoundUser("Usuario com o id " + id + " nao foi encontrado.");
        }
        userRepository.deleteById(id);
        return list();
    }




}
