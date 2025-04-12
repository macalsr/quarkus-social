package io.github.mariacsr.quarkussocial.domain.repository;

import io.github.mariacsr.quarkussocial.domain.model.Post;
import io.github.mariacsr.quarkussocial.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Post> {

    public List<Post> findPostsByUserOrderByDate(User user){
        return this.find("user", Sort.by("dateTime", Sort.Direction.Descending), user).list();
    }

}
