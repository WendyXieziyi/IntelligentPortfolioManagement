package server.daoImpl;

import server.dao.FileDao;
import server.util.ResultMessage;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FileDaoImpl implements FileDao {
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

    @Override
    public String readJsonFile(String filePath) {
        String jsonStr = "";
        try {
            File jsonFile = new File(filePath);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResultMessage writeJsonFile(String s, String filePath) {
        File fs = new File(filePath);
        try {
            FileWriter fw = new FileWriter(fs.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(s);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }

    @Override
    public ArrayList<String> readFileByLine(String path) {
        ArrayList<String> res = new ArrayList<>();
        try {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader bf = new BufferedReader(fileReader);
            String line = "";
            while ((line = bf.readLine()) != null) {
                res.add(line);
                //System.out.println(line);
            }
            bf.close();
            fileReader.close();
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
