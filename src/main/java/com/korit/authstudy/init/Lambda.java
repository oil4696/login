package com.korit.authstudy.init;

import com.korit.authstudy.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@RequiredArgsConstructor
class OptionalStudy<T> {
    private final T present;

    public void ifPresentOrELse(Consumer<T> action, Runnable runnable) {
        if (present != null) {
            action.accept(present);
        } else {
            runnable.run();
        }
    }
}


@Component
public class Lambda implements CommandLineRunner {

    @Override
    public  void run(String... args) throws  Exception{
        // 초기 데이터를 설정할 때 사용함
        User user = User.builder()
                .id(100)
                .username("test")
                .password("1234")
                .build();
        OptionalStudy<User> optionalStudy = new OptionalStudy<>(null);
        Consumer<User> consumer = new Consumer<User>() {
            @Override
            public void accept(User user) {
                System.out.println("user객체 찾음: " + user);
            }
        };
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("user객체 못 찾아서 여기서 다른 작업해준거임");
            }
        };
        optionalStudy.ifPresentOrELse(consumer, runnable);


//        위의 코드와 100% 똑같은 코드다
        Consumer<User> consumerLambda =(u) ->{
            System.out.println("user객체 찾음: " + u);
        };
        Runnable runnableLambda = () -> {
            System.out.println("user객체 못 찾아서 여기서 다른 작업해준거임");
        };
        optionalStudy.ifPresentOrELse(consumer, runnable);
    }
}
