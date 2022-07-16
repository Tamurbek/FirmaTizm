package com.example.topshiriqfirma.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Turniket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @CreatedBy
    private UUID createdBy;
    @LastModifiedBy
    private UUID LastModifiedBy;
    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp createdTime;
    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp updateTime;
}
