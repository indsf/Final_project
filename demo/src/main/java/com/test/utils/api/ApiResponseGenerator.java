package com.test.utils.api;


import com.test.common.exception.ErrorCodeDetail;
import com.test.common.exception.ErrorResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


@UtilityClass // Lombok: 클래스의 모든 메서드를 static으로 만들고 인스턴스 생성 불가능하게 함
public class ApiResponseGenerator {

    //성공 응답 (데이터 없음, 상태코드만)
    public static ApiResponse<ApiResponse.CustomBody<Void>> success(final HttpStatus status) {
        return new ApiResponse<>(new ApiResponse.CustomBody<>(true, null, null), status);
    }

    //성공 응답 (데이터 있음)
    public static <D> ApiResponse<ApiResponse.CustomBody<D>> success(final D data, final HttpStatus status) {
        return new ApiResponse<>(new ApiResponse.CustomBody<>(true, data, null), status);
    }

    // 성공 응답 (데이터 + MediaType 지정)
    public static <D> ApiResponse<ApiResponse.CustomBody<D>> success(final D data, final MediaType mediaType, final HttpStatus status) {
        return new ApiResponse<>(new ApiResponse.CustomBody<>(true, data, null), mediaType, status);
    }

    // 실패 응답 (ErrorResponse 객체로 에러 내려줌)
    public static <D> ApiResponse<ApiResponse.CustomBody<D>> fail(ErrorCodeDetail errorCodeDetail) {
        return new ApiResponse<>(new ApiResponse.CustomBody<>(false, null, new ErrorResponse(errorCodeDetail)),
                errorCodeDetail.getHttpStatus());
    }

    // 실패 응답 (Object를 받아서 ErrorResponse로 캐스팅 → 권장 X, 타입 안전성 떨어짐)
    public static ApiResponse<Object> fail(final Object object, final HttpStatus status) {
        return new ApiResponse<>(new ApiResponse.CustomBody<>(false, null, (ErrorResponse) object), status);
    }

    // 성공 응답 (데이터 + 커스텀 헤더 포함)
    public static <D> ApiResponse<ApiResponse.CustomBody<D>> success(final D data, final HttpHeaders headers, final HttpStatus status) {
        return new ApiResponse<>(new ApiResponse.CustomBody<>(true, data, null), headers, status);
    }

}
