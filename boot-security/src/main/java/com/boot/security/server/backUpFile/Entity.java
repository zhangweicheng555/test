package com.boot.security.server.backUpFile;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *  缺点:
        1, 有些属性并不想让toString给输入出来, (可能是没用, 也有可能是出于密码方面考虑),
            但用反射时所有属性值都给输了出来. (这个已有解决,见下面,不过虽说解决了,但还是不如另一种方式灵活.)
        2, 安全方面的考虑. 一般来说,一个java类是的属性都是private的,
            这样用反射来构建toString方法时,就得绕过private的限制. 
 * @author a-zhangweicheng
 */
@SuppressWarnings("serial")
public class Entity implements Serializable {

	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this);
	}

	public String toSimpleString() {
		return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
