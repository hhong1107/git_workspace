
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hoho on 14-3-12.
 */
public class AnalyzeLog {
    public static void main(String[] args) throws Exception {
        readF2("C:\\Users\\hoho\\Desktop\\文档\\ETSsysLog - 副本.2014-03-12");
    }

    public static final void readF1(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath)));

        for (String line = br.readLine(); line != null; line = br.readLine()) {
            line = new String(line.getBytes("GBK"), "GBK");
            if (line.indexOf("[易宝返回") > 0 && line.indexOf("<RSTCode>000000</RSTCode>") < 0) {

            }
            System.out.println(line);
        }
        br.close();

    }


    private static final String MARK1 = "<RSTMsg>";
    private static final String MARK2 = "</RSTMsg>";
    public static void readF2(String filePath) throws Exception {
        FileInputStream fio = new FileInputStream(new File(filePath));
        List<String> list = IOUtils.readLines(fio, "GBK");
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String msg : list) {
            if (msg.indexOf("[易宝返回") > 0 && msg.indexOf("<RSTCode>000000</RSTCode>") < 0) {
//                System.out.println(msg);
                String errorMsg = msg.substring(msg.indexOf(MARK1) + MARK1.length(), msg.indexOf(MARK2));
                if (map.containsKey(errorMsg)) {
                    map.put(errorMsg, map.get(errorMsg) + 1);
                } else {
                    map.put(errorMsg, 1);
                }

//                System.out.println(errorMsg);
            }

        }

        for (Map.Entry entry : map.entrySet()) {
            System.out.println( "次数=" + entry.getValue()+"                 错误信息："+entry.getKey() );
        }
    }
}
