package com.mediustechnologies.payemi.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShowNotificationDTO {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("notification_logo")
    @Expose
    private String notification_logo;


    @SerializedName("notification_name")
    @Expose
    private String notification_name;

    @SerializedName("notification_time")
    @Expose
    private String notification_time;

    @SerializedName("notification_type")
    @Expose
    private String notification_type;

    @SerializedName("notification_status")
    @Expose
    private boolean notification_status;

    @SerializedName("notification_action")
    @Expose
    private boolean notification_action;

    @SerializedName("notification_is_seen")
    @Expose
    private boolean notification_is_seen;


    @SerializedName("profile")
    @Expose
    private String profile;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotification_logo() {
        return notification_logo;
    }

    public void setNotification_logo(String notification_logo) {
        this.notification_logo = notification_logo;
    }

    public String getNotification_name() {
        return notification_name;
    }

    public void setNotification_name(String notification_name) {
        this.notification_name = notification_name;
    }

    public String getNotification_time() {
        return notification_time;
    }

    public void setNotification_time(String notification_time) {
        this.notification_time = notification_time;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }

    public boolean isNotification_status() {
        return notification_status;
    }

    public void setNotification_status(boolean notification_status) {
        this.notification_status = notification_status;
    }

    public boolean isNotification_action() {
        return notification_action;
    }

    public void setNotification_action(boolean notification_action) {
        this.notification_action = notification_action;
    }

    public boolean isNotification_is_seen() {
        return notification_is_seen;
    }

    public void setNotification_is_seen(boolean notification_is_seen) {
        this.notification_is_seen = notification_is_seen;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
