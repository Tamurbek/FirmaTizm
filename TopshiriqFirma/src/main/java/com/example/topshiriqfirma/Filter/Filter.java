package com.example.topshiriqfirma.Filter;

import com.example.topshiriqfirma.Service.HodimService;
import com.example.topshiriqfirma.WebToken.GetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class Filter extends OncePerRequestFilter {
    @Autowired
    GetToken getToken;

    @Autowired
    HodimService hodimService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authentication = request.getHeader("Auth");
        if (!(authentication==null) && authentication.startsWith("Bearer")){
            authentication = authentication.substring(7);
            if (getToken.tokenCheck(authentication)){
                System.out.println(authentication);
                String s = getToken.userCheck(authentication);
                UserDetails userDetails = hodimService.loadUserByUsername(s);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(usernamePasswordAuthenticationToken);
            }
            else System.out.println("null");
        }
        filterChain.doFilter(request, response);
    }
}
