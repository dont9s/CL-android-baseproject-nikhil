package com.skeleton.retrofit2;

import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * ErrorUtils
 */


public class ErrorUtils {

    /**
     *
     * @param response
     * @return
     */
    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter = RestClient.getRetrofitBuilder().responseBodyConverter(APIError.class, new Annotation[0]);
        APIError error;
        try {
            if (response.errorBody() != null) {//&& !converter.convert(response.errorBody()).isEmptyObject()
                error = converter.convert(response.errorBody());
            } else {
                error = new APIError(response.code(), response.message());
            }

        } catch (Exception e) {
            int statusCode = 900;
            String message = ResponseResolver.UNEXPECTED_ERROR_OCCURRED;
            if (response.code() != 0)
                statusCode = response.code();
            if (response.message() != null && !response.message().isEmpty())
                message = response.message();
            return new APIError(statusCode, message);
        }
        return error;
    }
}
