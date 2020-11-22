package com.cube.kiosk.modules.hardware;

import java.lang.annotation.*;

/**
 * 检测是否有卡
 * @author 李晋
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckCard {
}
