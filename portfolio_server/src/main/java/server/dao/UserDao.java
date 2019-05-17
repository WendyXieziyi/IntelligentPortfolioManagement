package server.dao;

import server.util.ResultMessage;

public interface UserDao {
    /**
     * 根据用户名搜索用户
     * @param username
     * @return
     */
    public String findUserByUsername(String username);


    /**
     * 新增一个用户
     * @param username
     * @param password
     * @return
     */
    public ResultMessage addUser(String username,String password);

    /**
     * 用户是否存在
     * @param username
     * @return
     */
    public ResultMessage isUserExits(String username);

    /**
     * 创建用户目录
     * @param username
     * @return
     */
    public ResultMessage createUserDir(String username);
}
