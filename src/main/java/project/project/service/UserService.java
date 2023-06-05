package project.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import project.project.domain.User;
import project.project.domain.embeded.Address;
import project.project.domain.enum_type.UserJoinType;
import project.project.file.UploadFile;
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

    @Transactional
    public void profileModify(Long id,String name, String tel, String email, MultipartFile file,Integer postcode,
                              String address,String detailAddress,String extraAddress){
        Optional<User> findUser = userRepository.findById(id);
        if(findUser.isEmpty()){

        }
        User user = findUser.get();
        String pimg="";
        Address newAddress = null;
        if(!file.getOriginalFilename().isEmpty()){
            UploadFile uploadFile=new UploadFile(file.getOriginalFilename());
            uploadFile.fileUpload(file);
            pimg=uploadFile.getStoreName();
        }

        if((StringUtils.hasText(address)&&StringUtils.hasText(extraAddress)&&postcode!=null)){
            newAddress=new Address(postcode,address,detailAddress,extraAddress);
        }

        user.modifyUser(name,tel,email,pimg,newAddress);

    }



}
