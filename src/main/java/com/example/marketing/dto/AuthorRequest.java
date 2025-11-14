package com.example.marketing.dto;

import jakarta.validation.constraints.*;

public record AuthorRequest(
        @NotBlank(message = "El nombre de usuario es obligatorio")
        @Size(min = 2, max = 50)
        String username,

        @NotNull(message = "El campo de verificación es obligatorio")
        Boolean verified,

        @Min(value = 0, message = "El número de seguidores no puede ser negativo")
        Integer follower,

        @NotNull(message = "El campo de influencer prioritario es obligatorio")
        Boolean priority
) {}
