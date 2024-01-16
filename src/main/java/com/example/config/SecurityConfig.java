package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@EnableWebSecurity // Spring Securityの設定を有効にします
public class SecurityConfig extends WebSecurityConfigurerAdapter {
// WebSecurityConfigurerAdapter：セキュリティ設定用のクラスは必ず継承する
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    // configure()：セキュリティ設定をカスタマイズするためのメソッド
    // HttpSecurity http：HTTPリクエストに対するセキュリティ設定を行うためのクラス
    	
    	// 認可の設定
    	http.authorizeRequests()
            .antMatchers("/loginForm").permitAll() // /loginFormは、全ユーザからのアクセスを許可
            .anyRequest().authenticated(); // 許可した項目以外は、認証を求める
    	
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
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    // WebSecurity型の引数を持ったconfigure()を追加します
    public void configure(WebSecurity web) throws Exception {
        /** 以下のファイルパス配下のディレクトリ、ファイルすべてを認証・認可の対象から除外します
            src/main/resources/static/css/
            src/main/resources/static/js/
            src/main/resources/static/images/
        */
        web.ignoring().antMatchers("/css/**", "/js/**", "/images/**");
    }
}


/*　HttpSecurity httpクラスの詳細
URLベースのアクセス制御：
　・antMatchers() 特定のURLパターンに対するアクセス許可や拒否のルールを設定できます。
ログイン関連の設定：
　・formLogin() ログインフォームやログインページのカスタマイズ、認証に使用するユーザー名とパスワードのフィールド名、
　　　　　　　　　 ログイン成功時や失敗時のリダイレクト先などを設定できます。
ログアウト関連の設定：
　・logout() ログアウト時の動作を設定できます。
認証の設定：
　・authorizeRequests() リクエストに対するアクセス権を設定できます。
　・permitAll() メソッドを使用して特定のリクエストをすべてのユーザーに許可したり、
　・authenticated() メソッドを使用して認証が必要なリクエストを指定したりします。
CSRF対策：
　・csrf() メソッドを使用して、クロスサイトリクエストフォージェリ（CSRF）攻撃からの保護を有効または無効にできます。
　　　　　　 CSRFトークンの生成と検証を設定できます。

カスタムフィルターの追加：
 addFilterBefore()、addFilterAfter() メソッドを使用して、カスタムセキュリティフィルターを追加できます。
                                     これにより、リクエストとレスポンスの処理をカスタマイズできます。
*/