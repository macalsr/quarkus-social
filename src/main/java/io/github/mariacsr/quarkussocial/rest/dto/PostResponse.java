package io.github.mariacsr.quarkussocial.rest.dto;

import io.github.mariacsr.quarkussocial.domain.model.Post;

import java.time.LocalDateTime;

public record PostResponse(String text, LocalDateTime dateTime) {

    public static PostResponse of(Post post) {
        return new PostResponse(post.getText(), post.getDateTime());
    }
}
