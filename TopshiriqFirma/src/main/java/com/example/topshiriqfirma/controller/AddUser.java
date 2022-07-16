package com.example.topshiriqfirma.controller;

import com.example.topshiriqfirma.model.Hodimlar;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AddUser implements AuditorAware<Integer> {
    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null && authentication.isAuthenticated() && authentication.getPrincipal().equals("anonymousUser")){
            Hodimlar hodimlar= (Hodimlar) authentication.getPrincipal();
            return Optional.of(hodimlar.getId());
        }
        return Optional.empty();
    }
}
