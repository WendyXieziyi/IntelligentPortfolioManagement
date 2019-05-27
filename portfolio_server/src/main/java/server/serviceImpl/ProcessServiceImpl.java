package server.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.dao.ProcessRepository;
import server.entity.ProcessEntity;
import server.service.FileService;
import server.service.ProcessService;
import server.util.ResultMessage;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service(value = "processService")
public class ProcessServiceImpl implements ProcessService {

    final String processDir = "/Users/apple/Desktop/Portfolio/portfolio_server/src/main/resources/static/processes/";
    final String PGPortfolioPath = "/Users/apple/Desktop/Portfolio/portfolio_server/src/main/resources/static/PGPortfolio-master";

    @Autowired
    private ProcessRepository processRepository;
    @Resource(name = "fileService")
    private FileService fileService;

    @Override
    public ResultMessage createProcess(ProcessEntity processEntity) {
        String id = this.generateProcessId();
        processEntity.setId(id);
        processRepository.save(processEntity);
        if (this.initProcess(id) == ResultMessage.SUCCESS) {
            return ResultMessage.SUCCESS;
        } else {
            return ResultMessage.FAILED;
        }
    }

    @Override
    public String generateProcessId() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append((int) (Math.random() * 10));
        }
        return sb.toString();
    }

    @Override
    public ResultMessage initProcess(String processId){
        String destDirName = processDir + processId;
        File dir = new File(destDirName);
        //创建目录 拷贝PGPortfolio 装载数据集 设定参数
        if (dir.mkdirs()) {
            try {
                fileService.copyDir(PGPortfolioPath,destDirName);
                fileService.loadDataSet(destDirName+"/database");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ResultMessage.SUCCESS;
        } else {
            return ResultMessage.FAILED;
        }
    }



}
