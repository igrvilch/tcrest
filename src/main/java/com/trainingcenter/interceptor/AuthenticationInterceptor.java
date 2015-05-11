package com.trainingcenter.interceptor;

import com.trainingcenter.form.LoginForm;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!request.getRequestURI().equals(request.getContextPath() + "/login")) {
            if (request.getRequestURI().equals(request.getContextPath() + "/courses/create")) {
                request.getSession().setAttribute("redirectPath", "/courses/create");
            }
            LoginForm userData = (LoginForm) request.getSession().getAttribute("LOGGEDIN_USER");
                if (userData == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
