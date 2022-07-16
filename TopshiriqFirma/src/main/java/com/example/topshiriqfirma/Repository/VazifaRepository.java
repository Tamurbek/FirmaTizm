package com.example.topshiriqfirma.Repository;

import com.example.topshiriqfirma.model.Hodimlar;
import com.example.topshiriqfirma.model.Vazifalar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VazifaRepository extends JpaRepository<Vazifalar,Integer> {
    Optional<Vazifalar> findByHodimlar(Hodimlar hodimlar);
}
