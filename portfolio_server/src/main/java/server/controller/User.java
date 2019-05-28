package server.controller;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import server.service.UserService;
import server.util.OptMessage;
import server.util.ResultMessage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping(value = "user")
@CrossOrigin
public class User {
    @Resource(name = "userService")
    private UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public void login(@RequestParam String username, @RequestParam String password, HttpServletRequest request, HttpServletResponse response){
        OptMessage optMessage = new OptMessage();
        if(userService.login(username,password)== ResultMessage.SUCCESS){
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

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public void register(@RequestParam String username, @RequestParam String password, HttpServletRequest request, HttpServletResponse response){
        OptMessage optMessage = new OptMessage();
        if(userService.register(username,password)== ResultMessage.SUCCESS){
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

    @RequestMapping(value = "/usernameToId",method = RequestMethod.GET)
    public void usernameToId(@RequestParam String username, HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",userService.getUserIdByUsername(username));
        try (PrintWriter out = response.getWriter()){
            out.println(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
