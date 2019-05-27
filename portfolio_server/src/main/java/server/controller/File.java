package server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.service.FileService;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/file")
@CrossOrigin
public class File {
    @Resource(name = "fileService")
    private FileService fileService;

    @RequestMapping(value = "/uploadDataSet", method = RequestMethod.POST)
    public void upload(MultipartFile file) {
        fileService.uploadDataSet(file);
    }
}