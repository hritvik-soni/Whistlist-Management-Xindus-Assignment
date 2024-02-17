package com.hritvik.WhistlistManagementXindusAssignment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class WishlistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String description;
    private double price;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    public WishlistItem(int i, String test, String test1, double v) {
    }
}
