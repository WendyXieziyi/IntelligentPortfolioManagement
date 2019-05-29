package server.service;

import server.entity.ProcessEntity;
import server.util.ResultMessage;

import java.io.IOException;
import java.util.List;

public interface ProcessService {

    /**
     * 新建一个任务
     * @param processEntity
     * @return
     */
    public ResultMessage createProcess(ProcessEntity processEntity);

    /**
     * 初始化任务目录
     * @param processId
     * @return
     */
    public ResultMessage initProcess(String processId);

    /**
     * 根据用户id查找所有任务
     * @param userId
     * @return
     */
    public List<ProcessEntity> searchProcesses(int userId);

    /**
     * 运行进程
     * @param processId
     * @return
     */
    public ResultMessage runProcess(String processId) throws IOException, InterruptedException;


}
