package com.example.loolah.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LiveDataWrapper<T> {
    @NonNull
    private final Status status;
    @Nullable
    private final T data;
    @Nullable
    private final String message;

    private LiveDataWrapper(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> LiveDataWrapper<T> success(@NonNull T data) {
        return new LiveDataWrapper<>(Status.SUCCESS, data, null);
    }

    public static <T> LiveDataWrapper<T> error(String msg, @Nullable T data) {
        return new LiveDataWrapper<>(Status.ERROR, data, msg);
    }

    public static <T> LiveDataWrapper<T> loading(@Nullable T data) {
        return new LiveDataWrapper<>(Status.LOADING, data, null);
    }

    public enum Status {SUCCESS, ERROR, LOADING}

    @NonNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public String getMessage() {
        return message;
    }
}
