package org.rails.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Result <T> {

    private T payload;

    private List<String> errorMessages = new ArrayList<>();

    private ResultType resultType = ResultType.SUCCESS;

    public Result() {}

    public void addErrorMessage(String format, ResultType resultType, Object... args) {
        this.resultType = resultType;
        errorMessages.add(String.format(format, args));
    }

    public boolean isSuccess() {
        return this.resultType == ResultType.SUCCESS;
    }

    public T getPayload() {
        return payload;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public ResultType getResultType() {
        return resultType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Result<?> result = (Result<?>) o;
        return Objects.equals(payload, result.payload) && Objects.equals(errorMessages, result.errorMessages) && resultType == result.resultType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(payload, errorMessages, resultType);
    }
}
