package CadrastroUsuario.Project.Service;

import CadrastroUsuario.Project.Entity.User;
import CadrastroUsuario.Project.Repository.UserRepository;
import org.springframework.stereotype.Service;

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
        return userRepository.findAll();
    }

    public Optional<User> FindByUser(Long id){
        return userRepository.findById(id);
    }

    public User replace(){
    }




}
