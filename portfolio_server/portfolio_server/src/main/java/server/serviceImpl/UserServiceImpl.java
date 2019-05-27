package server.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.dao.UserRepository;
import server.entity.UserEntity;
import server.service.UserService;
import server.util.ResultMessage;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResultMessage login(String username, String password) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity!=null&&userEntity.getPassword().equals(password)){
            return ResultMessage.SUCCESS;
        }
        return ResultMessage.FAILED;
    }

    @Override
    public ResultMessage register(String username, String password) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity!=null){
            return ResultMessage.FAILED;
        }
        else{
            userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setPassword(password);
            userRepository.save(userEntity);
            return ResultMessage.SUCCESS;
        }
    }

    @Override
    public int getUserIdByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity!=null){
            return userEntity.getId();
        }
        return -1;
    }
}
