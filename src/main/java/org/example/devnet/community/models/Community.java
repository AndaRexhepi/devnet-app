package org.example.devnet.community.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.devnet.user.models.User;
import org.example.devnet.post.models.Post;

import java.time.LocalDate;
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

    @Column()
    private String imageUrl;

    private LocalDate createdDate;

    @ManyToMany
    @JoinTable(
            name = "community_users",
            joinColumns = @JoinColumn(name = "community_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> members;

    @Column
    @OneToMany(mappedBy = "community")
    private List<Post> posts;



}
