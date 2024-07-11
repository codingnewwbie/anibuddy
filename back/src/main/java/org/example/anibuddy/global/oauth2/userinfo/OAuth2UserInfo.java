package org.example.anibuddy.global.oauth2.userinfo;

import java.util.Map;

public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String,Object> attributes){
        this.attributes = attributes;
    }


}
