package org.example.devnet.comment.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.devnet.post.models.Post;
import org.example.devnet.user.models.User;


import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User username;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(length = 500)
    private String comment;

    @Column
    private LocalTime postedAt = LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute());

}
