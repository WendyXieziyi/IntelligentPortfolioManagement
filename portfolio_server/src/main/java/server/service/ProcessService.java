package server.service;

import server.entity.ProcessEntity;
import server.util.ResultMessage;

import java.io.IOException;

public interface ProcessService {

    /**
     * 新建一个任务
     * @param processEntity
     * @return
     */
    public ResultMessage createProcess(ProcessEntity processEntity);

    /**
     * 生成任务id
     * @return
     */
    public String generateProcessId();

    /**
     * 初始化任务目录
     * @param processId
     * @return
     */
    public ResultMessage initProcess(String processId);



}
