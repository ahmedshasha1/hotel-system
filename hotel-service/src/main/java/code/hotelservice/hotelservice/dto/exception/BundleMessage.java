package code.hotelservice.hotelservice.dto.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
public class BundleMessage {
    @JsonProperty(value = "message_ar")
    private String messageAr;

    @JsonProperty(value = "message_en")
    private String messageEn;
}
