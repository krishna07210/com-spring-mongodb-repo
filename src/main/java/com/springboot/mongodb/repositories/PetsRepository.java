package com.springboot.mongodb.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.springboot.mongodb.model.Pets;

public interface PetsRepository extends MongoRepository<Pets, String> {
	Pets findBy_id(ObjectId _id);
}