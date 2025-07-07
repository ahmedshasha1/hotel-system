package code.hotelservice.hotelservice.service.exception;

import code.hotelservice.hotelservice.dto.exception.BundleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class BundleMessageService {
    private static ResourceBundleMessageSource source;

    @Autowired
    public BundleMessageService(@Qualifier("messages") ResourceBundleMessageSource source){
        this.source =source;
    }

    public static BundleMessage getMessage(String key){
        return new BundleMessage(
                source.getMessage(key, null , new Locale("en")),
                source.getMessage(key,null,new Locale("ar"))
        );

    }
}
