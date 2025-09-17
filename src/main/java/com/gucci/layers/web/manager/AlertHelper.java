package com.gucci.layers.web.manager;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AlertHelper {

    WebDriver driver;

    public AlertHelper(){this.driver = driver;}

    public Alert getAlert(){
        new WebDriverWait(driver, Duration.ofSeconds(6)).until(ExpectedConditions.alertIsPresent());
        return driver.switchTo().alert();
    }
    public void acceptAlert(){ getAlert().accept();}

    public void disMissAlert(){ getAlert().dismiss();}

    public boolean isAlertPresent(){

        try {
            driver.switchTo().alert();
            return true;
        }catch (NoAlertPresentException e){
            return false;

        }
    }
    public void acceptAlertIfPresented(){
        if (!isAlertPresent())return;
        acceptAlert();
    }
    public void dismissAlertIfPresented() {
        if(!isAlertPresent()) return;
        disMissAlert();
    }

    public void acceptPrompt(String param) {
        if(!isAlertPresent()) return;
        Alert alert = getAlert();
        alert.sendKeys(param);
        alert.accept();
    }

    public void dismissPrompt(String param) {
        if(!isAlertPresent()) return;
        Alert alert = getAlert();
        alert.sendKeys(param);
        alert.dismiss();
    }
}

