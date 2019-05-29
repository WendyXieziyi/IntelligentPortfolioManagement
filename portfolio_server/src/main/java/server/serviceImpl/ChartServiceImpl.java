package server.serviceImpl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import server.dao.FileDao;
import server.daoImpl.FileDaoImpl;
import server.service.ChartService;

import java.util.ArrayList;
import java.util.List;

@Service(value = "chartService")
public class ChartServiceImpl implements ChartService {

    private FileDao fileDao = new FileDaoImpl();
    final String processDir = "src\\main\\resources\\static\\processes\\";
    final int partDataSetSize = 50; //替代resT.size()

    @Override
    public List<JSONArray> getPropertyChartData(String processId) {
        ArrayList<String> lines = fileDao.readFileByLine(processDir + processId + "\\PGPortfolio-master\\train_package\\1\\programlog");
        ArrayList<JSONArray> res = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.startsWith("INFO:root:total assets")) {
                double assets = Double.parseDouble(line.replaceAll(":", " ").replaceAll("[a-zA-Z]", " ").trim());
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(counter);
                counter++;
                jsonArray.put(assets);
                res.add(jsonArray);
            }
        }
        // System.out.println(res.size());
        return res;
    }

    @Override
    public List<JSONArray> getRateChartData(String processId) {
        ArrayList<String> lines = fileDao.readFileByLine(processDir + processId + "\\PGPortfolio-master\\train_package\\1\\programlog");
        ArrayList<JSONArray> resT = new ArrayList<>();
        ArrayList<JSONArray> res = new ArrayList<>();
        int i = 0;
        String rates = "";
        while (i < lines.size()) {
            String line = lines.get(i);
            if (line.startsWith("DEBUG:root:the raw omega")) {
                rates += line.substring(line.indexOf("["));
                i++;
                line = lines.get(i);
                while (!line.startsWith("DEBUG:root:")) {
                    rates += line;
                    i++;
                    line = lines.get(i);
                }
                String[] ratesList = rates.substring(1,rates.length()-1).split(" ");
                JSONArray jsonArray = new JSONArray();
                for(int j=1;j<ratesList.length;j++){
                    try {
                        jsonArray.put(Double.parseDouble(ratesList[j]));
                    }catch (NumberFormatException e){}
                }
                resT.add(jsonArray);
                //System.out.println(rates);
                rates = "";
            }
            i++;
        }
        JSONArray steps = new JSONArray();
        steps.put("steps");
        for(int j=0;j<partDataSetSize;j++){
            steps.put(j);
        }
        res.add(steps);
        String[] coins = {"ETH", "LTC", "XRP", "reversed_USDT", "ETC", "DASH", "XMR", "XEM", "FCT", "GNT", "ZEC"};
        for(int j=0;j<11;j++){
            JSONArray coinArray = new JSONArray();
            coinArray.put(coins[j]);
            res.add(coinArray);
        }
        for(int j=0;j<partDataSetSize;j++){
            JSONArray temp = resT.get(j);
            for(int k=1;k<=coins.length;k++){
                res.get(k).put(temp.get(k-1));
            }
        }
        return res;
    }
}
