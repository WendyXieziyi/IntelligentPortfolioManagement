package server.service;

import org.json.JSONArray;

import java.util.List;

public interface ChartService {
    /**
     * 返回资产变化曲线需要的数据
     * @param processId
     * @return
     */
    public List<JSONArray> getPropertyChartData(String processId);

    /**
     * 返回投资组合比例需要的数据
     * @param processId
     * @return
     */
    public List<JSONArray> getRateChartData(String processId);
}
