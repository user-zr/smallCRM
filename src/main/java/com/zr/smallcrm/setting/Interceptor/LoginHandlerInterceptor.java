//package com.zr.smallcrm.setting.Interceptor;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//public class LoginHandlerInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        System.out.println("come");
//
//        HttpSession session = request.getSession(false);
//
//        System.out.println(session.getAttribute("user")==null);
//
//        if (session.getAttribute("user")==null){
//            response.sendRedirect(request.getContextPath()+"/login");
//            return false;
//
//        }
//
//        return true;
//    }
//
//}
