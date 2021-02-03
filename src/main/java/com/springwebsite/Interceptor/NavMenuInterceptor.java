package com.springwebsite.Interceptor;

import com.springwebsite.NavMenu.NavMenuService;
import com.springwebsite.board.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NavMenuInterceptor implements HandlerInterceptor {

    private final NavMenuService navMenuService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        List<Board> navBoardList = navMenuService.getNavMenuList();
        request.setAttribute("navBoardList", navBoardList);
        return true;
    }
}
