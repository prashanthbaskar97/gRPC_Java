package com.gitlab.pcbook.service;

import com.gitlab.techschool.pcbook.pb.Laptop;

public interface LaptopStore {

    void Save(Laptop laptop)throws Exception;

    Laptop Find(String id);

}
