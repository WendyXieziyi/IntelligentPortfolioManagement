package server.controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping(value = "/data")
@CrossOrigin
public class Data {
    private static final Logger LOGGER = LoggerFactory.getLogger(Data.class);

    @RequestMapping(value = "/uploadDataSet", method = RequestMethod.POST)
    public String upload(MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String fileName = file.getOriginalFilename();
        String filePath = "/Users/apple/Desktop/Portfolio/portfolio_server/src/main/resources/static/";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            LOGGER.info("upload success");
            return "upload success";
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
        }
        return "upload failed";
    }
}