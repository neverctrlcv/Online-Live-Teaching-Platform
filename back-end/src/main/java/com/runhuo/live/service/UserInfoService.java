package com.runhuo.live.service;

import com.runhuo.live.vo.R;
import com.runhuo.live.vo.request.IdentityInfoReqVo;
import com.runhuo.live.vo.request.UserInfoReqVo;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/17 17:51
 */
public interface UserInfoService {
    public R updateUserAvatar(String token,MultipartFile multipartFile) throws IOException;
    public R userInfoModify(String token, UserInfoReqVo userInfoReqVo);
    public R identityVerify(String token, IdentityInfoReqVo identityInfoReqVo);
}
