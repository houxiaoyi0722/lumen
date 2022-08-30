package com.sang.common.domain.router.entity.finder;

import com.sang.common.domain.router.entity.Button;
import io.ebean.Finder;
import lombok.Builder;

/**
 * 页面按钮维护
 * 页面按钮维护
 * finder
 * hxy 2022-08-30 17:45:30
 */
@Builder
public class ButtonFinder extends Finder<Long, Button> {

    /**
    * Construct using the default EbeanServer.
    */
    public ButtonFinder() {
        super(Button.class);
    }

}

