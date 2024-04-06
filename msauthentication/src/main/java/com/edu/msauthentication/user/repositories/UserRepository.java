package com.edu.msauthentication.user.repositories;
import java.util.UUID;

import com.edu.msauthentication.user.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<UserModel, UUID>{
    UserDetails findByName(String name);

}