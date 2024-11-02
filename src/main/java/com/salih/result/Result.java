package com.salih.result;

import lombok.Getter;

@Getter
public class Result {

    private final int resultCode;
    private final String resultText;

    public Result(int resultCode, String resultText) {
        this.resultCode = resultCode;
        this.resultText = resultText;
    }

    public static final Result SUCCESS = new Result(0, "OK!");
    public static final Result SERVER_ERROR = new Result(1, "SERVER ERROR!");
    public static final Result VALIDATION_ERROR = new Result(2, "VALIDATION ERROR!");
    public static final Result NOT_FOUND = new Result(3, "RESOURCE NOT FOUND!");
    public static final Result BAD_REQUEST = new Result(4, "BAD REQUEST!");
    public static final Result FAILURE = new Result(5, "FAILURE!");

    // Copy constructor
    public Result(Result result) {
        this.resultCode = result.resultCode;
        this.resultText = result.resultText;
    }

    // Method to check if the result is a success
    public boolean isSuccess() {
        return this.resultCode == 0;
    }

    public String getMessage() {
        return this.resultText;
    }

    public static Result showMessage(Result resultType, String customMessage) {
        return new Result(resultType.getResultCode(), customMessage);
    }

    @Override
    public String toString() {
        return "Result{" +
                "resultCode=" + resultCode +
                ", resultText='" + resultText + '\'' +
                '}';
    }
}
