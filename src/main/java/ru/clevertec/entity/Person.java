package ru.clevertec.entity;

import lombok.*;
import ru.clevertec.annotation.CorrectEmail;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class Person {
    @NonNull
    private Integer id;
    private String name;
    private String lastName;
    @CorrectEmail
    private String email;
    private String phoneNumber;

}
