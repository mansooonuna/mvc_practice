package org.example;

import org.example.annotation.Controller;
import org.example.annotation.Service;
import org.example.model.User;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Controller 애노테이션이 설정되어 있는 모든 클래스를 찾아 출력한다.
 * @Service
 * ShowClassInfo
 */
public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);
    @Test
    void controllerScan() {

        Set<Class<?>> beans = getTypesAnnotationWith(List.of(Controller.class, Service.class));

        logger.debug("beans : [{}]", beans);
    }

    @Test
    void showClassInfo() {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());

        logger.debug("User all declared fields ; [{}]", Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList()));
        logger.debug("User all declared constructors ; [{}]", Arrays.stream(clazz.getDeclaredConstructors()).collect(Collectors.toList()));
        logger.debug("User all declared methods ; [{}]", Arrays.stream(clazz.getDeclaredMethods()).collect(Collectors.toList()));

    }



    @Test
    void load() throws ClassNotFoundException {
        /**
         * 힙 영역에 있는 클래스 타입 객체를 가져오는 방법
         */
        // 1
        Class<User> clazz = User.class;

        // 2
        User user = new User("testId", "testName");
        Class<? extends User> clazz2 = user.getClass();

        // 3
        Class<?> clazz3 = Class.forName("org.example.model.User");


        logger.debug("class : [{}]", clazz);
        logger.debug("class : [{}]", clazz2);
        logger.debug("class : [{}]", clazz3);

        assertThat(clazz == clazz2).isTrue();
        assertThat(clazz2 == clazz3).isTrue();
        assertThat(clazz3 == clazz).isTrue();

    }


    private static Set<Class<?>> getTypesAnnotationWith(List<Class<? extends Annotation>> annotations) {
        Reflections reflections = new Reflections("org.example");

        Set<Class<?>> beans = new HashSet<>();
        annotations.forEach(annotation -> beans.addAll(reflections.getTypesAnnotatedWith(annotation)));

        return beans;
    }

}
