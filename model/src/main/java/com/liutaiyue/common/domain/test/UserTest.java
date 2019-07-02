package com.liutaiyue.common.domain.test;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by mrt on 2018/3/30.
 */
@Data
@ToString
public class UserTest {


    @Id
    private String id;
    private String name;

    @Column(name="create_time")
    private Date createTime;
}
