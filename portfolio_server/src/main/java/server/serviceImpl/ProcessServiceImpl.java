package server.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.dao.ProcessRepository;
import server.entity.ProcessEntity;
import server.service.FileService;
import server.service.ProcessService;
import server.util.ResultMessage;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service(value = "processService")
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private ProcessRepository processRepository;

    final String processDir = "src\\main\\resources\\static\\processes\\";
    final String commandDir = "src\\main\\resources\\static\\command.sh ";

    @Resource(name = "fileService")
    private FileService fileService;

    @Override
    public ResultMessage createProcess(ProcessEntity processEntity) {
        processEntity.setStatus(0);
        processRepository.save(processEntity);
        if (this.initProcess(processEntity.getId()) == ResultMessage.SUCCESS) {
            return ResultMessage.SUCCESS;
        } else {
            return ResultMessage.FAILED;
        }
    }

    @Override
    public ResultMessage initProcess(String processId) {
        String destDirName = processDir + processId+"\\PGPortfolio-master";
        File dir = new File(destDirName);
        //创建目录 拷贝PGPortfolio 装载数据集 设定参数
        if (dir.mkdirs()) {
            fileService.copyPGPortfolio(destDirName);
            fileService.loadDataSet(destDirName + "\\database");
            fileService.setParameters(processId);
            return ResultMessage.SUCCESS;
        } else {
            return ResultMessage.FAILED;
        }
    }

    @Override
    public List<ProcessEntity> searchProcesses(int userId) {
        return processRepository.findAllByUserId(userId);
    }

    @Override
    public ResultMessage runProcess(String processId){
        // TODO Auto-generated method stub
        try {
            Process process = Runtime.getRuntime().exec("chmod 755 /Users/apple/Desktop/Portfolio/portfolio_server/src/main/resources/static/command.sh");
            //process.waitFor();
            //sh和param1之间要有空格
            //多个参数可以在param1后面继续增加，但不要忘记空格！！
            process = Runtime.getRuntime().exec(new String[]{"\\bin\\sh","-c",commandDir+processDir+processId+"\\PGPortfolio-master"});
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = null;
            StringBuffer sb = new StringBuffer("");
            while((line=br.readLine()) != null){
                sb.append(line);
            }
            br.close();
            System.out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProcessEntity processEntity  = processRepository.findById(processId);
        processEntity.setStatus(1);
        return ResultMessage.SUCCESS;
    }

}
