package com.cjl.springboot_demo.controller;

import com.alibaba.fastjson.JSON;
import com.cjl.springboot_demo.dto.AccessTokenDTO;
import com.cjl.springboot_demo.dto.GithubUser;
import com.cjl.springboot_demo.mapper.UserMapper;
import com.cjl.springboot_demo.model.User;
import com.cjl.springboot_demo.provider.GithubProvider;
import com.cjl.springboot_demo.service.UserService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.plugin2.message.CookieReplyMessage;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 通过github授权登录
 */
@Controller
public class AuthController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.Redirect_uri}")
    private String redirect_uri;

    @Value("${github.Client_id}")
    private String client_id;

    @Value("${github.Client_secret}")
    private String client_secret;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        try {
            String accessToken = githubProvider.getAccessToken(accessTokenDTO);
            GithubUser githubUser = githubProvider.getUser(accessToken);
            //System.out.println(user);
            if (githubUser != null) {
                User user = new User();
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                if (githubUser.getName() == null) {
                    user.setName(githubUser.getLogin());//暂时使用GitHub的login属性当作name
                } else {
                    user.setName(githubUser.getName());
                }
                user.setAccountId(String.valueOf(githubUser.getId()));
                user.setAvatarUrl(githubUser.getAvatarUrl());
                userService.createOrUpdate(user);
                response.addCookie(new Cookie("token", token));
                return "redirect:/";
            } else {
                //登录失败，重新登录
                return "redirect:/";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 退出登录状态，删除cookie中的token和session
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        //删除cookie 创建一个同名的cookie并将其初始化为空，setMaxAge(0)
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);
        if (request.getSession().getAttribute("user")==null){
            System.out.println("session null");
        }
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie1:cookies
             ) {
            if (cookie.getName().equals("token")){
                System.out.println(cookie1.getValue());
            }
        }
        return "redirect:/";
    }
}
