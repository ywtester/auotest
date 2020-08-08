import org.apache.log4j.Logger;

public class test {
    public static final Logger logger = Logger.getLogger(test.class);
    public static void main(String[] args) {
        logger.info("日志输出");
        logger.error("error");
        logger.debug("debug");
        logger.warn("warn");
    }
}
