package com.example.demo1.service;

import com.example.demo1.dao.UserDao;
import com.example.demo1.model.User;
import com.example.demo1.util.PasswordEncryptionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserService {
    private UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    public void register(User user){
        user.setPassword(PasswordEncryptionUtil.encryptPassword(user.getPassword()));
        userDao.register(user);
    }

    public boolean login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String usernameOrEmail = req.getParameter("username");
        String password = req.getParameter("password");
        var user = userDao.findByUsernameOrEmail(usernameOrEmail);
        if(user != null && PasswordEncryptionUtil.checkPassword(password, user.getPassword())){
            HttpSession session = req.getSession();
            session.setAttribute("user", user); // luu vo kho
            if(user.getRole().getName().equals("ADMIN")){
                resp.sendRedirect("/book?message=Login Success");
            }else{
                resp.sendRedirect("/product?message=Login Success");
            }
            return true;
        }
        return false;
    }
}