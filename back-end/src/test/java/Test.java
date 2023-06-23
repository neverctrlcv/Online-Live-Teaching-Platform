import com.runhuo.live.utils.ShareCodeUtil;
import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisNoOpBindingRegistry;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/18 16:59
 */
public class Test {
    public static void main(String[] args) {
//        KeyWordComputer kwc = new KeyWordComputer(5);
//        String title = "初三物理";
//        List<Keyword> result = kwc.computeArticleTfidf(title);
//        for (Keyword keyword:result){
//            System.out.println(keyword.getScore());
//            System.out.println(keyword.getName());
//        }
        System.out.println(DigestUtils.md5Hex("12345678"));
//        long endtime = new Date().getTime();
//        long startTime =( endtime - 7 * 24 * 60 *60* 1000);
//        System.out.println(new Timestamp(startTime));
//        System.out.println(new Timestamp(endtime));

//         SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
//        System.out.println(sdf.format(new Date().getTime()));

    }
}
