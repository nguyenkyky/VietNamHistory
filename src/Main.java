import com.oop.data.*;
import com.oop.service.NhanVatService;
import com.oop.view.MainScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getRootLogger();

    public static void main(String[] args) {
//        new ThoiKy();
//        System.out.println("Working Directory = " + System.getProperty("user.dir"));
//        new TranDanh();
        MainScreen mainScreen = new MainScreen();
        mainScreen.on(args);
//        new DiaDanh();
//        new SuKien();
//        new DanhNhan();
//        logger.error("ahihi");
//        logger.trace("trace");
//        logger.error("asdfs");
//        logger.warn("warn");
//        logger.debug("debug");
//        logger.info("info");
    }

}
