package com.example.lab3_csis4280;

public interface OnEventListener<T> {

    public void onSuccess(T result);
    public void onFailure(Exception e);
}
