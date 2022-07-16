package com.example.topshiriqfirma.Repository;

import com.example.topshiriqfirma.model.Hodimlar;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Email;
import java.util.Optional;

public interface HodimRepositori extends JpaRepository<Hodimlar,Integer> {
    Optional<Hodimlar> findByEmailAndEmailcode(@Email String email,String emailcode);
    Optional<Hodimlar> findByEmail(@Email String email);
}
