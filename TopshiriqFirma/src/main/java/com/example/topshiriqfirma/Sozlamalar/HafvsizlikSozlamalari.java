package com.example.topshiriqfirma.Sozlamalar;

import com.example.topshiriqfirma.Filter.Filter;
import com.example.topshiriqfirma.Service.HodimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Properties;

@Configuration
@EnableWebSecurity
public class HafvsizlikSozlamalari extends WebSecurityConfigurerAdapter {
    @Autowired
    HodimService hodimServive;

    @Autowired
    Filter filter;
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JavaMailSender getJAvaMailSender(){
        JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("temuryoldoshev10@gmail.com");
        mailSender.setPassword("eaapkqflqsxfpfuq");
        Properties props=mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.debug","true");
        return mailSender;
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*super.configure(auth);*/
        auth.userDetailsService(hodimServive).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*super.configure(http);*/
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/directorTayinlash","/auth/manegerTayinlash","/auth/emailtasdiqlash").permitAll()
                .anyRequest()
                .authenticated();
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
