package org.example.devnet.reviews.models;

import jakarta.persistence.*;
import lombok.*;
import org.example.devnet.user.models.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User username;

    @Column(nullable = false, length = 100)
    private String role;

    private double rating;

    @Column(nullable = false, length = 1000)
    private String review;


}
