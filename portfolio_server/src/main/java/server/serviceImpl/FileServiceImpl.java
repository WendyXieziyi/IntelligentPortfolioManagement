package server.serviceImpl;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.dao.FileDao;
import server.dao.ProcessRepository;
import server.daoImpl.FileDaoImpl;
import server.entity.ProcessEntity;
import server.service.FileService;
import server.util.ResultMessage;

import java.io.File;
import java.io.IOException;

@Service(value = "fileService")
public class FileServiceImpl implements FileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(server.controller.File.class);
    private String dataSetPath = "src\\main\\resources\\static\\database\\";
    private final String PGPortfolioPath = "src\\main\\resources\\static\\PGPortfolio-master";
    private final String configPath = "src\\main\\resources\\static\\processes\\";
    @Autowired
    private ProcessRepository processRepository;

    private FileDao fileDaoImpl = new FileDaoImpl();

    @Override
    public ResultMessage uploadDataSet(MultipartFile file) {
        if (file.isEmpty()) {
            return ResultMessage.FAILED;
        }
        String fileName = file.getOriginalFilename();
        File dest = new File(dataSetPath + fileName);
        try {
            file.transferTo(dest);
            LOGGER.info("upload success");
            return ResultMessage.SUCCESS;
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
        }
        return ResultMessage.FAILED;
    }

    @Override
    public ResultMessage loadDataSet(String destinationPath) {
        try {
            fileDaoImpl.copyDir(dataSetPath,destinationPath);
            fileDaoImpl.deleteFile(dataSetPath);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage copyPGPortfolio(String destinationPath) {
        try {
            fileDaoImpl.copyDir(PGPortfolioPath,destinationPath);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage setParameters(String processId) {
        ProcessEntity processEntity = processRepository.findById(processId);
        String startDate = processEntity.getStartDate();
        String endDate = processEntity.getEndDate();
        int steps = processEntity.getSteps();
        String path = configPath+processId+"\\PGPortfolio-master\\train_package\\1\\net_config.json";
        String s = fileDaoImpl.readJsonFile(path);
        JSONObject config = new JSONObject(s);
        JSONObject input = config.getJSONObject("input"); //json对象里获得嵌套的json对象
        JSONObject training = config.getJSONObject("training");
        input.put("start_date",startDate);
        input.put("end_date",endDate);
        training.put("steps",steps);
        //System.out.println(input.get("coin_number")); //get方法获得对应的value
        fileDaoImpl.writeJsonFile(config.toString(),path);
        return ResultMessage.SUCCESS;
    }


}
