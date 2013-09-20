
import com.ocr.PriceOcr;
import com.ocr.Study;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.xue.http.HttpHelper;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author xueliang
 */
public class Program {

    private static HttpHelper helper = new HttpHelper();

    public static void main(String[] args) throws IOException {
        //study();
        recog();
    }
    static StringBuilder message;
    static HttpHelper loader = new HttpHelper();
    static PriceOcr ocr = new PriceOcr();

    static void study() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
//        map.put("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=QhR4EIGRvPo%3d", "1380.00");
//        map.put("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=MI4bTYRK6kI%3d", "1599.00");
//        map.put("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=PnbRPod%2fIjs%3d", "2640.00");
//        map.put("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=XIlBliTnVh0%3d", "718.00");
//        map.put("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=MpMy%2fxX%2bF88%3d", "928.00");
        // map.put("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=wUYe86jZk2I%3d","3199.00");
        map.put("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=RoLZy%2basTIQ%3d", "5029.00");
        for (Entry<String, String> kv : map.entrySet()) {
            String url = kv.getKey();
            String knowledge = kv.getValue();
            int width = 10;
            System.out.println(url + " " + knowledge + " " + width);
            Study.study(url, knowledge, 10);
        }
    }

    static void recog() throws IOException {
        List<String> sources = new LinkedList<String>();
        sources.add("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=qMpvdVn5C98%3d");
        sources.add("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=MpMy%2fxX%2bF88%3d");
        sources.add("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=A5FJ0%2bPsbEo%3d");
        sources.add("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=wUYe86jZk2I%3d");
        sources.add("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=ZVcRwAX7vKc%3d");
        sources.add("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=P8CP9fPXk0Q%3d");
        sources.add("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=zPQ55JSOt6s%3d");
        sources.add("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=hhbT%2f%2bp2jOM%3d");
        sources.add("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=wl6GUUqEx7Q%3d");
        sources.add("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=RoLZy%2basTIQ%3d");
        sources.add("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=Zrk%2ftR%2bGf9s%3d");
        sources.add("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=X7QWOLLZGbw%3d");
        sources.add("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=O8UZvkd0WPk%3d");
        sources.add("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=zPQ55JSOt6s%3d");
        sources.add("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=RoLZy%2basTIQ%3d");
        sources.add("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=azEplZpVtqo%3d");
        sources.add("http://www.newegg.com.cn/Common/PriceImage.aspx?PId=RtuaNqGDYRg%3d");
        PriceOcr ocr = new PriceOcr();
        long now = System.currentTimeMillis();
        for (String source : sources) {
            byte[] bytes = helper.getBytes(source);
            java.io.ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            String content = ocr.recog(bis, 10);
            bis.close();
            System.out.println(String.format("%-10s %s", content,source));
        }
        System.out.println(String.format("识别%d个图片 耗时约:%d毫秒",sources.size(),System.currentTimeMillis()-now));
    }
}
