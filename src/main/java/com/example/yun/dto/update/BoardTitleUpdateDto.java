package com.example.yun.dto.update;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardTitleUpdateDto {

    @NotNull
    @ApiModelProperty(value = "게시물 id", required = true)
    private Long id;

    @NotBlank
    @Size(max = 15)
    @ApiModelProperty(value = "제목", example = "안녕하세요", required = true)
    private String title;
}
