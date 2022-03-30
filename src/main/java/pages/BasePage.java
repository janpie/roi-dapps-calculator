package pages;


import com.google.gson.internal.$Gson$Preconditions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BasePage {

    protected WebDriver driver;
    private WebDriverWait webDriverWait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        webDriverWait = new WebDriverWait(driver, 5);
    }

    protected WebElement clickOn(WebElement element) {
        waitForVisibility(element).click();
        return element;
    }

    protected WebElement waitForVisibility(WebElement element) {
        return webDriverWait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement writeText(WebElement element, String text) {
        waitForVisibility(element).sendKeys(text);
        return element;
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void switchTabs(int tabindex) {
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabindex));
    }

    protected List<WebElement> waitForList(List<WebElement> list) {
        webDriverWait.until((ExpectedCondition<Boolean>) driver -> list.size() > 0);
        return list;
    }

    protected List<String> getTextsFromList(List<WebElement> list) {
        return waitForList(list)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    protected void openBlankTab() {
        ((JavascriptExecutor) driver).executeScript("window.open('about:blank', '_blank')");
    }

    protected void timeUnitWait(int wait) {
        try {
            Thread.sleep(wait * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
