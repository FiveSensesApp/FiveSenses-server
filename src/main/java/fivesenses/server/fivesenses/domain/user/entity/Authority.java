package fivesenses.server.fivesenses.domain.user.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority {

    @Id
    @Column(name = "authority_name")
    private String authorityName;


    @Override
    public String toString() {
        return "Authority{" +
                "authorityName='" + authorityName + '\'' +
                '}';
    }
}
