package config;

public class BrowserConfig {

    public String getBrowser() {
        String browser = System.getProperty("browser");
        if (browser == null)
            return "chrome";
        else return browser;
    }

}
