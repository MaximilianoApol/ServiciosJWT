package com.example.marketing.mapper;

import com.example.marketing.dto.AuthorRequest;
import com.example.marketing.dto.AuthorResponse;
import com.example.marketing.model.Author;


public class AuthorMapper {


//     Convierte entidad Author a AuthorResponse DTO

    public static AuthorResponse toResponse(Author entity) {
        if (entity == null) {
            return null;
        }

        return AuthorResponse.builder()
                .authorApiId(entity.getAuthorId())
                .username(entity.getUsername())
                .verified(entity.getIsVerified())
                .follower(entity.getFollowerCount())
                .priority(entity.getIsPriorityInfluencer())
                .firstRegistrationDate(entity.getFirstRegistrationDate())
                .build();
    }


//      Convierte AuthorRequest DTO a nueva entidad Author
    public static Author toEntity(AuthorRequest request) {
        if (request == null) {
            return null;
        }

        Author entity = new Author();
        entity.setUsername(request.username());
        entity.setIsVerified(request.verified() != null ? request.verified() : false);
        entity.setFollowerCount(request.follower() != null ? request.follower() : 0);
        entity.setIsPriorityInfluencer(request.priority() != null ? request.priority() : false);
        // firstRegistrationDate se establece automáticamente con valor por defecto

        return entity;
    }


//     * Actualiza una entidad Author existente con datos de AuthorRequest
    public static void copyToEntity(AuthorRequest request, Author existing) {
        if (request == null || existing == null) {
            return;
        }

        if (request.username() != null) {
            existing.setUsername(request.username());
        }

        if (request.verified() != null) {
            existing.setIsVerified(request.verified());
        }

        if (request.follower() != null) {
            existing.setFollowerCount(request.follower());
        }

        if (request.priority() != null) {
            existing.setIsPriorityInfluencer(request.priority());
        }
    }
}