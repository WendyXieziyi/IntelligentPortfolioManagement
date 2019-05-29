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
     * 拷贝Python模型文件夹
     * @param destinationPath
     * @return
     */
    public ResultMessage copyPGPortfolio(String destinationPath);

    /**
     * 设定net-config参数
     * @param processId
     * @return
     */
    public ResultMessage setParameters(String processId);

}
