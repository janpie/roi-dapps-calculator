package misc.data_models;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class MyAppConfig {

    public static final String chain;

    public static final String contractAddress;

    public static final double devFee;

    public static final List<Plan> plans;

    static {
        try {
            System.out.println("LOADING CONFIG");
            Gson gson = new Gson();
            FileReader fileReader = new FileReader(System.getProperty("user.dir") + "/config.json");
            AppConfig cfg = gson.fromJson(fileReader, AppConfig.class);
            chain = cfg.getChain();
            contractAddress = cfg.getContractAddress();
            devFee = cfg.getDevFee();
            plans = cfg.getPlans();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found " + e);
        }
    }
}
