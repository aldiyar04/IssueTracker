package kz.iitu.issuetracker.entity.enumconverter;

import kz.iitu.issuetracker.entity.User;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<User.Role, String> {
    @Override
    public String convertToDatabaseColumn(User.Role role) {
        return role.toString();
    }

    @Override
    public User.Role convertToEntityAttribute(String s) {
        for (User.Role role: User.Role.values()) {
            if (Objects.equals(s, role.toString())) {
                return role;
            }
        }
        throw new IllegalArgumentException(s + " is not a valid User.Role enum constant");
    }
}
