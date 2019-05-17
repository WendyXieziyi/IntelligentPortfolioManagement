package server.daoImpl;

import server.dao.UserDao;
import server.util.ResultMessage;

import java.io.*;

public class UserDaoImpl implements UserDao {
    final String userData = "/Users/apple/Desktop/Portfolio/portfolio_server/src/main/resources/static/user.txt";
    final String userDir = "/Users/apple/Desktop/Portfolio/portfolio_server/src/main/resources/static/user/";

    @Override
    public String findUserByUsername(String username) {
        String item = "";
        File fs = new File(userData);
        FileReader fr = null;
        try {
            fr = new FileReader(fs);
            BufferedReader br = new BufferedReader(fr);
            while ((item=br.readLine() )!= null) {
                if(item.split(",")[0].equals(username)){
                    return item;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public ResultMessage addUser(String username, String password) {
        String item = username + "," + password;
        File f=new File(userData);
        FileWriter fw= null;
        try {
            fw = new FileWriter(f,true);
            fw.write(item+"\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage isUserExits(String username) {
        String item = "";
        File fs = new File(userData);
        FileReader fr = null;
        try {
            fr = new FileReader(fs);
            BufferedReader br = new BufferedReader(fr);
            while ((item=br.readLine() )!= null) {
                if(item.split(",")[0].equals(username)){
                    return ResultMessage.EXIST;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultMessage.NOT_EXIST;
    }

    @Override
    public ResultMessage createUserDir(String username) {
        String destDirName = userDir+username;
        File dir = new File(destDirName);
        //创建目录
        if (dir.mkdirs()) {
            return ResultMessage.SUCCESS;
        } else {
            return ResultMessage.FAILED;
        }
    }
}
