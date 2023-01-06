package com.tyr.todo.core.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyr.todo.core.exception.ApiException;
import com.tyr.todo.core.model.ApiExceptionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FunctionHelper {
    private final ObjectMapper objectMapper;

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> objectMapper.convertValue(element, targetClass))
                .collect(Collectors.toList());
    }

    public <S, T> T convert(S source, Class<T> targetClass) {
        return objectMapper.convertValue(source, targetClass);
    }

    public ApiException constructApiException(ApiExceptionEnum apiExceptionEnum) {
        return ApiException.builder()
                .errorCode(apiExceptionEnum.getErrorCode())
                .errorMessage(apiExceptionEnum.getErrorMessage())
                .build();
    }

    public OffsetDateTime getOffsetDateTime() {
        return OffsetDateTime.now(ZoneId.of("Europe/Dublin"));
    }
}
