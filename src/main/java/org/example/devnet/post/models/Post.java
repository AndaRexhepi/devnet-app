package org.example.devnet.post.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.devnet.comment.models.Comment;
import org.example.devnet.community.dtos.CommunityDto;
import org.example.devnet.user.models.User;
import org.example.devnet.community.models.Community;

import java.time.LocalTime;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "community_id")
    private Community community;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 500)
    private String body;

    @Column(length = 1000)
    private String imageUrl;

    @Column(nullable = false)
    private int likes;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @Column(nullable = false)
    private LocalTime postedAt = LocalTime.of(0, 0);

}
