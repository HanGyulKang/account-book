package com.study.account.common.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class CommonFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if("/apis/v1/user/signup".equals(req.getRequestURI())) {
           chain.doFilter(req, res);
        } else {
            if(req.getMethod().equals("POST")) {
                String authorization = req.getHeader("Authorization");

                if(authorization.equals("test token")) {
                    chain.doFilter(req, res);
                } else {
                    PrintWriter outPrintWriter = res.getWriter();
                    outPrintWriter.println("not authenticated");
                }
            } else {
                chain.doFilter(req, res);
            }
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
