package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//Spring Securityの設定を有効にする
@EnableWebSecurity 
public class SecurityConfig extends WebSecurityConfigurerAdapter {
// WebSecurityConfigurerAdapter：セキュリティ設定用のクラスは必ず継承する
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    // configure()：セキュリティ設定をカスタマイズするためのメソッド
    // HttpSecurity http：HTTPリクエストに対するセキュリティ設定を行うためのクラス
    	
    	// 認可の設定
    	http.authorizeRequests() // HTTPリクエストに対するアクセス許可の設定を開始する
            .antMatchers("/loginForm").permitAll() 
            // antMatchers()：特定のURLパターンに対して適用されるアクセス許可ルールを設定
            // "/loginForm"：/loginForm に対して
            // permitAll()：認証なしでアクセスを許可
            .anyRequest().authenticated();
    	    // anyRequest()：どのリクエストに対しても適用されるアクセス許可ルールを設定
    	    // authenticated()：認証が必要であることを示す
    	
    	//ログイン処理
    	http.formLogin()
            .loginProcessingUrl("/login") // ログイン処理のパス
            .loginPage("/loginForm") // ログインページの指定
            .usernameParameter("email") // ログインページのメールアドレス(<input>タグのname属性と引数を合わせる必要がある)
            .passwordParameter("password") // ログインページのパスワード(<input>タグのname属性と引数を合わせる必要がある)
            .defaultSuccessUrl("/home", true) // ログイン成功後のパス
            //第1引数には認証が成功したときの遷移先を指定
            //第2引数がtrueの場合は、認証成功後に第1引数のパスに遷移
            .failureUrl("/loginForm?error"); // ログイン失敗時のパス
    	    // ?error：GETパラメータもつけることでエラーのメッセージ表示が簡単に行える
    	
    	//ログアウト処理
    	http.logout()
            .logoutUrl("/logout") //ログアウト処理のパス
            //今回のプロジェクトの場合では、/logoutにPOSTメソッドでアクセスすることでログアウトの処理が実行される
            .logoutSuccessUrl("/loginForm"); //ログアウト成功後のパス
    }
    
    // ハッシュアルゴリズムの定義
    @Bean
    // メソッドの戻り値をBeanとしてSpringコンテナに登録
    public PasswordEncoder passwordEncoder() {
    // passwordEncoder()：PasswordEncoderのインスタンスを提供する
        return new BCryptPasswordEncoder();
        // BCryptPasswordEncoder()：パスワードをハッシュ化する
    }
    
    @Override
    // WebSecurity型の引数を持ったconfigure()を追加します
    public void configure(WebSecurity web) throws Exception {
    //WebSecurity web：アプリケーション内のリクエストに対するセキュリティフィルターの設定を調整する
        web.ignoring().antMatchers("/css/**", "/js/**", "/images/**");
        // ignoring()：指定したリクエストパスをセキュリティーフィルターから除外する
    }
}