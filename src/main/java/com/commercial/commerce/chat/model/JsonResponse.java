package com.commercial.commerce.chat.model;

public class JsonResponse {
    private ImageResponse data;
    private boolean success;
    private int status;

    public ImageResponse getData() {
        return data;
    }

    public void setData(ImageResponse data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
