package com.webtoonscorp.spring.aop.pointcut;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.util.PatternMatchUtils;

public class DualPointcut extends NameMatchMethodPointcut {

    public void setMappedClassName(String mappedClassName) {
        setClassFilter(new SimpleClassFilter(mappedClassName));
    }

    static class SimpleClassFilter implements ClassFilter {

        String mappedName;

        private SimpleClassFilter(String mappedName) {
            this.mappedName = mappedName;
        }

        public boolean matches(Class<?> type) {
            return PatternMatchUtils.simpleMatch(mappedName, type.getSimpleName());
        }
    }
}
