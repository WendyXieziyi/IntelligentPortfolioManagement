package server.controller;

import org.apache.tomcat.jni.Proc;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import server.entity.ProcessEntity;
import server.service.ProcessService;
import server.util.OptMessage;
import server.util.ResultMessage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping(value = "process")
@CrossOrigin
public class Process {
    @Resource(name = "processService")
    private ProcessService processService;

    @RequestMapping(value = "/createProcess",method = RequestMethod.POST)
    public void createProcess(@RequestBody ProcessEntity processEntity, HttpServletRequest request, HttpServletResponse response){
        OptMessage optMessage = new OptMessage();
        if(processService.createProcess(processEntity)== ResultMessage.SUCCESS){
            optMessage.setResult(true);
        }
        else{
            optMessage.setResult(false);
        }
        try (PrintWriter out = response.getWriter()){
            out.println(new JSONObject(optMessage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
