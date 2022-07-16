package com.example.topshiriqfirma.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Vazifalar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String vazifaNomi;
    @Column(nullable = false)
    private String vazifaHaqida;
    @Column(nullable = false)
    private String vazifaTugashVaqti;
    @OneToOne
    private Hodimlar hodimlar;
}
