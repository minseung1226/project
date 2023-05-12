package project.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.project.domain.User;
import project.project.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public boolean userJoin(String name,String email,String pw){
        User findUser = userRepository.findByEmail(email);
        if(findUser==null){
            userRepository.save(new User(email,name,pw));
            return true;
        }
        return false;
    }


}