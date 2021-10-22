package com.outven.bmtchallange.models.upload;

import com.google.gson.annotations.SerializedName;

public class UploadResponse {

    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
