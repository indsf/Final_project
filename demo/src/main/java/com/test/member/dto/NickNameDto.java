package com.test.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NickNameDto {
    @NotBlank
    private String oldNickname;

    @NotBlank
    private String newNickname;

    // getter, setter
    public String getOldNickname() { return oldNickname; }
    public void setOldNickname(String oldNickname) { this.oldNickname = oldNickname; }

    public String getNewNickname() { return newNickname; }
    public void setNewNickname(String newNickname) { this.newNickname = newNickname; }
}
