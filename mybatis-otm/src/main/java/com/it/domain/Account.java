package com.it.domain;

public class Account {
    private Integer id;
    private String name;
    private Float money;
    private Integer uids;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Integer getUids() {
        return uids;
    }

    public void setUids(Integer uids) {
        this.uids = uids;
    }


    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money=" + money +
                ", uids=" + uids +
                '}';
    }

}
