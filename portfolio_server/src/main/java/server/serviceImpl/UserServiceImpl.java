package server.serviceImpl;

import org.springframework.stereotype.Service;
import server.dao.UserDao;
import server.daoImpl.UserDaoImpl;
import server.service.UserService;
import server.util.ResultMessage;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public ResultMessage login(String username, String password) {
        String item = userDao.findUserByUsername(username);
        if(item==null){
            return ResultMessage.NOT_EXIST;
        }
        else if(item.split(",")[1].equals(password)){
            return ResultMessage.SUCCESS;
        }
        else{
            return ResultMessage.FAILED;
        }
    }

    @Override
    public ResultMessage register(String username, String password) {
        if(userDao.isUserExits(username)==ResultMessage.EXIST){
            return ResultMessage.EXIST;
        }
        else{
            userDao.addUser(username,password);
            userDao.createUserDir(username);
            return ResultMessage.SUCCESS;
        }
    }
}
