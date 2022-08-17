package com.ateam.popserver.common.config;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public final class RememberMeConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<RememberMeConfigurer<H>, H> {

    /**
     * The default name for remember me parameter name and remember me cookie name
     */
    private static final String DEFAULT_REMEMBER_ME_NAME = "remember-me";
  
    
}

