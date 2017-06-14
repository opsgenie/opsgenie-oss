package com.opsgenie.core.app;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents application specific information
 * such as application name, type, id, etc ...
 *
 * @author serkan
 */
public class ApplicationInfo {

    private String applicationId;
    private String applicationName;
    private String applicationVersion;
    private String applicationType;
    private String applicationProfile;
    private Map<String, Object> extraInfos;

    public ApplicationInfo() {
    }

    public ApplicationInfo(String applicationId, String applicationName,
                           String applicationVersion, String applicationType,
                           String applicationProfile) {
        this.applicationId = applicationId;
        this.applicationName = applicationName;
        this.applicationVersion = applicationVersion;
        this.applicationType = applicationType;
        this.applicationProfile = applicationProfile;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getApplicationProfile() {
        return applicationProfile;
    }

    public void setApplicationProfile(String applicationProfile) {
        this.applicationProfile = applicationProfile;
    }

    public Map<String, Object> getExtraInfos() {
        return extraInfos;
    }

    public void setExtraInfos(Map<String, Object> extraInfos) {
        this.extraInfos = extraInfos;
    }

    public void addExtraInfo(String infoKey, Object infoValue) {
        if (extraInfos == null) {
            extraInfos = new HashMap<String, Object>();
        }
        extraInfos.put(infoKey, infoValue);
    }

    public Map<String, Object> toProperties() {
        Map<String, Object> props = new HashMap<String, Object>(5 + (extraInfos == null ? 0 : extraInfos.size()));
        props.put("applicationName", getApplicationName());
        props.put("applicationId", getApplicationId());
        props.put("applicationVersion", getApplicationVersion());
        props.put("applicationType", getApplicationType());
        props.put("applicationProfile", getApplicationType());
        if (extraInfos != null) {
            for (Map.Entry<String, Object> entry : extraInfos.entrySet()) {
                props.put(entry.getKey(), entry.getValue());
            }
        }
        return props;
    }

    @Override
    public String toString() {
        return "ApplicationInfo{" +
                "applicationId='" + applicationId + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", applicationVersion='" + applicationVersion + '\'' +
                ", applicationType='" + applicationType + '\'' +
                ", applicationProfile='" + applicationProfile + '\'' +
                ", extraInfos=" + extraInfos +
                '}';
    }

}
