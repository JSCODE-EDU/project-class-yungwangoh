package com.example.yun.dto.update;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardTitleUpdateDto {

    @NotNull
    private Long id;
    @NotBlank
    @Size(max = 15)
    private String title;
}
