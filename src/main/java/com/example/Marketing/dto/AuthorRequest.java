package com.example.Marketing.dto;

import jakarta.validation.constraints.*;

public record AuthorRequest(
        @NotBlank(message = "El nombre de usuario es obligatorio")
        String username,

        @NotNull(message = "El campo de verificación es obligatorio")
        Boolean isVerified,

        @Min(value = 0, message = "El número de seguidores no puede ser negativo")
        Integer followerCount,

        @NotNull(message = "El campo de influencer prioritario es obligatorio")
        Boolean isPriorityInfluencer
) {}
