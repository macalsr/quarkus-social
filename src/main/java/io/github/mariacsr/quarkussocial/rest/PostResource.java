package io.github.mariacsr.quarkussocial.rest;

import io.github.mariacsr.quarkussocial.domain.model.Post;
import io.github.mariacsr.quarkussocial.domain.model.User;
import io.github.mariacsr.quarkussocial.domain.repository.PostRepository;
import io.github.mariacsr.quarkussocial.domain.repository.UserRepository;
import io.github.mariacsr.quarkussocial.rest.dto.CreatePostRequest;
import io.github.mariacsr.quarkussocial.rest.dto.PostResponse;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Inject
    public PostResource(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @POST
    @Transactional
    public Response savePost(@PathParam("userId") Long userId, CreatePostRequest createPostRequest) {
        User user = userRepository.findById(userId);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Post post = new Post();
        post.setText(createPostRequest.text());
        post.setUser(user);
        postRepository.persist(post);
        return Response.status(Response.Status.CREATED).entity(post).build();
    }

    @GET
    public Response getPosts(@PathParam("userId") Long userId){
        User user = userRepository.findById(userId);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<Post> postList = postRepository.findPostsByUserOrderByDate(user);
        List<PostResponse> postResponseList = postList.stream().map(PostResponse::of).toList();
        return Response.status(Response.Status.OK)
                .entity(postResponseList).build();
    }
}
