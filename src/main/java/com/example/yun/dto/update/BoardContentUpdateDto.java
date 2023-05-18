package com.example.yun.dto.update;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardContentUpdateDto {

    @NotNull
    @ApiModelProperty(value = "게시물 id", required = true)
    private Long id;

    @NotBlank
    @Size(max = 1000)
    @ApiModelProperty(value = "내용", example = "ㅎㅇ요", required = true)
    private String content;
}
