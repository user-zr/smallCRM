package com.zr.smallcrm.filter;

import com.zr.smallcrm.setting.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        User user = (User) request.getSession().getAttribute("user");

        String path = request.getServletPath();
//        System.out.println(path);
//        System.out.println(path);
        if ("/login.jsp".equals(path) || "/".equals(path) || "/login".equals(path) || "/login.do".equals(path) || path.contains(".js") || path.contains(".css") || path.contains(".JPG") || path.contains(".png")) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {

            if (user != null) {
                filterChain.doFilter(servletRequest, servletResponse);
            }
            else {
                response.sendRedirect(request.getContextPath() + "/login");
            }
        }


    }
}
