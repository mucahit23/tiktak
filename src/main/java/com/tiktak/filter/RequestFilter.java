package com.tiktak.filter;

import com.tiktak.util.LogBean;
import com.tiktak.util.MessageBean;
import com.tiktak.util.TiktakUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.LocaleResolver;

import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

@Component
@Order(1)
@Slf4j
public class RequestFilter implements Filter {

    @Autowired
    private LogBean logBean;
    @Autowired
    private LocaleResolver localeResolver;
    @Autowired
    private MessageBean messageBean;

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        req.setCharacterEncoding("UTF-8");

        try {
            Locale locale = localeResolver.resolveLocale(req);
            messageBean.setLocale(locale);
        } catch (Exception ex) {
            messageBean.setLocale(new Locale("tr"));
            log.error("RequestFilter exception : {}", TiktakUtils.getFormattedMessage(ex.getMessage()));
        }
        log.info("Starting a transaction for req : {}", req.getRequestURI());
        logBean.setTraceId(UUID.randomUUID().toString());
        chain.doFilter(request, response);
        log.info("Committing a transaction for req : {}", req.getRequestURI());
    }

    // other methods 
}

