package com.example.topshiriqfirma.Repository;

import com.example.topshiriqfirma.enums.RollName;
import com.example.topshiriqfirma.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RollRepository extends JpaRepository<Roles,Integer> {
    Roles findByRollName(RollName rollName);
}
