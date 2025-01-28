package org.example.devnet.projectshowcase.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.devnet.user.models.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User username;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(nullable = false)
    private String imageUrl;
}
