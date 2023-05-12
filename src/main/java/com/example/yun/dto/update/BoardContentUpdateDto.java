package com.example.yun.dto.update;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardContentUpdateDto {

    @NotNull
    private Long id;
    @NotBlank
    @Size(max = 1000)
    private String content;
}
