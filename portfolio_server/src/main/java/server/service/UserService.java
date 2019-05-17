package server.service;

import server.util.ResultMessage;

public interface UserService {
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public ResultMessage login(String username,String password);

    /**
     * 注册
     * @param username
     * @param password
     * @return
     */
    public ResultMessage register(String username,String password);
}
