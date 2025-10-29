package CadrastroUsuario.Project.Service;

import CadrastroUsuario.Project.Entity.User;
import CadrastroUsuario.Project.Repository.UserRepository;
import org.springframework.data.domain.Sort;
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
        Sort sort = Sort.by("prioridade").descending().and(
                Sort.by("name").ascending()
        );

        return userRepository.findAll(sort);
    }

    public Optional<User> FindByUser(Long id){
        return userRepository.findById(id);
    }

    public List<User> replace(User user){
        userRepository.save(user);
        return list();
    }

    public List<User> delete(Long id){
        userRepository.deleteById(id);
        return list();
    }




}
