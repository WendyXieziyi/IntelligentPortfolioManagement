package server.dao;

import server.util.ResultMessage;

import java.io.IOException;
import java.util.ArrayList;

public interface FileDao {
    /**
     * 拷贝文件夹
     * @param sourcePath
     * @param destinationPath
     * @return
     * @throws IOException
     */
    public ResultMessage copyDir(String sourcePath, String destinationPath) throws IOException;

    /**
     * 拷贝文件
     * @param oldPath
     * @param newPath
     * @throws IOException
     */
    public void copyFile(String oldPath, String newPath) throws IOException;

    /**
     * 删除文件夹下所有文件(如果有文件夹会删掉文件夹里的文件)
     * @param path
     * @return
     */
    public ResultMessage deleteFile(String path);

    /**
     * 读取json文件
     * @param filePath
     * @return
     */
    public String readJsonFile(String filePath);

    /**
     * 写入json文件
     * @param s
     * @param filePath
     * @return
     */
    public ResultMessage writeJsonFile(String s, String filePath);

    /**
     * 按行读取文件
     * @param path
     * @return
     */
    public ArrayList<String> readFileByLine(String path);
}
