package jobs;

import org.apache.commons.io.FileUtils;
import play.Logger;
import play.Play;
import play.jobs.Job;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ApplicationStartJob extends Job {
    public static final String KLDI_DIR = Play.configuration.getProperty("kldi.directory");
    public static final String[] SESSIONCONNECTION= {
            Play.configuration.getProperty("db-session.url"),
            Play.configuration.getProperty("db-session.table"),
            Play.configuration.getProperty("db-session.driver"),
            Play.configuration.getProperty("db-session.user"),
            Play.configuration.getProperty("db-session.pass")
    };
    public static final Map<String, String> CACHE_TIME;
    static
    {
        CACHE_TIME = new HashMap<String, String>();
        CACHE_TIME.put("VERYSHORT", "5mn");
        CACHE_TIME.put("SHORT", "10mn");
        CACHE_TIME.put("MEDIUM", "30mn");
        CACHE_TIME.put("LONG", "1h");
        CACHE_TIME.put("ONEDAY", "24h");
        CACHE_TIME.put("ONEMONTH", "720h");
    }
    public void doJob(){
        SimpleDateFormat df=new SimpleDateFormat("dd-MMM-yyyy hh:mm");
        int count=0;
        for(File file: FileUtils.getTempDirectory().listFiles()) {
            if (file.getName().startsWith("meroket_temp")) {
                file.delete();
                count++;
            }
        }
        if(count>0) {
            Logger.info("[START] delete sirup temp files: %,d", count);
        }
    }

}
