package com.example.sp.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class User {
    @NotNull
    private Integer id;

    private String username;

    @JsonIgnore//让springmvc把当前对象转化成json字符串的时候，忽略password，最终的json字符串中就没有password这个属性了
    private String password;

    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;


    @NotEmpty
    @Email
    private String email;


    private String userPic;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")//注意大小写！！！
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
