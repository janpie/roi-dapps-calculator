package runner;

import io.github.bonigarcia.wdm.WebDriverManager;
import misc.ChainUtils;
import misc.DataAggregator;
import misc.data_models.MyAppConfig;
import misc.data_models.Transaction;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.TransactionsPage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Run {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("disable-gpu");
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();


        driver.get(ChainUtils.getChainUrl() + "txs?a=" + MyAppConfig.contractAddress);
        TransactionsPage transactionsPage = new TransactionsPage(driver);
        List<String> transactionIds = transactionsPage.getAllInvestTransactionsIds();
        List<Transaction> transactions = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(1);
        int size = transactionIds.size();
        transactionIds.forEach(tx -> {
            Transaction transaction = transactionsPage.getTransactionDetails(tx);
            transactions.add(transaction);
            System.out.println("Tx analyzed: " + count + "/" + size);
            count.getAndIncrement();
        });


        DataAggregator.printPlansBalance(transactions);
        DataAggregator.printContractHealthPrediction(transactions, MyAppConfig.plans);
        DataAggregator.printLegal();


        driver.quit();
    }


}
