package com.example.groenify_api.database;

@Entity
@Table(name = "`epole_brand`")
public class EPoleBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "mediumtext")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(final Long var) {
        this.id = var;
    }

    public String getName() {
        return name;
    }

    public void setName(final String var) {
        this.name = var;
    }

}
