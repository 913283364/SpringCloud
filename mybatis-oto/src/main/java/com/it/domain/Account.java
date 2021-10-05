package com.it.domain;

public class Account {
    private Integer id;
    private String name;
    private Float money;
    private Integer uids;
    //一个账户对应一个用户
    private User user; //此user也是pojo


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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money=" + money +
                ", uids=" + uids +
                ", user=" + user +
                '}';
    }

}
