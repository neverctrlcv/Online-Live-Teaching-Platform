package com.runhuo.live.controller;

import com.runhuo.live.service.UserInfoService;
import com.runhuo.live.vo.R;
import com.runhuo.live.vo.request.IdentityInfoReqVo;
import com.runhuo.live.vo.request.UserInfoReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/17 17:44
 */
@RestController
@RequestMapping("/userInfo")
@SuppressWarnings("all")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @PutMapping("/updateUserInfo")
    public R userInfoModify(@RequestHeader HttpHeaders httpHeaders,
                            @RequestBody UserInfoReqVo userInfoReqVo) {
        List<String> authorization = httpHeaders.get("Authorization");
        return userInfoService.userInfoModify(authorization.get(0), userInfoReqVo);
    }

    @PutMapping("/updateAvatar")
    public R updateAvatar(@RequestParam("file") MultipartFile multipartFile,
                          @RequestHeader HttpHeaders httpHeaders) throws IOException {
        List<String> authorization = httpHeaders.get("Authorization");
        return userInfoService.updateUserAvatar(authorization.get(0), multipartFile);
    }

    @PutMapping("/updateUserIdentityInfo")
    public R updateUserIdentityInfo(@RequestHeader HttpHeaders httpHeaders,
                                    @RequestBody IdentityInfoReqVo identityInfoReqVo) {
        List<String> authorization = httpHeaders.get("Authorization");
        return userInfoService.identityVerify(authorization.get(0), identityInfoReqVo);
    }
}
