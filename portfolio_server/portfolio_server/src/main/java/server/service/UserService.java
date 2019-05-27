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

    /**
     * 根据用户名返回用户id
     * @param username
     * @return
     */
    public int getUserIdByUsername(String username);
}
