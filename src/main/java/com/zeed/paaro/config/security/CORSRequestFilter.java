package com.zeed.paaro.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSRequestFilter implements Filter {

    @Value("${headers.access.allowed-origins:*}")
    private String accessControlAllowedServer;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        String originHeader = request.getHeader("Origin");

        if (originHeader != null && !originHeader.isEmpty() && accessControlAllowedServer.contains(originHeader)) {
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Origin", originHeader);
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with,origin,accept,content-type,access-control-request-method,access-control-request-headers,authorization");
        }
        if (!"OPTIONS".equals(request.getMethod())) {
            try {
                chain.doFilter(new RequestWrapper((HttpServletRequest)req), res);
            } catch (IOException | ServletException e) {
                e.printStackTrace();
            }
        }
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}