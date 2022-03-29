package pages;

import misc.ChainUtils;
import misc.data_models.Transaction;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransactionsPage extends BasePage {

    @FindBy(xpath = "//span[@data-original-title='Invest']//preceding::td[1]")
    private List<WebElement> transactionsList;

    @FindBy(css = "a[aria-label='Next']")
    private WebElement nextPage;

    @FindBy(xpath = "//div[text()='Status:']//following::span[1]")
    private WebElement transactionStatus;

    @FindBy(xpath = "//div[text()='Timestamp:']//following::div[1]")
    private WebElement transactionTime;

    @FindBy(xpath = "//div[text()='Value:']//following::div[1]/span/span")
    private WebElement transactionValue;

    @FindBy(xpath = "//td[text()='uint8']//following::td[1]/span")
    private WebElement decodedPlan;

    private final String DECODE_JAVASCRIPT = "javascript:decodeInput();btnDecodeClick();";
    private final int retryCount = 10;


    public TransactionsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public List<String> getAllInvestTransactionsIds() {
        System.out.println("FETCHING ALL INVEST CONTRACT TRANSACTIONS...");
        List<String> transactions = new ArrayList<>();
        boolean proceed = true;
        while (proceed) {
            transactions.addAll(getTextsFromList(transactionsList));
            if (isDisplayed(nextPage)) {
                clickOn(nextPage);
                proceed = true;
            } else {
                proceed = false;
            }
        }
        return transactions;
    }

    public Transaction getTransactionDetails(String transactionId) {
        int count = 0;
        while (count < retryCount) {
            try {
                driver.get(ChainUtils.getChainUrl() + "/tx/" + transactionId);
                Transaction transaction = new Transaction();
                transaction.setSuccess(checkIsTransactionSuccess());
                transaction.setValue(getTransactionValue());
                transaction.setDateTime(getTransactionDateTime());
                transaction.setPlan(getPlan());
                return transaction;
            } catch (Exception e) {
                System.out.println("Failed to fetch data for this tx - retrying...");
                count++;
            }
        }
        Transaction emptyTransaction = new Transaction();
        emptyTransaction.setPlan(0);
        emptyTransaction.setDateTime(LocalDateTime.now());
        emptyTransaction.setSuccess(true);
        emptyTransaction.setValue(0.0);
        System.out.println("Failed to fetch data for this tx - abandoning");
        return emptyTransaction;
    }

    public Boolean checkIsTransactionSuccess() {
        waitForVisibility(transactionStatus);
        return transactionStatus.getAttribute("class").contains("success");
    }

    private Double getTransactionValue() {
        String s = waitForVisibility(transactionValue).getText();
        Pattern pattern = Pattern.compile("[0-9,\\.]+");
        Matcher matcher = pattern.matcher(s);
        matcher.find();
        s = matcher.group(0);
        s = s.replaceAll(",", "");
        return Double.parseDouble(s);
    }

    private LocalDateTime getTransactionDateTime() {
        String s = waitForVisibility(transactionTime).getText();
        Pattern pattern = Pattern.compile("\\w{3}-\\d{2}.+UTC");
        Matcher matcher = pattern.matcher(s);
        matcher.find();
        s = matcher.group(0);
        return LocalDateTime.parse(s, DateTimeFormatter.ofPattern("MMM-dd-yyyy hh:mm:ss a +z", Locale.ENGLISH));
    }

    private Integer getPlan() {
        ((JavascriptExecutor) driver).executeScript(DECODE_JAVASCRIPT);
        return Integer.parseInt(decodedPlan.getAttribute("textContent"));
    }


}
