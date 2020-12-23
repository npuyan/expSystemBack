package com.zty.springboot01login.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zty.springboot01login.Pojo.RespBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out=httpServletResponse.getWriter();
        RespBean errer=RespBean.error("权限不足");
        out.write(new ObjectMapper().writeValueAsString(errer));
        out.flush();
        out.close();

    }
}
