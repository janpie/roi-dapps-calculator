package misc;

import misc.data_models.MyAppConfig;

public class ChainUtils {


    public static final String getChainUrl() {
        switch (MyAppConfig.chain.toUpperCase()) {
            case "POLYGON":
                return Constants.POLYSCAN_URL;
            case "BINANCE":
                return Constants.BSCSCAN_URL;
            case "FANTOM":
                return Constants.FTMSCAN_URL;
            case "AVALANCHE":
                return Constants.SNOWTRACE_URL;
            default:
                throw new IllegalArgumentException(MyAppConfig.chain + " is unsupported chain. Please use one of the following: POLYGON, BINANCE, FANTOM, AVALANCHE");
        }
    }

    public static final String getChainCurrency() {
        switch (MyAppConfig.chain.toUpperCase()) {
            case "POLYGON":
                return "MATIC";
            case "BINANCE":
                return "BNB";
            case "FANTOM":
                return "FTM";
            case "AVALANCHE":
                return "AVAX";
            default:
                throw new IllegalArgumentException(MyAppConfig.chain + " is unsupported chain. Please use one of the following: POLYGON, BINANCE, FANTOM, AVALANCHE");
        }
    }


}
