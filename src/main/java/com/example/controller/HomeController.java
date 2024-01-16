package com.example.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.service.LoginUser;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String getHome(Model model, @AuthenticationPrincipal LoginUser loginUser) {
    // Model model：ユーザー情報をビューに渡す
    // @AuthenticationPrincipal LoginUser loginUser：認証されたユーザー情報を直接受け取る
        model.addAttribute("user", loginUser.getUser());
        // model.addAttribute()：モデルへの追加
        // 第一引数：モデル名
        // 第二引数：ユーザー情報の取得
        return "home";
        // home.htmlの表示
    }
}