package com.example.Marketing.mapper;


import com.example.Marketing.dto.AuthorRequest;
import com.example.Marketing.dto.AuthorResponse;
import com.example.Marketing.model.Author;


public class AuthorMapper {

    // Convierte de entidad → DTO de respuesta
    public static AuthorResponse toResponse(Author entity) {
        if (entity == null) return null;

        return AuthorResponse.builder()
                .authorApiId(entity.getAuthorId())
                .username(entity.getUsername())
                .isVerified(entity.getIsVerified())
                .followerCount(entity.getFollowerCount())
                .isPriorityInfluencer(entity.getIsPriorityInfluencer())
                .firstRegistrationDate(entity.getFirstRegistrationDate())
                .build();
    }

    // Convierte de DTO de request → entidad nueva
    public static Author toEntity(AuthorRequest request) {
        if (request == null) return null;

        Author entity = new Author();
        entity.setUsername(request.username());
        entity.setIsVerified(request.isVerified());
        entity.setFollowerCount(request.followerCount());
        entity.setIsPriorityInfluencer(request.isPriorityInfluencer());
        return entity;
    }

    // Copia los valores del request sobre una entidad existente (para updates)
    public static void copyToEntity(AuthorRequest request, Author entity) {
        if (request == null || entity == null) return;

        entity.setUsername(request.username());
        entity.setIsVerified(request.isVerified());
        entity.setFollowerCount(request.followerCount());
        entity.setIsPriorityInfluencer(request.isPriorityInfluencer());
    }
}
