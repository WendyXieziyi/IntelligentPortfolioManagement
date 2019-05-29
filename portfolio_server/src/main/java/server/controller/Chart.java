package server.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import server.service.ChartService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping(value = "/chart")
@CrossOrigin
public class Chart {
    @Resource(name = "chartService")
    private ChartService chartService;

    @RequestMapping(value = "/getPropertyData",method = RequestMethod.GET)
    public void getPropertyData(@RequestParam String processId, HttpServletRequest request, HttpServletResponse response){
        List<JSONArray> propertyChartData = chartService.getPropertyChartData(processId);
        try (PrintWriter out = response.getWriter()){
            out.println(new JSONArray(propertyChartData));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/getRateData",method = RequestMethod.GET)
    public void getRateData(@RequestParam String processId, HttpServletRequest request, HttpServletResponse response){
        List<JSONArray> propertyChartData = chartService.getRateChartData(processId);
        try (PrintWriter out = response.getWriter()){
            out.println(new JSONArray(propertyChartData));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
