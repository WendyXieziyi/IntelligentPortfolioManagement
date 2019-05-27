package server.service;

import org.springframework.web.multipart.MultipartFile;
import server.util.ResultMessage;

import java.io.IOException;

public interface FileService {

    /**
     * 上传数据集
     * @param file
     * @return
     */
    public ResultMessage uploadDataSet(MultipartFile file);

    /**
     * 装载数据集
     * @param destinationPath
     * @return
     */
    public ResultMessage loadDataSet(String destinationPath);

    /**
     * 拷贝python模型到任务目录
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
     * 删除文件夹下所有文件
     * @param path
     * @return
     */
    public ResultMessage deleteFile(String path);
}
