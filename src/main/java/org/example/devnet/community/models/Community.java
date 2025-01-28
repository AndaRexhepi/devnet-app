package org.example.devnet.community.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.devnet.user.models.User;
import org.example.devnet.post.models.Post;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "communities")
public class Community {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @ManyToOne()
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column()
    private String imageUrl;

    private LocalDate createdDate;

    @Column
    @OneToMany(mappedBy = "community")
    private List<Post> posts;



}
