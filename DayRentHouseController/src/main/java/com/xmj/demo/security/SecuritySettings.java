package com.xmj.demo.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "securityconfig")
public class SecuritySettings {
    private String logoutsuccessurl;
    private String permitall;
    private String deniedpage;
    private String urlroles;

    public String getLogoutsuccessurl() {
        return logoutsuccessurl;
    }

    public void setLogoutsuccessurl(String logoutsuccessurl) {
        this.logoutsuccessurl = logoutsuccessurl;
    }

    public String getPermitall() {
        return permitall;
    }

    public void setPermitall(String permitall) {
        this.permitall = permitall;
    }

    public String getDeniedpage() {
        return deniedpage;
    }

    public void setDeniedpage(String deniedpage) {
        this.deniedpage = deniedpage;
    }

    public String getUrlroles() {
        return urlroles;
    }

    public void setUrlroles(String urlroles) {
        this.urlroles = urlroles;
    }

    @Override
    public String toString() {
        return "SecuritySettings{" +
                "logoutsuccessurl='" + logoutsuccessurl + '\'' +
                ", permitall='" + permitall + '\'' +
                ", deniedpage='" + deniedpage + '\'' +
                ", urlroles='" + urlroles + '\'' +
                '}';
    }
}
