package org.example.devnet.user.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.devnet.comment.models.Comment;
import org.example.devnet.community.models.Community;
import org.example.devnet.post.models.Post;
import org.example.devnet.projectshowcase.models.Project;
import org.example.devnet.reviews.models.Review;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String surname;

    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @Column(length = 100)
    private String role;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column
    private String profileImage;

    @Column(nullable = false)
    private LocalDate dateJoined = LocalDate.now();

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 100)
    private String confirmPassword;

    @OneToMany(mappedBy = "username")
    private List<Post> posts;

    @OneToMany(mappedBy = "username")
    private List<Comment> comments;

    @OneToMany(mappedBy = "username")
    private List<Review> reviews;

    @OneToMany(mappedBy = "owner")
    private List<Community> communitiesCreated;

    @OneToMany(mappedBy = "username")
    private List<Project> projects;

    @Override
    public String toString() {
        return username;
    }
}
