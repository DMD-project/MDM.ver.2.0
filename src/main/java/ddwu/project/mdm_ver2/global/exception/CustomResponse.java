package ddwu.project.mdm_ver2.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"statusCode", "message", "content"})
public class CustomResponse<T> {
    @JsonProperty("statusCode")
    @NonNull
    private final String statusCode;

    @JsonProperty("message")
    @NonNull
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("content")
    private T content;

    // 성공한 경우 응답 생성
    public static <T> CustomResponse<T> onSuccess(T content) {
        return new CustomResponse<>(HttpStatus.OK.name(), HttpStatus.OK.getReasonPhrase(), content);
    }

    // 실패한 경우 응답 생성
    public static <T> CustomResponse<T> onFailure(String statusCode, String message) {
        return new CustomResponse<>(statusCode, message, null);
    }

    public static <T> CustomResponse<T> onFailure(String statusCode, String message, T content) {
        return new CustomResponse<>(statusCode, message, content);
    }

    // Json serialize
    public String toJsonString() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }

}
