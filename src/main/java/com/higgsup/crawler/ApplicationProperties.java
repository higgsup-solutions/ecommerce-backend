package com.higgsup.crawler;

public class ApplicationProperties {

  /*Dynamic Config*/

  public static final String LINK_PARENT = "https://www.lazada.vn/trang-diem/?spm=a2o4n.pdp.cate_4.1.63912a8e51DPgu";

  public static final String PROXY_SERVER = "183.90.191.93:1080";

  public static final int CATEGORY_ID = 23;

  public static final String OUTPUT_FILE_PATH = "C:\\Users\\thanhdt\\Desktop\\fullData.txt";

  /*Driver config*/

  public static final String DRIVER_TYPE = "webdriver.chrome.driver";

  public static final String DRIVER_PATH = "src/main/resources/chromedriver.exe";

  public static final String PROXY_SETTING = "--proxy-server=http://" + ApplicationProperties.PROXY_SERVER;

  public static final String DRIVER_SCREEN_SIZE = "start-maximized";

  public static final String DRIVER_HEADLESS = "headless";

  public static final String DRIVER_DISABLE_EXTENSION = "--disable-extensions";

}
