package spring.pgt.crudapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.pgt.crudapp.data.RoleEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private String nomorInduk;

    private String name;

    private RoleEnum role;
}
