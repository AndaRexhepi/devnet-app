package org.example.devnet.post.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.devnet.user.models.User;
import org.example.devnet.community.models.Community;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User username;

    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 500)
    private String body;

    @Column(nullable = false)
    private int likes;

    @Column(length = 500)
    private String comment;

    @Column(nullable = false)
    private LocalTime postedAt;

}
