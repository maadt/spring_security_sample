package com.example.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.entity.User;


public class LoginUser implements UserDetails {
// UserDetails：認証・認可に関係するメソッドが定義されている

    // Userオブジェクト(Entityクラス)
    private final User user;

    // コンストラクタ
    public LoginUser(User user) {
        this.user = user;
    }

    // Entityクラスである、Userオブジェクトのゲッター
    public User getUser() {
        return this.user;
    }

    // ユーザーの認証に使用されるパスワードを返却する
    @Override // オーバーライドしたことが明確になるよう@Overrideを設定
    public String getPassword() {
        return this.user.getPassword();
    }

    // ユーザーの認証に使用されるユーザー名を返却する
    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    // 以降は今回利用しません

    // ユーザーに付与された権限を返却する
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    // Collection<? extends GrantedAuthority>：権限情報を格納するためのコレクション
    // Collection：要素の集まりを表すインターフェース
    // ?：ワイルドカード型（具体的な型を指定せず、任意の型を受け入れる型）
    // extends：ワイルドカード型を制限するキーワードで、特定の型またはそのサブタイプ（派生型）を受け入れる
    // GrantedAuthority：ユーザーの権限情報を表現するためのインターフェース
    // getAuthorities()：ユーザーが持つ権限（ロール）情報を取得するためのメソッド
        return AuthorityUtils.NO_AUTHORITIES;
        // AuthorityUtils.NO_AUTHORITIES：何の特別な権限も持っておらず、一般的なユーザーとして扱われる
    }

    // アカウントの有効期限の状態を判定する
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // アカウントのロック状態を判定する
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 資格情報の有効期限の状態を判定する
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 有効なユーザかを判定する
    @Override
    public boolean isEnabled() {
        return true;
    }
}