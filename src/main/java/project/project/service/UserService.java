package project.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.project.domain.User;
import project.project.domain.enum_type.UserJoinType;
import project.project.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public boolean userJoin(String name, String email, String pw){
        Optional<User> findUser = userRepository.findByEmail(email);
        if(findUser.isEmpty()){
            userRepository.save(new User(email,name,pw));
            return true;
        }
        return false;
    }

    @Transactional
    public void pwChange(Long id,String pw){
        Optional<User> findUser = userRepository.findById(id);
        if(!findUser.isEmpty()){
            findUser.get().changePw(pw);
        }
    }



}
