package server.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.service.FileService;
import server.util.ResultMessage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service(value = "fileService")
public class FileServiceImpl implements FileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(server.controller.File.class);
    private String dataSetPath = "/Users/apple/Desktop/Portfolio/portfolio_server/src/main/resources/static/database/";

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
            copyDir(dataSetPath,destinationPath);
            deleteFile(dataSetPath);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }


    @Override
    public ResultMessage copyDir(String sourcePath, String destinationPath) throws IOException {
        File file = new File(sourcePath);
        String[] filePath = file.list();

        if (!(new File(destinationPath)).exists()) {
            (new File(destinationPath)).mkdir();
        }

        for (int i = 0; i < filePath.length; i++) {
            if ((new File(sourcePath + file.separator + filePath[i])).isDirectory()) {
                copyDir(sourcePath + file.separator + filePath[i], destinationPath + file.separator + filePath[i]);
            }

            if (new File(sourcePath + file.separator + filePath[i]).isFile()) {
                copyFile(sourcePath + file.separator + filePath[i], destinationPath + file.separator + filePath[i]);
            }

        }
        return ResultMessage.SUCCESS;
    }

    @Override
    public void copyFile(String oldPath, String newPath) throws IOException {
        File oldFile = new File(oldPath);
        File file = new File(newPath);
        FileInputStream in = new FileInputStream(oldFile);
        FileOutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[2097152];
        int readByte = 0;
        while ((readByte = in.read(buffer)) != -1) {
            out.write(buffer, 0, readByte);
        }
        in.close();
        out.close();
    }

    @Override
    public ResultMessage deleteFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return ResultMessage.FAILED;
        }
        if (!file.isDirectory()) {
            return ResultMessage.FAILED;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                deleteFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
            }
        }
        return ResultMessage.SUCCESS;
    }
}
