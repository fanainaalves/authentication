package com.edu.authentication.user.repositories;
import java.util.UUID;

import com.edu.authentication.user.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<UserModel, UUID>{
    UserDetails findByUsername(String username);

}
