package com.bookexample.mynewdb;

public class Model {


    String id, image, name, age, phone, addTimeStamp, updateStamp;
    //constuctor 변수선언(db에 넣을 data 변수)...
    // 이상태에서 alr+insert 버튼 자동으로 constructor 생성
    public Model(String id, String image, String name, String age, String phone, String addTimeStamp, String updateStamp) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.addTimeStamp = addTimeStamp;
        this.updateStamp = updateStamp;
    }

    //alr+insert get/setID 선택 자동생성


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddTimeStamp() {
        return addTimeStamp;
    }

    public void setAddTimeStamp(String addTimeStamp) {
        this.addTimeStamp = addTimeStamp;
    }

    public String getUpdateStamp() {
        return updateStamp;
    }

    public void setUpdateStamp(String updateStamp) {
        this.updateStamp = updateStamp;
    }
}
