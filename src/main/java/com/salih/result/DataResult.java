package com.salih.result;

public class DataResult<T> extends Result {
    private final T data;

    public DataResult(T data, Result result) {
        super(result);
        this.data = data;
    }

    public DataResult(T data, int resultCode, String resultText) {
        super(resultCode, resultText);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "DataResult{" +
                "data=" + data +
                ", resultCode=" + getResultCode() +
                ", resultText='" + getResultText() + '\'' +
                '}';
    }
}