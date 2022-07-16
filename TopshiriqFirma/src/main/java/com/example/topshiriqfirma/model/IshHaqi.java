package com.example.topshiriqfirma.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class IshHaqi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private double miqdor;

    @ManyToOne
    private Hodimlar hodimlar;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp createdTime;

    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp updatedTime;

}
