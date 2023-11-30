package ru.itis.filter;

import ru.itis.dao.UserDao;
import ru.itis.service.UserService;
import ru.itis.util.exception.DBException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter extends HttpFilter {
    private static final String[] securedPaths = new String[]{"/profile", "/notes", "/editnote", "/deletenote"};
    private UserService userService;
    private UserDao userDao;

    @Override
    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        userDao = new UserDao();
        try {
            userDao.init();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        userService = new UserService(userDao);
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        boolean prot = false;
        for (String path : securedPaths) {
            if (path.equals(req.getRequestURI().substring(req.getContextPath().length()))) {
                prot = true;
                break;
            }
        }
        if (prot && !userService.isNonAnonymous(req, res)) {
            res.sendRedirect(req.getContextPath() + "/signin");
        } else {
            if (userService.isNonAnonymous(req, res)) {
                req.setAttribute("user", userService.getUser(req, res));
            }
            chain.doFilter(req, res);
        }
    }
}
