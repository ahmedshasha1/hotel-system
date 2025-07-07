package code.hotelservice.hotelservice.dto.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
public class ExceptionDto {
   private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "YYYY - MM - DD  hh:mm:ss")
    private LocalDateTime time;

    private BundleMessage bundleMessage;

   public ExceptionDto(HttpStatus status,BundleMessage bundleMessage){
       time = LocalDateTime.now();
       this.status = status;
       this.bundleMessage = bundleMessage;

   }

}
