package com.tiktak.service;

import com.tiktak.util.LogBean;
import com.tiktak.util.MessageBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final MessageSource messageSource;
    private final LogBean logBean;
    private final MessageBean messageBean;

    public String getLocaleMessage(String messageKey) {
        try {
            return messageSource.getMessage(messageKey, null, messageBean.getLocale());
        } catch (Exception e) {
            log.error("(traceId:{}), key : {} not found", logBean.getTraceId(), messageKey);
            return null;
        }
    }

    public String getLocaleMessage(String messageKey, String... args) {
        try {
            return messageSource.getMessage(messageKey, args, messageBean.getLocale());
        } catch (Exception e) {
            log.error("(traceId:{}), key : {} not found", logBean.getTraceId(), messageKey);
            return null;
        }
    }

}